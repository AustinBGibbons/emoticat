package main.scala

import spark._
import spark.SparkContext._

import breeze.linalg._
import breeze.classify._

import breeze.data.Example

/*
* For each label, we train a 1-v-all classifier
*/
class TrainSVM(sc: SparkContext, polarities: Map[String, Polarity], samples: List[Sample]) extends Base {

  def oneVsAll() : List[Array[Example[Int, Vector[Float]]]] = {
  
    List()
  }
}

object TrainSVM extends Base {
  def main(args: Array[String]) {
    if(args.length < 1 || args(0).length != 2) {
      goodbye("Usage: run-main main.scala.TrainSVM -c labeled-data.csv support-vectors.sv\n")
                  // + "run-main main.scala.TrainSVM -d labeled-text.csv support-vectors.sv")
    }

    val sc = new SparkContext("local", "caluclating polarities on new data")

    val samples = sc.parallelize(readLabeledCSV(args(1))) map (x => x.text)
    val pd = new PolarityDistribution()
    val polarities = pd.generateDistributionFromFile(args(1))
    //val cp = new CalculatePolarities(sc, polarities, samples)
   // val featureArray = cp.compute().collect()
  }
}
