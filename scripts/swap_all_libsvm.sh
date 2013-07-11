#!/bin/bash

mkdir ../Data/Twitter/Final/
rm -rf ../Data/Twitter/Final/libsvm_labels/
mkdir ../Data/Twitter/Final/libsvm_labels/

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/libsvm/funny/ ../Data/Twitter/Final/libsvm_labels/funny

#./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/libsvm/afraid/ ../Data/Twitter/Final/libsvm_labels/afraid

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/libsvm/angry/ ../Data/Twitter/Final/libsvm_labels/angry

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/libsvm/happy/ ../Data/Twitter/Final/libsvm_labels/happy

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/libsvm/hopeful/ ../Data/Twitter/Final/libsvm_labels/hopeful

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/libsvm/mocking/ ../Data/Twitter/Final/libsvm_labels/mocking

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/libsvm/none/ ../Data/Twitter/Final/libsvm_labels/none

./directory_swap.sh ../Data/Twitter/Processed/Polarity_Time_Tweet/ ../Data/Twitter/Learned_Labels/libsvm/sad/ ../Data/Twitter/Final/libsvm_labels/sad
