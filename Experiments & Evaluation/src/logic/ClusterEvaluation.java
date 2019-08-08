package logic;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import frontend.LexNorm;
import utils.Constants;


public class ClusterEvaluation {
	
	public static HashMap<Integer,ArrayList<String>> standardizer = new HashMap<Integer,ArrayList<String>>();
	public static HashMap<Integer, ArrayList<String>> clusters = new HashMap<Integer,ArrayList<String>>();
	public static HashMap<String, Integer> clusterAssign = new HashMap<String, Integer>();
	public static HashMap<String, Integer> clusterActual = new HashMap<String, Integer>();
	public static HashMap<Integer,String> actual = new HashMap<Integer,String>();
	public static HashMap<Integer,String> prediction = new HashMap<Integer,String>();
	public static  Integer wordCount = 0;
	static int num = 1;
	public static void evaluate() {
		String filename = Constants.outputFile;
		BufferedWriter bw;
		try{
			String line = "";
			String val[];
			ArrayList<String> cluster;
			String folder = "./Result Files/";
			System.out.println(Constants.proj+"-File: "+filename);
			String word = "";
			Set<Integer> resultKeys = LexNorm.results.keySet();
			for(Integer key:resultKeys){
				line = LexNorm.results.get(key);
				num++;
				wordCount++;
				val = line.split(",");
				word = val[0];
				cluster = standardizer.get(Integer.parseInt(val[1]));
				if(cluster == null)
					cluster = new ArrayList<String>();
				cluster.add(word);
				standardizer.put(Integer.parseInt(val[1]), cluster);
				cluster = clusters.get(Integer.parseInt(val[2]));
				if(cluster == null)
					cluster = new ArrayList<String>();
				cluster.add(word);
				clusters.put(Integer.parseInt(val[2]), cluster);
				clusterAssign.put(word, Integer.parseInt(val[2]));
				clusterActual.put(word, Integer.parseInt(val[1]));
			}
			System.out.println("Actual Count = "+standardizer.keySet().size());
			System.out.println("Predicted Count = "+clusters.keySet().size());
			System.out.println("Abs Difference = "+Math.abs(clusters.keySet().size() - standardizer.keySet().size()));
			Set<Integer> keys = clusters.keySet();
			int singleCount = 0;
			for(Integer key:keys){
				cluster = clusters.get(key);
				if(cluster.size()==1)
					singleCount++;
			}
			System.out.println("Single Word Cluster Count = "+singleCount);
			getBCubed();
			bw = new BufferedWriter(new FileWriter(folder+"results_"+filename));
			ArrayList<String> list = new ArrayList<String>();
			String str = "";
			for(Integer key:keys){
				list = clusters.get(key);
				str = list.get(0);
				for(int i=1;i<list.size();i++){
					str += "," + list.get(i);
				}
				bw.write(str+"\r\n");
				str = "";
			}
			bw.close();
		}
		catch(Exception ex){
			System.out.println("Line "+num);
			ex.printStackTrace();
		}
	}
	
	public static void getBCubed(){
		Set<String> words = clusterAssign.keySet(); //get complete word list
		ArrayList<String> actualClusters;
		ArrayList<String> retrievedClusters;
		double actualClusterCount = 0, retrievedClusterCount=0;
		
		double precision = 0;
		double recall = 0;
		double totalPrecision = 0;
		double totalRecall = 0;
		double averagePrecision;
		double averageRecall;
		double f_measure;
		int common = 0;
		for(String word:words){
			actualClusters = getActualCluster(word); //Returns the cluster from gold standard containing word
			retrievedClusters =	getRetrievedCluster(word); //Returns the cluster from output clusters containing word
			actualClusterCount = actualClusters.size();
			retrievedClusterCount = retrievedClusters.size();
				for(String str:retrievedClusters){
					if(actualClusters.contains(str))
						common++;
				}
				if(common!=0){
					precision = common/retrievedClusterCount;
					recall = common/actualClusterCount;
					totalPrecision += precision;
					totalRecall += recall;
				}
				common = 0;
				precision = 0;
				recall = 0;
			}
			averagePrecision = totalPrecision/wordCount;
			averageRecall = totalRecall/wordCount;
			f_measure = (2*averagePrecision*averageRecall)/(averagePrecision+averageRecall);
			System.out.println("Precision = "+averagePrecision*100);
			System.out.println("Recall = "+averageRecall*100);
			System.out.println("F-Measure = "+f_measure*100);			
	}

	private static ArrayList<String> getRetrievedCluster(String word) {
		Integer clusterid = clusterAssign.get(word);
		return clusters.get(clusterid);
	}

	private static ArrayList<String> getActualCluster(String word) {
		Integer clusterid = clusterActual.get(word);
		return standardizer.get(clusterid);
	}
}
