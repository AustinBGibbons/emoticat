package main.scala

import spark._
import spark.SparkContext._

import io.Source

//import breeze.linalg.Counter

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
 //   all_samples foreach (x => if (x.size != polarity_count) println("I had : " + x.size + " instead of " + polarity_count))
    val filtered_samples = all_samples filter(x => x.size == polarity_count)
    println("\nfiltered: " + (all_samples.count() - filtered_samples.count()) + " / " + all_samples.count() + "\n")
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
    val labelCounts = scala.collection.mutable.HashMap[String, Float]() 
    pd.tokenize(sample) foreach (token => {
      if (polarities.contains(token)) {
        polarities.get(token).get foreach{case(label, value) => 
          //println("adding: " + value + " from " + token + " to " + label)
          labelCounts.put(label, labelCounts.getOrElse(label, 0f)+value)
        }
      }  
    })
    if (labelCounts.size == 0) Array()
    else normalize(labelCounts.toSeq.sortBy(_._1).map(_._2).toArray) // TODO : map to specific sized and indexed array
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
  def writeUnlabeledOutput(fileName: String, featureMatrix : RDD[Array[Float]]) {
    featureMatrix.map(_.mkString(" ")).saveAsTextFile(fileName)
  }

  /**
  *   Write out the featureMatrix as space separated text, preceded by label being present
  *
  * @param directory      : output directory to contain /label.mat
  * @param featureMatrix  : row are samples, columns are labels
  * @param sample_lables  : labels corresponding to each row
  * @param labels         : total set of all labels
  */
  def writeLabeledOutput(directory: String, featureMatrix: RDD[Array[Float]], sample_labels: RDD[Array[String]], labels: Seq[String]) = {
    labels foreach (label => {
      val f : ((Array[String], Array[Float])) => String = {
        case (sample_label, row) => {
          val sample_bit = if (sample_label.contains(label)) 1 else 0
          sample_bit.toString + " " + row.mkString(" ")
        }
      }
      sample_labels.zip(featureMatrix).map(f).saveAsTextFile(directory + "/" + label + ".mat")
    })
    writeUnlabeledOutput(directory + "/unlabeled.mat", featureMatrix)
  }

  def main(args: Array[String]) {
    if(args.length < 4 || args(0).length != 2) {
      goodbye("Usage: \n\t run-main main.scala.CalculatePolarities -s polarities.ser examples.txt output.mat [-sep <sep>]\n"
      + "\t run-main main.scala.CalculatePolarities -d labeled-text.csv examples.txt output.mat [-sep {',','t'}]")
    }
  
    val sc = new SparkContext("local", "caluclating polarities on new data")
    val pd = new PolarityDistribution()
    val sep = if(args.length < 6 || args(5) == "t") "\t" else args(5)
    val polarities : Map[String, Polarity] = 
      if (args(0) == "-s") readSerializedPolarities(args(1))
      else pd.generateDistributionFromFile(args(1), sep)
    val labels = pd.getLabels(polarities)

    val rows = sc.textFile(args(2)).map(_.split(sep)).persist()
    val samples = rows.map(_.head)
    val canary = rows.take(1).apply(0)

    val cp = new CalculatePolarities(sc, polarities)
    val polarity_distributions = cp.compute(samples).persist()
 
    if(canary.size > 1) {
      val sample_labels = rows.map(_.drop(1))
      writeLabeledOutput(args(3), polarity_distributions, sample_labels, labels)
    } else {
      writeUnlabeledOutput(args(3), polarity_distributions)
    }
    println("\nThis is what your row looks like : \n" + canary.mkString(sep))
    println("\nThis is what a tweet looks like : \n" + canary.head)
    if (canary.size > 1) println("\nColumns: " + labels.sorted.mkString(sep) +"\n")
  }
}
