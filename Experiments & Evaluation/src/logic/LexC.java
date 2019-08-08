package logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import utils.Constants;
import utils.Utils;
import frontend.LexNorm;

public class LexC {
	public static HashMap<Integer,String> clustroids = new HashMap<Integer,String>();
	
	private static void setRowSums(){
		Set<Integer> clusterIds = LexNorm.clusters.keySet();
		for(Integer id:clusterIds){
			clustroids.put(id, getRowSum(id));
		}
	}
	
	private static String getRowSum(Integer id){
		ArrayList<String> words = LexNorm.clusters.get(id);
		int clusterSize = words.size();
		if(clusterSize<3)
			return words.get(0);
		double rowSums[] = new double[clusterSize];
		double maxSum = 0;
		int maxVal = -1;
		for(int i=0;i<clusterSize;i++){
			rowSums[i] = 0;
			for(int j=0;j<clusterSize;j++){
				if(i!=j)
					rowSums[i] += Utils.getSimilarity(words.get(i), words.get(j));
			}
			if(maxSum<rowSums[i]){
				maxSum = rowSums[i];
				maxVal = i;
			}
		}
		if(maxVal == -1)
			return words.get(0);
		//Split cluster if maxVal = -1
		return words.get(maxVal);
	}
	
	public static void cluster(){
		Constants.iteration++;
		setRowSums();
		int cluster1;
		String word1 = "", word2 = "";
		Set<Integer> clusterIds = LexNorm.clusters.keySet();
		Double dist = 0.0;
		int count = 0;
		double maxSim;
		int maxClusterId;
		double percent = 0;
		int num =0;
		try{
			for(int i=0;i<Constants.wordCount;i++){
				maxSim = 0;
				maxClusterId = -1;
				word1 = LexNorm.wordList.get(i);
				cluster1 = LexNorm.clusterAssignment.get(word1);
				for(int cluster2:clusterIds){
					num++;
					word2 = clustroids.get(cluster2);
					dist = Utils.getSimilarity(word1, word2);
					if(dist>Constants.threshold){
						if(maxSim<dist){
							maxSim = dist;
							maxClusterId = cluster2;
						}
					}
				}
				if(maxClusterId != cluster1){
					if(maxClusterId != -1){
						LexNorm.clusterAssignment.put(word1, maxClusterId);
						count++;
					}
					else{
						count++;
						Constants.clusterCount +=1;
						while(LexNorm.clusterAssignment.containsValue(Constants.clusterCount)){
							Constants.clusterCount +=1;
						}
						LexNorm.clusterAssignment.put(word1, Constants.clusterCount);
					}
				}
				if(i%(Constants.wordCount/1000)==0 && i>1){
					percent += 0.1;
					System.out.println("Itr"+Constants.iteration+" "+Utils.round(percent)+"%");
				}
			}
		}
		catch(Exception ex){
			System.out.println("Word = "+word1);
			System.out.println("Num = "+num);
			ex.printStackTrace();
		}
		System.out.println("Moved = "+count);
		if(count>Constants.stopingCriteria1 && Constants.iteration<Constants.stopingCriteria2){			
			LexNorm.makeClusters();
			cluster();
		}
		else{
			makeResults();
			writeResults(true);
			ClusterEvaluation.evaluate();
		}
	}

	public static void makeResults() {
		int i=1;
		for(String word:LexNorm.wordList){
			if(LexNorm.stdClusterAssignment.get(word)==null)
				continue;
			LexNorm.results.put(i, word+","+LexNorm.stdClusterAssignment.get(word)+","+LexNorm.clusterAssignment.get(word));
			i++;
		}
	}
	
	public static void writeResults(boolean withStd){
		try{
			System.out.println("Writing Results...");
			String folder = "./Assignment Files/";
			BufferedWriter bw = new BufferedWriter(new FileWriter(folder+Constants.outputFile));
			for(String word:LexNorm.wordList){
				if(withStd)
					bw.write(word+","+LexNorm.stdClusterAssignment.get(word)+","+LexNorm.clusterAssignment.get(word)+"\r\n");
				else
					bw.write(word+","+LexNorm.clusterAssignment.get(word)+"\r\n");
			}
			bw.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
