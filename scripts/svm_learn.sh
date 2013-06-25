#!/bin/sh

/usr/local/bin/matlab -nodesktop -nosplash -r "svm_learn('$1','$2','$3'); exit;"
