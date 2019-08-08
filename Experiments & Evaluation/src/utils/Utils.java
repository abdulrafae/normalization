package utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import dto.Features;
import logic.LexC;
import frontend.LexNorm;

public class Utils {

	public static double getSimilarity(String word1,String word2){
		double dist = 0;
		double UrduPhoneDist = 0;
		double EDDist = 0;
		double prevUrduPhoneDist = 0;
		double nextUrduPhoneDist = 0;
		double prevWordDist = 0;
		double nextWordDist = 0;
		
		String UrduPhone1 = UrduPhone(word1);
		String UrduPhone2 = UrduPhone(word2);
		if(UrduPhone1.equals(UrduPhone2))
			UrduPhoneDist = 1;
		
		EDDist = ((double)LCS(word1, word2))/(Math.min(word1.length(), word2.length()) + computeLevenshteinDistance(word1, word2));

		ArrayList<Integer> prevUrduPhone1 = new ArrayList<Integer>();
		ArrayList<Integer> prevUrduPhone2 = new ArrayList<Integer>();
		ArrayList<Integer> nextUrduPhone1 = new ArrayList<Integer>();
		ArrayList<Integer> nextUrduPhone2 = new ArrayList<Integer>();
		ArrayList<Integer> prevWord1 = new ArrayList<Integer>();
		ArrayList<Integer> prevWord2 = new ArrayList<Integer>();
		ArrayList<Integer> nextWord1 = new ArrayList<Integer>();
		ArrayList<Integer> nextWord2 = new ArrayList<Integer>();
		try{
			prevUrduPhone1 = LexNorm.prevUrduPhoneMap.get(word1);
			prevUrduPhone2 = LexNorm.prevUrduPhoneMap.get(word2);
			nextUrduPhone1 = LexNorm.nextUrduPhoneMap.get(word1);
			nextUrduPhone2 = LexNorm.nextUrduPhoneMap.get(word2);
			prevWord1 = LexNorm.prevWordMap.get(word1);
			prevWord2 = LexNorm.prevWordMap.get(word2);
			nextWord1 = LexNorm.nextWordMap.get(word1);
			nextWord2 = LexNorm.nextWordMap.get(word2);
			
			if(!isContextCorrect(word1,word2)){
				dist = 0.0;
				return dist;
			}
			
			prevUrduPhoneDist = Utils.getRankCorr(prevUrduPhone1,prevUrduPhone2);
			nextUrduPhoneDist = Utils.getRankCorr(nextUrduPhone1,nextUrduPhone2);
			prevWordDist = Utils.getRankCorr(prevWord1,prevWord2);
			nextWordDist = Utils.getRankCorr(nextWord1,nextWord2);
			
			if(LexNorm.features == Features.URDUPHONE)
				dist = Constants.w_UrduPhone*UrduPhoneDist/Constants.w_UrduPhone;
			else if(LexNorm.features == Features.EDITDISTANCE)
				dist = Constants.w_ED*EDDist/Constants.w_ED;
			else if(LexNorm.features == Features.URDUPHONE_EDITDISTANCE)
				dist = (Constants.w_UrduPhone*UrduPhoneDist + Constants.w_ED*EDDist)/(Constants.w_UrduPhone+Constants.w_ED);
			else if(LexNorm.features == Features.URDUPHONE_EDITDISTANCE_URDUPHONEID)
				dist = (Constants.w_UrduPhone*UrduPhoneDist + Constants.w_ED*EDDist + Constants.w_prevUrduPhone*prevUrduPhoneDist + Constants.w_nextUrduPhone*nextUrduPhoneDist)/(Constants.w_UrduPhone + Constants.w_ED + Constants.w_prevUrduPhone + Constants.w_nextUrduPhone);
			else if(LexNorm.features == Features.URDUPHONE_EDITDISTANCE_WORDID)
				dist = (Constants.w_UrduPhone*UrduPhoneDist + Constants.w_ED*EDDist + Constants.w_prevWord*prevWordDist + Constants.w_nextWord*nextWordDist)/(Constants.w_UrduPhone + Constants.w_ED + Constants.w_prevWord + Constants.w_nextWord);
			
			if(dist<Constants.threshold)
				dist = 0;
		}
		catch(Exception ex){
			//ex.printStackTrace();
		}
		return dist;
	}

	private static Double getRankCorr(ArrayList<Integer> list1,ArrayList<Integer> list2) {
		double dist = 0;
		int n = Math.max(list1.size(), list2.size());
		for(int i=list1.size();i>0;i--){
			if(list2.contains(list1.get(i-1))){
				for(int j=list2.size();j>0;j--){
					if(list2.get(j-1).equals(list1.get(i-1))){
						dist += min(list1.size()-i+1,list2.size()-j+1);
						break;
					}
				}
			}
		}
		return dist/((n*(n+1))/2);
	}
	private static int computeLevenshteinDistance(String str1, String str2) {
		int[][] distance = new int[str1.length() + 1][str2.length() + 1];

		for (int i = 0; i <= str1.length(); i++)
			distance[i][0] = i;
		for (int j = 1; j <= str2.length(); j++)
			distance[0][j] = j;

		for (int i = 1; i <= str1.length(); i++)
			for (int j = 1; j <= str2.length(); j++)
				distance[i][j] = min(
						distance[i - 1][j] + 1,
						distance[i][j - 1] + 1,
						distance[i - 1][j - 1]
								+ ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
										: 1));
		return distance[str1.length()][str2.length()];
	}
	

	private static int LCS(String x, String y) {

		int i, j;
		int lenx = x.length();
		int leny = y.length();
		int[][] table = new int[lenx + 1][leny + 1];

		// Initialize table that will store LCS's of all prefix strings.
		// This initialization is for all empty string cases.
		for (i = 0; i <= lenx; i++)
			table[i][0] = 0;
		for (i = 0; i <= leny; i++)
			table[0][i] = 0;

		// Fill in each LCS value in order from top row to bottom row,
		// moving left to right.
		for (i = 1; i <= lenx; i++) {
			for (j = 1; j <= leny; j++) {

				// If last characters of prefixes match, add one to former
				// value.
				if (x.charAt(i - 1) == y.charAt(j - 1))
					table[i][j] = 1 + table[i - 1][j - 1];

				// Otherwise, take the maximum of the two adjacent cases.
				else
					table[i][j] = Math.max(table[i][j - 1], table[i - 1][j]);
			}
		}
		return table[lenx][leny];
		
	}

	private static int min(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}
	
	private static int min(int a, int b) {
		return Math.min(a, b);
	}
	
	public static String UrduPhone(String word){
		return LexNorm.UrduPhoneMap.get(word);
	}
	
	public static boolean UrduPhoneExists(String word){
		return UrduPhone(word)==null?false:true;
	}
	
	public static boolean isClustroid(String word) {
		return LexC.clustroids.get(LexNorm.clusterAssignment.get(word)).equals(word) ? true:false;
	}
	
	private static boolean isContextCorrect(String word1, String word2) {
		if((LexNorm.prevWordMap.get(word1).size()<LexNorm.contextSize) || (LexNorm.prevWordMap.get(word2).size()<LexNorm.contextSize))
			return false;
		else if((LexNorm.nextWordMap.get(word1).size()<LexNorm.contextSize) || (LexNorm.nextWordMap.get(word2).size()<LexNorm.contextSize))
			return false;
		return true;
	}
	
	public static String round(double x){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(x);
	}
	
	public static void setConstants() {
		Constants.wordCount = LexNorm.wordList.size();
		Constants.clusterCount = LexNorm.clusters.keySet().size();
		Constants.stopingCriteria1 = (int)(0.05*Constants.wordCount);
		Constants.stopingCriteria2 = 20;
	}
}
