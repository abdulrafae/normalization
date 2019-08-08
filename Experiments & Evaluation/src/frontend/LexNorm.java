package frontend;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import dto.Dataset;
import dto.Features;
import dto.InitialCluster;
import logic.LexC;
import utils.Constants;
import utils.Utils;

public class LexNorm {
	
	public static ConcurrentHashMap<String,Integer> frequentWordMap = new ConcurrentHashMap<String, Integer>();
	public static ArrayList<String> wordList = new ArrayList<String>();
	public static HashMap<String, Integer> wordMap = new HashMap<String,Integer>();
	public static HashMap<String,Integer> clusterID = new HashMap<String, Integer>();
	public static HashMap<String,Integer> stdClusterAssignment = new HashMap<String, Integer>();
	public static HashMap<String, ArrayList<Integer>> prevWordMap = new HashMap<String,ArrayList<Integer>>();
	public static HashMap<String, ArrayList<Integer>> nextWordMap = new HashMap<String,ArrayList<Integer>>();
	public static HashMap<String, ArrayList<Integer>> prevUrduPhoneMap = new HashMap<String,ArrayList<Integer>>();
	public static HashMap<String, ArrayList<Integer>> nextUrduPhoneMap = new HashMap<String,ArrayList<Integer>>();
	public static HashMap<String, String> UrduPhoneMap = new HashMap<String, String>();
	public static HashMap<String, String> UrduPhoneCodes = new HashMap<String, String>();
	public static HashMap<String,Integer> clusterAssignment = new HashMap<String, Integer>();
	public static HashMap<Integer,ArrayList<String>> clusters = new HashMap<Integer, ArrayList<String>>();
	public static HashMap<Integer,String> results = new HashMap<Integer,String>();

	//Update the 3 parameters below for different experimental scenario
	public static String dataset = Dataset.SMS_SMALL.getName();
	public static InitialCluster initial = InitialCluster.URDUPHONE;
	public static Features features = Features.URDUPHONE_EDITDISTANCE_WORDID;
	
	public static String dataPath = "Input Files\\"+dataset+"\\";
	public static String inputPath = "Input Files\\";
	public static String standardFile = "Gold Standard.txt";
	public static int contextSize = 5;
	
	public static void main(String[] args) {
		
		loadInput();
		System.out.println("Input Read!");
		
		if(features == Features.URDUPHONE)
			Constants.outputFile = dataset+" - Experiment=1 P="+Constants.w_UrduPhone+" C_Size="+contextSize+" t="+Constants.threshold+".txt";
		else if(features == Features.EDITDISTANCE)
			Constants.outputFile = dataset+" - Experiment=2 S="+Constants.w_ED+" C_Size="+contextSize+" t="+Constants.threshold+".txt";
		else if(features == Features.URDUPHONE_EDITDISTANCE)
			Constants.outputFile = dataset+" - Experiment=3 P="+Constants.w_UrduPhone+" S="+Constants.w_ED+" C_Size="+contextSize+" t="+Constants.threshold+".txt";
		else if(features == Features.URDUPHONE_EDITDISTANCE_URDUPHONEID)
			Constants.outputFile = dataset+" - Experiment=4 P="+Constants.w_UrduPhone+" S="+Constants.w_ED+" C="+Constants.w_nextUrduPhone+" C_Size="+contextSize+" t="+Constants.threshold+".txt";
		else if(features == Features.URDUPHONE_EDITDISTANCE_WORDID)
			Constants.outputFile = dataset+" - Experiment=5 P="+Constants.w_UrduPhone+" S="+Constants.w_ED+" C="+Constants.w_nextWord+" C_Size="+contextSize+" t="+Constants.threshold+".txt";
		else{
			System.out.println("Incorrect Feature Set!");
			System.exit(0);
		}
		
		LexC.cluster();
		
		System.out.println("Dataset = "+dataset);
		System.out.println("Word Count Original = "+wordMap.keySet().size());
		System.out.println("Word Count Final = "+results.keySet().size());
		System.out.println("DONE!");
	}

	private static void loadInput() {
		BufferedReader br;
		String file = "";
		try{
			String line = "";
			String val[];
			int id = 0;
			
			file = dataset+" - Word List.txt";
			br = new BufferedReader(new FileReader(dataPath+file));
			System.out.println("Reading file \'"+dataPath+file+"\'");
			id = 1;
			while((line=br.readLine())!=null){
				val = line.split(" ");
				wordList.add(val[0]);
				wordMap.put(val[0], id);
				id++;
			}
			br.close();
			
			file = standardFile;
			br = new BufferedReader(new FileReader(inputPath+file));
			System.out.println("Reading file \'"+inputPath+file+"\'");
			while((line=br.readLine())!=null){
				id++;
				val = line.split(",");
				clusterID.put(val[1], id);
			}
			br.close();

			file = dataset+" - Frequent Word List.txt";
			br = new BufferedReader(new FileReader(dataPath+file));
			System.out.println("Reading file \'"+dataPath+file+"\'");
			while((line=br.readLine())!=null){
				val = line.split(" ");
				frequentWordMap.put(val[0], 1);
			}
			br.close();
			file = standardFile;
			br = new BufferedReader(new FileReader(inputPath+file));
			
			while((line=br.readLine())!=null){
				val = line.split(",");
				stdClusterAssignment.put(val[0], clusterID.get(val[1]));
				if(frequentWordMap.get(val[1])!=null)
					stdClusterAssignment.put(val[1], clusterID.get(val[1]));
			}
			br.close();

			ArrayList<Integer> list;
			file = dataset+" - "+contextSize+" Prev Word ID List.txt";
			br = new BufferedReader(new FileReader(dataPath+file));
			System.out.println("Reading file \'"+dataPath+file+"\'");
			while((line=br.readLine())!=null){
				list = new ArrayList<Integer>();
				val = line.split(",");
				if(val.length<contextSize+1)continue;
				for(int i=1;i<val.length;i++)
					list.add(Integer.parseInt(val[i]));
				prevWordMap.put(val[0], list);
			}
			br.close();

			file = dataset+" - "+contextSize+" Next Word ID List.txt";
			br = new BufferedReader(new FileReader(dataPath+file));
			System.out.println("Reading file \'"+dataPath+file+"\'");
			while((line=br.readLine())!=null){
				list = new ArrayList<Integer>();
				val = line.split(",");
				if(val.length<contextSize+1)continue;
				for(int i=1;i<val.length;i++)
					list.add(Integer.parseInt(val[i]));
				nextWordMap.put(val[0], list);
			}
			br.close();
			
			file = dataset+" - "+contextSize+" Prev UrduPhone ID List.txt";
			br = new BufferedReader(new FileReader(dataPath+file));
			System.out.println("Reading file \'"+dataPath+file+"\'");
			while((line=br.readLine())!=null){
				list = new ArrayList<Integer>();
				val = line.split(",");
				if(val.length<contextSize+1)continue;
				for(int i=1;i<val.length;i++)
					list.add(Integer.parseInt(val[i]));
				prevUrduPhoneMap.put(val[0], list);
			}
			br.close();
			
			file = dataset+" - "+contextSize+" Next UrduPhone ID List.txt";
			br = new BufferedReader(new FileReader(dataPath+file));
			System.out.println("Reading file \'"+dataPath+file+"\'");
			while((line=br.readLine())!=null){
				list = new ArrayList<Integer>();
				val = line.split(",");
				if(val.length<contextSize+1)continue;
				for(int i=1;i<val.length;i++)
					list.add(Integer.parseInt(val[i]));
				nextUrduPhoneMap.put(val[0], list);
			}
			br.close();
			
			file = dataset+" - UrduPhone - ID.txt";
			br = new BufferedReader(new FileReader(dataPath+file));
			System.out.println("Reading file \'"+dataPath+file+"\'");
			while((line=br.readLine())!=null){
				val = line.split(",");
				UrduPhoneMap.put(val[0], val[1]);
				if(initial == InitialCluster.URDUPHONE){
					id = Integer.parseInt(val[2]);
					clusterAssignment.put(val[0], id);
				}
				UrduPhoneCodes.put(val[1],"1");
			}
			br.close();
			
			if(initial == InitialCluster.RANDOM_URDUPHONESIZE){
				Random r = new Random();
				for(String word:wordList){
					id = r.nextInt(UrduPhoneCodes.size());
					clusterAssignment.put(word, id);
				}
			}
			
			if(initial == InitialCluster.RANDOM_SINGLE){
				id = 0;
				for(String word:wordList){
					id++;
					clusterAssignment.put(word, id);
				}
			}

			for(String word:wordMap.keySet()){
				if(stdClusterAssignment.get(word)==null || prevWordMap.get(word)==null || nextWordMap.get(word)==null || prevUrduPhoneMap.get(word)==null || nextUrduPhoneMap.get(word)==null){
					wordList.remove(word);
					clusterAssignment.remove(word);
				}
				if(clusterAssignment.get(word)==null){
					wordList.remove(word);
				}
			}

			makeClusters();
			Utils.setConstants();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void makeClusters(){
		Set<String> words = clusterAssignment.keySet();
		int id = 0;
		ArrayList<String> cluster;
		clusters = new HashMap<Integer, ArrayList<String>>();
		for(String word:words){
			id = clusterAssignment.get(word);
			cluster = clusters.get(id);
			if(cluster == null)
				cluster = new ArrayList<String>();
			cluster.add(word);
			clusters.put(id, cluster);
		}
	}
}