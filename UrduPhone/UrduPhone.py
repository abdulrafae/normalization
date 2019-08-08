import string
import re
def remove1st(word):
	neww= word[0]
	i=1
	while i<len(word):
		
		if word[i]=='A' or word[i]=='I' or word[i]=='O' or word[i]=='U' or word[i]=='Y' or word[i]=='E':
			i+=1
		elif i>1 and word[i-1]=='A' or word[i-1]=='I' or word[i-1]=='O' or word[i-1]=='U' or word[i-1]=='Y' or word[i-1]=='E':
			return word
			#break;
		elif i==1:
			return word
		else:
			neww=neww+word[i:]
			break
	return neww
	
def find22(word):
	from_str  = ['BH', 'PH' ,'TH', 'JH','DH','RH','GH' ,'ZH', 'CH','SH','KH']
	to_str    = [ '{' ,'}', ':',    ';','~' ,'[', ']' , '(',  ')', '<',  '>' ]
	word = word.upper()
	#print word
	for i in range(len(from_str)):	word =word.replace(from_str[i],to_str[i])
	
	
	# word
	word = remove1st(word)
	i=1
	new_word = word[0]
	
#	print "Length: ",len(word)
	while i<(len(word)-1):
#		print "Loop: ",i,":",new_word
		x = word[i]
		if x=='A' or x=='I' or x=='O' or x=='U' or x=='Y' or x=='E':
			
			#print word[i-1] == word[i+1],word[i-1],word[i+1]
			if new_word[-1] == word[i+1]:
				i+=1
				new_word = new_word+word[i]
			else:	
				x= word[i] 
				while i<(len(word))  and (x=='A' or x=='I' or x=='O' or x=='U' or x=='Y' or x=='E'):
					i+=1
					try:x= word[i]
					except: break
				
				if x=='A' or x=='I' or x=='O' or x=='U' or x=='Y' or x=='E': 
					break
				if new_word[-1] == word[i]:
					new_word = new_word+word[i]
				else:new_word = new_word+word[i]
		elif new_word[-1]!=word[i]:
			new_word = new_word+word[i]
		i+=1
	x = word[len(word)-1]	
	if x=='A' or x=='I' or x=='O' or x=='U' or x=='Y' or x=='E':
		pass
	elif x!=new_word[-1]: new_word = new_word+x
	
	from_str  = ['BH', 'PH' ,'TH', 'JH','DH','RH','GH' ,'ZH', 'CH','SH','KH']
	to_str    = [ '{' ,'}', ':',    ';','~' ,'[', ']' , '(',  ')', '<',  '>' ]
	for i in range(len(from_str)):	new_word = new_word.replace(to_str[i],from_str[i])

	
	return new_word

def find( word):
	str2=word[0]
	a=False
	for x in word:
		if x=='A' or x=='I' or x=='O' or x=='U' or x=='Y' or x=='E':
			a=True 
			continue 
		if x == str2[-1] and a==True:
			str2 = str2 + x
		elif x != str2[-1]:
			str2 = str2 + x
		if x=='A' or x=='I' or x=='O' or x=='U' or x=='Y' or x=='E':
			a=True
		else:
			a=False
	return str2

def UrduPhone (term):
	if term[0]=='Q':             #replacing K with Q
		term = 'K'+term[1:len(term)]
	
	from_str  = ['BH', 'PH' ,'TH', 'JH','DH','RH','GH' ,'ZH', 'CH','C','SH','S','T','Z','X','J','KH','K','Q','D','B','P','N','M','G','R','F','L','W']
	to_str    = [ '/' ,';', ':',    '.',',' ,'[', ']' , '(',  '|', '1','1', '1','2','3','3','6','7', '8','8','9','!','@','#','$','%','&', '*', ')','-']
	
	from_str2 = ['A','I','O','Y','U','H','E']
	to_str2   = ['0','0','0','0','0','~','0']
	# please change this mapping when you change above
	from_to  = ['0','1' ,'2', '3',  '6',  '|', '7','8', '9',  '!', '@',  '#',  '$'   ,'%',  '&', '*',   '(', ')',   '-','~'   , '/' ,  ';' , ':',    '.', ',',    '[' , ']' ]
	to_new    = ['_0','_1','_2','_3', '_4','_5','_6','_7','_8', '_9','_10','_11','_12','_13','_14','_15','_16','_17','_18','_19','_20','_21','_22' ,'_23','_24',   '_25' , '_26']
	
	# check parameter
	if not term:
		return "0000" # could be Z000 for compatibility with other implementations
	# convert into uppercase letters
	term = term.upper()#string.upper(term)
#	term1 = find(term)
	term1=term
#	print term1
	term = find22(term1)
	# term1,term
	first_char = term[0]

	term1=term[1:]

	for i in range(len(from_str)):	term1 =term1.replace(from_str[i],to_str[i])
	for i in range(len(from_str2)) :term1 =term1.replace(from_str2[i],to_str2[i])
	
	term = term[0]+term1
	str2=term
	# remove all 0s
	'''
	term = string.replace(term, "0", "")
	# remove duplicate numbers in-a-row
	str2 = first_char
	#str2 = term
	for x in term:
		if x != str2[-1]:
			str2 = str2 + x
		# end if
	# end for '''

	# pad with zeros
	str2 = str2+"0000"

	# take the first four letters
	return_value = str2[:6]
#	return_value = str2
		
	# return value
	for i in range(len(from_to)): return_value =return_value.replace(from_to[i],to_new[i])
	
	return return_value

