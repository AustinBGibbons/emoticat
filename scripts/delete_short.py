import sys

fd = open(sys.argv[1])
of = open(sys.argv[2], 'w+')

leng = -1

for line in fd :
  line = line.rstrip("\n").split(" ")
  if(leng < 0) : 
    leng = len(line)
  if(len(line) > leng) : print "Longer line after line 0 : ", line
  if(len(line) == leng) : print >>of, (" ".join(line))

