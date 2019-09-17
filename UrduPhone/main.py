import UrduPhone as phonetics
from nltk import sent_tokenize
from nltk.tokenize import WordPunctTokenizer

def get_wordslist():
	# getting words list that in standard corpus
	ff=open("./Output Files/Default - Word List.txt","r") 
	text=ff.read(); text = text.split() ; ff.close()
	words= text
	
	ff=open("./Input Files/Gold Standard.txt","r")    # comma seprated words
	text=ff.read(); text = text.split() ; ff.close()
	strwords=[]
	for t in text:
		strwords.append(t.split(",")[0])
		strwords.append(t.split(",")[1])
		
	new_list = set(words).intersection(set(strwords))
	print ("wordlist count = ",len(new_list))
	new_list = list(new_list)
	new_list.sort()
	return new_list

def sound_codes(arr):
    ''' function input is a list , function return dictionary'''
    ''' key of dictionay is UrduPhone Code, and word is all words in input list with key sound code.'''
    ''' words are seprated by comma'''	
    dict_sounds = dict()
    for element in arr:
         sound_code = phonetics.UrduPhone(element)
         try:
             dict_sounds[sound_code] = dict_sounds[sound_code]+","+element 
         except KeyError:
             dict_sounds[sound_code] = element
    return dict_sounds
def get_words_and_write_UrduPhone():
	words = get_wordslist()
	UrduPhone_dict  = sound_codes(words)
	i=0
	UrduPhone_ids = dict()
	for key in UrduPhone_dict.keys():
		UrduPhone_ids[key]=i
		i+=1
	ff=open("./Output Files/Default - UrduPhone - ID.txt","w")
	f2=open("./Output Files/Default - UrduPhone.txt","w")
	for w in words:
		UrduPhone = phonetics.UrduPhone(w)
		ff.write(w+","+UrduPhone+","+str(UrduPhone_ids[UrduPhone])+"\n")
		f2.write(w+","+UrduPhone+"\n")
	f2.close()	
	ff.close()
def getText():
	ff = open("./Input Files/Dataset.txt","r")
	text = ff.read()
	ff.close()

	text = text.lower()
	return text
def is_ascii(s):
    return all(ord(c) < 128 for c in s)
def BigramWords():
	text = getText()
	text = text.replace("\n",".")
	text = sent_tokenize(text)
	bigrams = dict()
	for t in text:
		words = WordPunctTokenizer().tokenize(t)
		i=0
		while i<len(words)-1:
			if is_ascii(words[i]) and is_ascii(words[i+1]):
				try:bigrams[words[i]+","+words[i+1]] +=1
				except: bigrams[words[i]+","+words[i+1]] =1
			i+=1
	ff=open("./Output Files/bigram.txt","w")
	for key, val in bigrams.items():
		ff.write(key+","+str(val)+"\n")
	ff.close
	
def words_ids(arr):
	i=1
	d=dict()
	for w in arr:
		d[w]=i
		i+=1
	return d
def _5NextList():
	ff=open("./Output Files/bigram.txt","r")
	text = ff.read() ; text = text.split("\n")
	ff.close()
	ff=open("./Output Files/Default - 5  Next Word ID List.txt","w")
	
	
	words = get_wordslist()
	ids = words_ids(words)
	for w in words:
		print(w)
		nextdict = dict()
		for t in text:
			t=t.split(",")
			if len(t)>1 and t[0] == w:
				if t[1].isalpha():
					nextdict[t[1]]=int(t[2])
		# sort dict
		#print len(nextdict.keys())
		nextdict2 =sorted(nextdict.items(), key=lambda x: x[1],reverse=True)
		i=0
		
		if len(nextdict2)>0:
			ff.write(w)
			while i<5 and i<len(nextdict2):
				try:
					ff.write(","+str(ids[nextdict2[i][0]]))
				except: pass
				i+=1
			ff.write("\n")
	ff.close()
	
def _5PrevList():
	ff=open("./Output Files/bigram.txt","r")
	text = ff.read() ; text = text.split("\n")
	ff.close()
	ff=open("./Output Files/5  Prev Word ID List.txt","w")
	
	
	words = get_wordslist()
	ids = words_ids(words)
	for w in words:
		print(w)
		nextdict = dict()
		for t in text:
			#print t
			t=t.split(",")
			if len(t)>1 and t[1] == w:
				if t[0].isalpha():
					nextdict[t[0]]=int(t[2])
		# sort dict
		#print len(nextdict.keys())
		nextdict2 =sorted(nextdict.items(), key=lambda x: x[1],reverse=True)
		i=0
		
		if len(nextdict2)>0:
			ff.write(w)
			while i<5 and i<len(nextdict2):
				try:
					ff.write(","+str(ids[nextdict2[i][0]]))
				except: pass
				i+=1
			ff.write("\n")
	ff.close()
	ff.close()
def Write_words_IDs():
	
	words = get_wordslist()
	ids = words_ids(words)
	ff=open("./Output Files/Default - Word List.txt","w")
	for key, val in ids.items():
		ff.write(key+" "+str(val)+"\n")
	ff.close
def PreProcess5Files():
	ff=open("./Output Files/Default - 5 Previous Word ID List.txt","r")
	text = ff.read(); ff.close()
	text = text.split("\n")
	ff=open("./Output Files/Default - 5 Previous Word ID List.txt","w")
	for t in text:
		if t.find(",")!=-1:
			ff.write(t+"\n")
	ff.close()
	
	ff=open("./Output Files/Default - 5 Next Word ID List.txt","r")
	text = ff.read(); ff.close()
	text = text.split("\n")
	ff=open("./Output Files/Default - 5 Next Word ID List.txt","w")
	for t in text:
		if t.find(",")!=-1:
			ff.write(t+"\n")
	ff.close()
def getUrduPhoneIds():
	ff = open("./Output Files/Default - UrduPhone - ID.txt", "r")
	text = ff.read(); ff.close()
	ids = dict()
	text = text.split()
	for t in text:
		words = t.split(",")
		ids[words[1]] = words[2]
	return ids
def getWordsIds():

	ff = open("./Output Files/Default - Word List.txt", "r")
	text = ff.read(); ff.close()
	ids = dict()
	text = text.split("\n")
	text.remove('')
	for t in text:
		words = t.split()
		ids[words[1]] = words[0]
	return ids
def getUrduPhoneEncodings():
	ff = open("./Output Files/Default - UrduPhone - ID.txt", "r")
	text = ff.read(); ff.close()
	ids = dict()
	text = text.split()
	for t in text:
		words = t.split(",")
		ids[words[0]] = words[1]
	return ids
	
def PreProcess5FilesForUrduPhone():
	UrduPhone_ids  = getUrduPhoneIds()
	UrduPhone_Encodings = getUrduPhoneEncodings()
	words_ids = getWordsIds()
	
	ff=open("./Output Files/Default - 5 Prev Word ID List.txt","r")
	text = ff.read(); ff.close()
	text = text.split("\n")
	ff=open("./Output Files/Default - 5 Prev UrduPhone ID List.txt","w")
	for t in text:
		words = t.split(",")
		ff.write(words[0])
		i=1
		while i<len(words):
			ff.write(","+UrduPhone_ids [ UrduPhone_Encodings[words_ids[ words[i]  ]] ])
			i+=1
		ff.write("\n")
	ff.close()

	ff=open("./Output Files/Default - 5 Next Word ID List.txt","r")
	text = ff.read(); ff.close()
	text = text.split("\n")
	ff=open("./Output Files/Default - 5 Next UrduPhone ID List.txt","w")
	for t in text:
		words = t.split(",")
		ff.write(words[0])
		i=1
		while i<len(words):
			ff.write(","+UrduPhone_ids [ UrduPhone_Encodings[words_ids[ words[i]  ]] ])
			i+=1
		ff.write("\n")
	ff.close()
def write_UrduPhone_clusters():
	ff=open("./Output Files/Default - UrduPhone_clusters.txt","w")
	words = get_wordslist()
	UrduPhone_dict  = sound_codes(words)
	for key,val in UrduPhone_dict.items():
		ff.write(key+"#"+val+"\n")
	ff.close()

def isAlphNum(word):
	for i in word:
		if (i>='A' and i<='Z') or (i>='a' and i<='z') or (i>='0' and i<='9'):
			pass
		else:
			return False
	return True

def WordList():
	text = getText()
	text = text.replace("\n",".")
	text = sent_tokenize(text)
	unigrams = dict()
	for t in text:
		words = WordPunctTokenizer().tokenize(t)
		i=0
		while i<len(words)-1:
			if is_ascii(words[i]) and is_ascii(words[i+1]):
				try:unigrams[words[i]] +=1
				except: unigrams[words[i]] =1
			i+=1
	
	ff=open("./Output Files/Default - Word List.txt","w")
	for key, val in unigrams.items():
		ff.write(key+"\n")
	ff.close
	
	ff=open("./Output Files/Default - Frequent Word List.txt","w")
	for key, val in unigrams.items():
		if val>=5:
			ff.write(key+"\n")
	ff.close
	
if __name__=="__main__":
	WordList()  #function will generate 'bigram.txt' using corpus data , this file is required in other functions 
	get_words_and_write_UrduPhone()  # this function will generate "UrduPhone.txt" (word,encoding) and 'UrduPhone - ID.txt' (word, UrduPhone, UrduPhoneID)
	Write_words_IDs()    #function will create 'Word List.txt' (word, word_id) 

	#BigramWords()  #function will generate 'bigram.txt' using corpus data , this file is required in other functions 
	#_5NextList()  # Function will gernerte '5 Next Word ID List.txt' 
	#_5PrevList()   # Function will gernerte '5 Previous Word ID List.txt' 
	#PreProcess5Files()  # Removing words with no context information in '5 Previous Word ID List.txt' , 5 Next Word ID List.txt 
	#PreProcess5FilesForUrduPhone() #function will generate '5 Next UrduPhone ID List.txt' and '5 Previous UrduPhone ID List.txt' , using '5 Next Word ID List.txt',5 Previous Word ID List.txt '''
	write_UrduPhone_clusters()
	print("Files are ready for KMeans!")
