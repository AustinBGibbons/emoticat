emoticat
========

Emotion Categorization

Running :
  We are using sbt. If you have trouble with the dependencies or any general questions,
  contact Ausitn

     > sbt
     > compile
     > project
     > run
    
  As an example

     > sbt
     > compile
     > project polarity
     > run-main main.scala.PolarityDistribution Tweet-Data/Tiny-Labeled.csv Tweet-Data/Tiny-Polarity.ser
     > run-main main.scala.CalculatePolarities -s Tweet-Data/Tiny-Polarity.ser Tweet-Data/Tiny-Unlabeled.csv Tweet-Data/Tiny-features.txt

If you are running a very large dataset, you should modify your sbt configuration to increase RAM, ie,

```
#!/bin/sh
test -f ~/.sbtconfig && . ~/.sbtconfig
exec java -Xmx400G -Xms20G -XX:MaxPermSize=40G ${SBT_OPTS} -jar ~/bin/sbt-launch.jar "$@"
```

Todo : describe how to run liblinear / libsvm

Testing :
  
  We are using scalatest for testing. From the root directory :

     > sbt test
     
  Should run and compile all test. You can also test within a specific project, 
  or use test-only to call a specific test.

Todo :

  - Unit Tests
  - Streaming Support
  - Documentation Auto-generation
  - Maven for external jar/lib handling
