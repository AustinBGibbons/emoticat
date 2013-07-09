#!/bin/bash

echo "angry"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/angry.mat/model ../Data/Twitter/LibSvm_Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm_out_angry

echo "happy"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/happy.mat/model ../Data/Twitter/LibSvm_Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm_out_happy

echo "sad"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/sad.mat/model ../Data/Twitter/LibSvm_Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm_out_sad

echo "mocking"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/mocking.mat/model ../Data/Twitter/LibSvm_Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm_out_mocking

echo "none"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/none.mat/model ../Data/Twitter/LibSvm_Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm_out_none

echo "hopeful"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/hopeful.mat/model ../Data/Twitter/LibSvm_Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm_out_hopeful

echo "funny"
time ./svm_learn_libsvm_directory.sh ../Data/Tweet-New-Data/libsvm/Obamney-Labeled-Polarities/funny.mat/model ../Data/Twitter/LibSvm_Processed/Polarity ../Data/Twitter/Learned_Labels/libsvm_out_funny
