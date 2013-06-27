import sys, glob, re

keywords = [line.rstrip('\n').split(",") for line in open(sys.argv[1]).readlines()]
times = [line.rstrip('\n') for line in open(sys.argv[2]).readlines()]
tweets = glob.glob(sys.argv[3] + '/part-*')

of = open(sys.argv[4], 'w+')

keyword_counts = {}

def what_time_is_it(t):
  if t < times[0] or t > times[-1] : return -1
  closest = -1
  for time in times :
    if t > time : closest = time
  return closest

for f in tweets :
  fd = open(f)
  lines = [x.rstrip('\n').lower().split(",") for x in fd.readlines()]
  #binary_label,time,tweet
  # def proc
  for line in lines :
    if line[0] == 0: continue
    t = what_time_is_it(line[1])
    if t == -1 : continue
    words = [re.sub(r'\W+', '', w) for w in line[2].split(" ")]
    for k_list in keywords :
      for k in k_list :
        if k in line :
          if (k_list[0], t) not in keyword_counts :
            keyword_counts[(k_list[0],t)] = 0
          keyword_counts[(k_list[0],t)]+=1
          break # what do you break out of

for k in keyword_counts :
  print >>of, k, keyword_counts[k]

