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
  val pd = new PolarityDistribution()

  // executes the calculation
  def compute(samples: RDD[String]) : RDD[Array[Float]] = {
    val polarity_count = pd.getLabelCount(polarities)
    val all_samples = samples map (sample => {
      compute(sample)
    }) 
    val filtered_samples = all_samples filter(x => x.size == polarity_count)
    println("\n\nfiltered: " + (all_samples.count() - filtered_samples.count()) + " / " + all_samples.count())
    filtered_samples
  }
  
  /**
  *   Take a natural language string and return
  *   the normalized sum of the polarity distributions of each token (word)
  *
  *   @param : sample : Natural language sample (ie, Tweet)
  *   @return : normalized vector of float
  */
  def compute(sample: String) : Array[Float] = {
    val labelCounts = Counter[String, Float]()
    //println("Trying: " + sample)
    pd.tokenize(sample) foreach (token => {
      if (polarities.contains(token)) {
        polarities.get(token).get foreach{case(label, value) => labelCounts(label) += value}
      }  
    })
    if (labelCounts.size == 0) Array()
    else normalize(labelCounts.valuesIterator.toArray) // TODO : map to specific sized and indexed array
  }

  // create each 1-v-all feature set
/*
  def compute(labels: List[String], samples: RDD[Sample]) : RDD[(String, Seq[PolarExample])] = {
    samples flatMap ( sample => {
      val featureArray = compute(sample.text)
      labels map ( label => {
        (label, (new PolarExample(if(sample.label.contains(label)) 1 else 0, featureArray)))
      })
    }) groupByKey()
  }
*/

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
    if(args.length < 4 || args(0).length != 2) {
      goodbye("Usage: \n\t run-main main.scala.CalculatePolarities -s polarities.ser examples.txt output.mat [-sep <sep>]\n"
      + "\t run-main main.scala.CalculatePolarities -d labeled-text.csv examples.txt output.mat [-sep <sep>]")
    }
  
    val sc = new SparkContext("local", "caluclating polarities on new data")
    val pd = new PolarityDistribution()
    val polarities : Map[String, Polarity] = 
      if (args(0) == "-s") {
        readSerializedPolarities(args(1))
      }
      else {
         pd.generateDistributionFromFile(args(1))
      }
    val sep = if (args.length >= 5) args(4) else "\t" // todo - check csv, tsv by default
    val samples = sc.parallelize(Source.fromFile(args(2)).getLines().map(x => x.split(sep).head).toList)
    val cp = new CalculatePolarities(sc, polarities)
    println(pd.getLabels(polarities).mkString("\t"))
    writeOutput(args(3), cp.compute(samples).collect())
  }
}
