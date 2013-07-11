#!/bin/bash

mkdir ../Data/Twitter/Learned_Labels/
rm -rf ../Data/Twitter/Learned_Labels/liblinear/
mkdir ../Data/Twitter/Learned_Labels/liblinear/


echo "angry"
time ./linear_learn_liblinear_directory.sh ../Data/Tweet-New-Data/liblinear/Obamney-Labeled-Polarities/angry.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/liblinear/angry

echo "happy"
time ./linear_learn_liblinear_directory.sh ../Data/Tweet-New-Data/liblinear/Obamney-Labeled-Polarities/happy.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/liblinear/happy

echo "sad"
time ./linear_learn_liblinear_directory.sh ../Data/Tweet-New-Data/liblinear/Obamney-Labeled-Polarities/sad.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/liblinear/sad

echo "mocking"
time ./linear_learn_liblinear_directory.sh ../Data/Tweet-New-Data/liblinear/Obamney-Labeled-Polarities/mocking.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/liblinear/mocking

echo "none"
time ./linear_learn_liblinear_directory.sh ../Data/Tweet-New-Data/liblinear/Obamney-Labeled-Polarities/none.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/liblinear/none

echo "hopeful"
time ./linear_learn_liblinear_directory.sh ../Data/Tweet-New-Data/liblinear/Obamney-Labeled-Polarities/hopeful.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/liblinear/hopeful

echo "funny"
time ./linear_learn_liblinear_directory.sh ../Data/Tweet-New-Data/liblinear/Obamney-Labeled-Polarities/funny.mat/model ../Data/Twitter/Processed/Polarity ../Data/Twitter/Learned_Labels/liblinear/funny
