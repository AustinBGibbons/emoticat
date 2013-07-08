package main.scala

import io.Source
import collection.immutable._

import breeze.text.tokenize.PTBTokenizer
import breeze.text.analyze.PorterStemmer
import java.util.regex.Pattern

import java.io.FileWriter
import java.io.FileOutputStream
import java.io.ObjectOutputStream

/**
*   @author : Austin Gibbons
*   @date : 2/15/2013
*   @param : InputFile.csv
*   @param : OutputFile.ser
*
*   Take in a csv with format
*   Text,Label1,Label2,...,LabelN
*   And produce a serialized CounterMap
*   (String,String) --> Float
*   (Token, Label)  --> Normalized frequency
*
*   Example : todo
*/
class PolarityDistribution extends Base {

  protected final val STOP_LIST = Set("rt", "a", "the", "...")
  protected final val URL_PATTERN =
    Pattern.compile("""\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]""")
  protected final val URL_TOKEN = "<URL>"

  /**
  *   Takes in unstructured, splittable text
  *   and returns a list of strings :
  *   tokenized, stemmed, and 1/2/3 ngrams
  *
  *   @param sample : whitespace separated natural language text
  *   @return : tokenization of the sample
  */
  def tokenize(sample: String) : List[String] = {
    val tokenized = PTBTokenizer(sample.toLowerCase)
    val stemmed = tokenized.map( word => (new PorterStemmer)(word) ) toList
    val pruned = transforms(stemmed)  // Todo - a more robust ngrams solution
    val doubles = for(i <- 0 until pruned.length-1) yield pruned(i) + pruned(i+1)
    val triples = for(i <- 0 until pruned.length-2) yield pruned(i) + pruned(i+1) + pruned(i+2)
    pruned ++ doubles ++ triples
  }

  /**
   * Text transforms
   *
   * - stop word filtering
   * - normalizing urls to "<URL>"
   *
   * @param tokens
   * @return
   */
  def transforms(tokens : List[String]) : List[String] = {
    tokens
      .filter( tok => !(STOP_LIST contains tok) )
      .map(tok => if (URL_PATTERN.matcher(tok).matches()) URL_TOKEN else tok)
      .map(tok => stripPunctuation(tok))
  }

  /**
  * @param some input string
  * @return if the string had characters, those characters without punctuation. otherwise, the string.
  */
  def stripPunctuation(input :String) : String = {
    if(("\\w".r findFirstIn input) == None) return input;
    else return input.replaceAll("[^a-z\\sA-Z]","");
  }

  // Take in many text samples and perform tokenization
  def extractFeatures(samples: List[Sample]) : List[Feature] = {
    samples map (sample => new Feature(tokenize(sample.text), sample.label)) 
  }

  // this would be easier if there was a reduceByKey
  def normalize(labels : List[(String, String)]) : Polarity = {
    val length = labels.length.toFloat
    val res = labels groupBy(x => x._2) map {case(label, count) => (label, count.length / length)}
    new Polarity(res)
  }

  /**
  * @param (token, label) pairs
  * @return Mapping from token to a normalized distribution over the labels
  */
  def polarize(features: List[Feature]) : Map[String, Polarity] = {
    features flatMap (feature => {
      for(
        token <- feature.tokens ;
        label <- feature.labels
      ) yield {(token, label)}  
    }) groupBy(x => x._1) map {case(token, labels) => {
      (token, normalize(labels))
    }}
  }

  // this is ugly
  def getLabelCount(polarities: Map[String, Polarity]) : Int = 
    polarities.toArray.map(x=> x._2.polarity.size).reduce(scala.math.max)

  // so is this
  def getLabels(polarities: Map[String, Polarity]) : Array[String] = {
    val size = getLabelCount(polarities)
    polarities.toArray.filter(x=> x._2.polarity.size == size).toArray.apply(0)._2.polarity.keys.toArray
  }

  def generateDistributionFromFile(fileName: String, sep: String = "\t") : Map[String, Polarity] = {
    val samples = readLabeledFile(fileName, sep)
    val features = extractFeatures(samples)
    polarize(features)
  }
}

object PolarityDistribution extends Base {
  
  def writeListOutput(fileName: String, polarities: Map[String, Polarity]) {
    val out = new FileWriter(fileName)
    polarities foreach {case(w,p) => out.write(w + "," + p.toOrderedArray.mkString(" ") + "\n")}
    out.close()
  }

  def writeOutput(fileName: String, polarities: Map[String, Polarity]) {
    val fos = new FileOutputStream(fileName)
    val oos = new ObjectOutputStream(fos)
    oos.writeObject(polarities)
    oos.close()
  }

  def main(args : Array[String]) {
    if (args.length < 2) {
      goodbye("Usage : run-main main.scala.PolarityDistribution inputFile.csv outputFile.ser sep")
    }
    
    val pd = new PolarityDistribution()
    val sep = if (args.length == 3) args(2) else "\t"
    val polarities = pd.generateDistributionFromFile(args(0), sep)
    writeOutput(args(1), polarities)
    //polarities foreach(println)
  }
}
