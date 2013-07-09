
mkdir $3

for f in `find $2 -name 'part-*'`
do
  `python delete_short.py $f temp`
  libsvm-3.17/svm-predict "temp" $1 "$3/${f##*/}"
done

rm temp
