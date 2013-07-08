#!/bin/bash

echo "angry"
time ./svm_learn_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/angry.mat/part-00000 ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/matlab_out_angry

echo "happy"
time ./svm_learn_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/happy.mat/part-00000 ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/matlab_out_happy

echo "sad"
time ./svm_learn_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/sad.mat/part-00000 ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/matlab_out_sad

echo "mocking"
time ./svm_learn_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/mocking.mat/part-00000 ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/matlab_out_mocking

echo "none"
time ./svm_learn_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/none.mat/part-00000 ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/matlab_out_none

echo "hopeful"
time ./svm_learn_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/hopeful.mat/part-00000 ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/matlab_out_hopeful

echo "funny"
time ./svm_learn_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/funny.mat/part-00000 ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/matlab_out_funny
