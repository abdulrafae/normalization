# Folder structure

*****Input Files folder has the input files from all 4 dataset along with the gold standard file.

	>SMS_small Folder contains Input files, generated from the SMS (small) dataset, to Java Program. Just change the parameter dataset in LexNorm.java file to read these.

	>SMS_large Folder contains Input files, generated from the SMS (large) dataset, to Java Program. Just change the parameter dataset in LexNorm.java file to read these.

	>CFMP Folder contains Input files, generated from the CFMP dataset, to Java Program. Just change the parameter dataset in LexNorm.java file to read these.

	>Web Folder contains Input files, generated from the Web scrapped data, to Java Program. Just change the parameter dataset in LexNorm.java file to read these.

	>Gold Standard.txt file contains the gold standard mapping. Format is <data_word>,<standard_mapping>

*****Result Files folder has the final clusters from the Java Code. The cluster are written with each line representing a seperate cluster

*****Assignment Files folder has the assignment clusters from the Java Code. The assignments for each word are written as <word>,<standard cluster ID>,<predicted cluster ID>
	
**************************************
Input Files structure and definitions:
1) Prev/Next Word ID List
	Word with top 5 Word IDs for prev/next words. The format is <word>,<contex_word_id1>,...,<context_word_id5>
2) Prev/Next Soundex ID List
	Word with top 5 Soundex IDs for prev/next words. The format is <word>,<context_soundex_id1>,...,<context_soundex_id5>
5) Soundex - ID
	Word with its soundex mapping and ID for that mappping. The format is <word>,<soundex_code><soundex_id>
7)Word List
	Word List of all the words
7)Frequent Word List
	Word List of frequent words	
