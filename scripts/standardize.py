import sys

fd = open(sys.argv[1])

for line in fd :
  line = line.rstrip('\n').split(',')
  for i in range(0, len(line)) :
    line[i] = line[i][1:len(line[i])-1]
  print line[1] +',' + line[3] + ',' + line[5]
