package main.scala

import spark._
import spark.SparkContext._
/*
import breeze.linalg._
import breeze.classify._

import breeze.data.Example
*/
/*
* For each label, we train a 1-v-all classifier
*/
class TrainSVM(sc: SparkContext) extends Base {

/*
  // Support generic sequences
  def train(samples : Seq[PolarExample]) : Classifier[Int, Array[Float]] = {
    //SVM(samples)
    null
  }

  def train(samples : RDD[PolarExample]) : Classifier[Int, Array[Float]] = {
    train(samples.collect())
  }
*/
}

object TrainSVM extends Base {
  def main(args: Array[String]) {
    if(args.length < 1 || args(0).length != 2) {
      goodbye("Usage: run-main main.scala.TrainSVM -c labeled-data.csv support-vectors.sv\n")
                  // + "run-main main.scala.TrainSVM -d labeled-text.csv support-vectors.sv")
    }

    val sc = new SparkContext("local", "caluclating polarities on new data")

    val samples = sc.parallelize(readLabeledFile(args(1))) 
    val labels = samples flatMap (x => x.label) distinct() collect() toList
    val pd = new PolarityDistribution()
    val polarities = pd.generateDistributionFromFile(args(2))
    val cp = new CalculatePolarities(sc, polarities)
    //val featureMatrices = cp.compute(labels, samples).collect().toList
    //featureMatrices foreach {x => println(x._1) ; x._2 foreach (println)}
    //featureMatrices foreach (x => printMatrix(x, args(3)))

  }
/*
  def printMatrix(x : (String, Seq[PolarExample]), dir: String) { 
    val fileName = dir+"/"+x._1+".mat"
    val file = new java.io.File(fileName);
    val parent_directory = file.getParentFile();

    if (null != parent_directory) {
      parent_directory.mkdirs();
    }

    val pw = new java.io.PrintWriter(new java.io.FileWriter(file))
    x._2 foreach (y => pw.write(y.toString + "\n"))
    pw.close()
  }
*/
}
