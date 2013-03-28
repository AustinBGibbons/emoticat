import sys

fd = open(sys.argv[1])

leng = -1

for line in fd :
  line = line.rstrip("\n").split(" ")
  if(leng < 0) : 
    leng = len(line)
  if(len(line) == leng) : print(" ".join(line))

