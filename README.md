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

  The matlab script can be run with :

    > matlab
    > svm_learn train_set.ascii learn_set.ascii output_labels.txt

Testing :
  
  We are using scalatest for testing. From the root directory :

     > sbt test
  Should run and compile all test. You can also test within a specific project, 
  or use test-only to call a specific test.

Todo :

  - Training Pipeline
  - Develop Pipeline
  - Test Pipeline
  - Production Pipeline
  - Streaming Support
  - Documentation
  - Test Cases
  - Maven for external jar/lib handling
