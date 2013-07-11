#!/bin/bash

mkdir ../Data/Twitter/Final/
rm -rf ../Data/Twitter/Final/liblinear_labels/
mkdir ../Data/Twitter/Final/liblinear_labels/

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/liblinear/funny/ ../Data/Twitter/Final/liblinear_labels/funny

#./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/liblinear/afraid/ ../Data/Twitter/Final/liblinear_labels/afraid

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/liblinear/angry/ ../Data/Twitter/Final/liblinear_labels/angry

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/liblinear/happy/ ../Data/Twitter/Final/liblinear_labels/happy

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/liblinear/hopeful/ ../Data/Twitter/Final/liblinear_labels/hopeful

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/liblinear/mocking/ ../Data/Twitter/Final/liblinear_labels/mocking

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/liblinear/none/ ../Data/Twitter/Final/liblinear_labels/none

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/liblinear/sad/ ../Data/Twitter/Final/liblinear_labels/sad
