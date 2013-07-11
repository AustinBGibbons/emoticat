
mkdir $3

for f in `find $2 -name 'part-*'`
do
  `python delete_short.py $f temp`
  liblinear-1.93/predict "temp" $1 "$3/${f##*/}"
done

rm temp
