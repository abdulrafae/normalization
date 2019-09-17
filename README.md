# Lexical Normalization

```
mkdir -p UrduPhone/Input\ Files/
mkdir -p UrduPhone/Output\ Files/
cp gold_standard UrduPhone/Input\ Files/Gold\ Standard.txt
cp raw_data UrduPhone/Input\ Files/Dataset.txt
cd UrduPhone
python main.py
cd ../
mkdir -p Experiments\ &\ Evaluation/Input\ Files/Default/
cp UrduPhone/Output\ Files/* Experiments\ &\ Evaluation/Input\ Files/Default/
cp UrduPhone/Input\ Files/Gold\ Standard.txt Experiments\ &\ Evaluation/Input\ Files/Default/
Import project in a Java IDE and run src/frontend/LexNorm.java
```


# Paper
Abdul et. al. "A Clustering Framework for Lexical Normalization of Roman Urdu", *Journal of Natural Language Engineering (accepted)*, 2019
