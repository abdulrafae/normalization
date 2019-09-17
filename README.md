# Lexical Normalization

This project finds lexical normalization from a dataset and evaluates the performance compared to a gold standard lexicon.

# Preprocessing and Normalization

Preprocess the raw data using the gold standard file
```
mkdir -p UrduPhone/Input\ Files/
mkdir -p UrduPhone/Output\ Files/
cp gold_standard UrduPhone/Input\ Files/Gold\ Standard.txt
cp raw_data UrduPhone/Input\ Files/Dataset.txt
cd UrduPhone
python main.py
```
Copy these preporcessed files and the gold standard file to Lexical Normalization project
```
cd ../
mkdir -p Experiments\ &\ Evaluation/Input\ Files/Default/
cp UrduPhone/Output\ Files/* Experiments\ &\ Evaluation/Input\ Files/Default/
cp UrduPhone/Input\ Files/Gold\ Standard.txt Experiments\ &\ Evaluation/Input\ Files/Default/
```
Import the Lexical Normalization project in a JAVA IDE (e.g. Eclipse) and run
```
Build and Run file src/frontend/LexNorm.java
```


# Paper
Abdul et. al. "A Clustering Framework for Lexical Normalization of Roman Urdu", *Journal of Natural Language Engineering (accepted)*, 2019
