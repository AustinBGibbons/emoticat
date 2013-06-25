
mkdir $3

for f in `find $2 -name 'part-*'`
do
  `python delete_short.py $f temp`
  ./svm_learn.sh $1 "temp" "$3/${f##*/}"
done

rm temp
