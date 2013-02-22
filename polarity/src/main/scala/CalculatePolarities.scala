package main.scala

import spark._
import spark.SparkContext._

import io.Source

import breeze.linalg.Counter

/**
*   Give natural language text and a mapping from string tokens to floats (polarities)  
*   Calculate the distribution over the labels
*/
class CalculatePolarities(sc: SparkContext, polarities: Map[String, Polarity]) extends Base {
  // executes the calculation
  def compute(samples: RDD[String]) : RDD[Array[Float]] = {
    val pd = new PolarityDistribution()
    samples map (sample => {
      val labelCounts = Counter[String, Float]()
      pd.tokenize(sample) foreach (token => {
      println(token)
        if (polarities.contains(token)) {
          polarities.get(token).get foreach{case(label, value) => labelCounts(label) += value}
        }  
      })
      normalize(labelCounts.valuesIterator.toArray) // TODO : map to specific sized and indexed array
    })
  }

  // create each 1-v-all feature set
  def compute(labels: List[String], samples: RDD[Sample]) : List[RDD[PolarExample]] = {
    List()
  }

  // normalize an array of floats
  def normalize(arr: Array[Float]) : Array[Float] = {
    val sum = arr.reduce(_+_)
    arr map (x => x/sum)
  }
}

object CalculatePolarities extends Base {
  import java.io._

  /** 
  * Read in the serialized polarities map.
  * overriding resolveClass due to an error in how scala/sbt search for classes
  *
  * @param fileName : input File (map.ser)
  * @return : deserialized map
  */
  def readSerializedPolarities(fileName : String) : Map[String, Polarity] = {
    val ois = new ObjectInputStream(new FileInputStream(fileName)) {
      override def resolveClass(desc: java.io.ObjectStreamClass): Class[_] = {
        try { Class.forName(desc.getName, false, getClass.getClassLoader) }
        catch { case ex: ClassNotFoundException => super.resolveClass(desc) }
      }
    }
    val polarities = ois.readObject() match {
      case m : Map[String, Polarity] => m
      case _ => throw new ClassCastException
    }
    ois.close();
    polarities
  }

  /**
  *   Write out the featureMatrix as space separated text
  *
  * @param filename : outputFile
  * @param featureMatrix : row are samples, columns are labels
  */
  def writeOutput(fileName: String, featureMatrix : Iterable[Array[Float]]) {
    val pw = new PrintWriter(new FileWriter(fileName))
    featureMatrix foreach {row =>
      row foreach {col =>
        pw.print(col.toString + " ")
      }
      pw.println()
    }
    pw.close()
  }

  def main(args: Array[String]) {
    if(args.length != 4 || args(0).length != 2) {
      goodbye("Usage: \n\t run-main main.scala.CalculatePolarities -s polarities.ser examples.txt output.mat\n"
      + "\t run-main main.scala.CalculatePolarities -d labeled-text.csv examples.txt output.mat")
    }
  
    val sc = new SparkContext("local", "caluclating polarities on new data")
    val polarities : Map[String, Polarity] = 
      if (args(0) == "-s") {
        readSerializedPolarities(args(1))
      }
      else {
        val pd = new PolarityDistribution()
         pd.generateDistributionFromFile(args(1))
      }
    val samples = sc.parallelize(Source.fromFile(args(2)).getLines().toList)
    val cp = new CalculatePolarities(sc, polarities)
    writeOutput(args(3), cp.compute(samples).collect())
  }
}
