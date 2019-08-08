**************************************

How to use for Input Generation

**************************************
	
2) Generating Files for Java Code
	copy a complete dataset file to "datacorpus" folder(create new) inside UrduPhone folder
	File = main.py
	Python Library used for Pre Processing:
			  sent_tokenize (from nltk import sent_tokenize)
			  WordPunctTokenizer (from nltk.tokenize import WordPunctTokenizer)

	Input Files:
		Gold standarad = Gold Standard.txt
		For context information of words = complete dataset file (SMS_small dataset file provided as example)

	Output Files will be:
		UrduPhone.txt
		UrduPhone - ID.txt
		Word List.txt
		Frequent WordList.txt
		5 Next Word ID List.txt
		5 Next UrduPhone ID List.txt
		5 Previous Word ID List.txt
		5 Previous UrduPhone ID List.txt
