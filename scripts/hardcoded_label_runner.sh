#!/bin/bash

echo "angry"
time ./run_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/angry.mat/part-00000 ../Data/Twitter-Super-Bowl/Processed/Polarity ../Data/Twitter-Super-Bowl/Learned_Labels/matlab_out_angry

echo "happy"
time ./run_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/happy.mat/part-00000 ../Data/Twitter-Super-Bowl/Processed/Polarity ../Data/Twitter-Super-Bowl/Learned_Labels/matlab_out_happy

echo "sad"
time ./run_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/sad.mat/part-00000 ../Data/Twitter-Super-Bowl/Processed/Polarity ../Data/Twitter-Super-Bowl/Learned_Labels/matlab_out_sad

echo "mocking"
time ./run_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/mocking.mat/part-00000 ../Data/Twitter-Super-Bowl/Processed/Polarity ../Data/Twitter-Super-Bowl/Learned_Labels/matlab_out_mocking

echo "none"
time ./run_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/none.mat/part-00000 ../Data/Twitter-Super-Bowl/Processed/Polarity ../Data/Twitter-Super-Bowl/Learned_Labels/matlab_out_none

echo "hopeful"
time ./run_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/hopeful.mat/part-00000 ../Data/Twitter-Super-Bowl/Processed/Polarity ../Data/Twitter-Super-Bowl/Learned_Labels/matlab_out_hopeful

echo "funny"
time ./run_directory.sh ../Data/Tweet-New-Data/06-24/Obamney-Labeled-Polarities/funny.mat/part-00000 ../Data/Twitter-Super-Bowl/Processed/Polarity ../Data/Twitter-Super-Bowl/Learned_Labels/matlab_out_funny
