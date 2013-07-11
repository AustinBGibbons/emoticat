#!/bin/bash

mkdir ../Data/Twitter/Learned_Labels/
rm -rf ../Data/Twitter/Learned_Labels/libsvm/
mkdir ../Data/Twitter/Learned_Labels/libsvm/

echo "angry"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/angry.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm/angry

echo "happy"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/happy.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm/happy

echo "sad"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/sad.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm/sad

echo "mocking"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/mocking.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm/mocking

echo "none"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/none.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm/none

echo "hopeful"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/hopeful.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm/hopeful

echo "funny"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/funny.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm/funny
