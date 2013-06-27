
mkdir $3

for f in `find $1 -name 'part-*'`
do
  n=${f##*/}
  `python delete_short.py $f swap_temp`
  `python features_label_swap.py swap_temp $2/$n $3/$n`
done

rm swap_temp
