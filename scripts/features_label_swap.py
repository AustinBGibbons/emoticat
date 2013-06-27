import sys

tweets = open(sys.argv[1]).read().splitlines()
labels = open(sys.argv[2]).read().splitlines()
of = open(sys.argv[3], 'w+')

for (tweet, label) in zip(tweets, labels) :
  t = tweet.split(',')
  t[0] = label
  print >>of,','.join(t)

