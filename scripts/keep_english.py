#!/usr/bin/python
import sys

FIELDS_LIST = ['TweetID', 'DateTime', 'UserID', 'UserLocation', 'B1', 'B2', 'Text', 'Language']
NUM_FIELDS = len(FIELDS_LIST)
FIELDS = dict((line, i) for i, line in zip(range(0, NUM_FIELDS), FIELDS_LIST))

for line in iter(sys.stdin.readline, "") :
  fields = line.rstrip("\n").split("\t")
  if len(fields) != NUM_FIELDS and len(fields) != NUM_FIELDS-1:
    print >>sys.stderr, "malformed line : " + line, "has", len(fields), "instead of", NUM_FIELDS
    #sys.exit(1)
  elif fields[-1] == 'en' : print fields[-2]
