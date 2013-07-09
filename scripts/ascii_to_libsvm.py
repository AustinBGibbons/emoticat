import sys

fd = open(sys.argv[1])
of = open(sys.argv[2], 'w+')

for line in fd:
  line = line.rstrip('\n').split()
  if '.' in line[0]:
    print >>of, ' '.join([str(index)+':'+elem for (index, elem) in zip(range(1, 1+len(line)), line) ])
  else :
    print >>of, line[0],
    print >>of, ' '.join([str(index)+':'+elem for (index, elem) in zip(range(1, len(line)), line[1:]) ])
