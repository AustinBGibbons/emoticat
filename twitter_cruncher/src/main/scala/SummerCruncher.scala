package main.scala

import spark._
import spark.SparkContext._

import io.Source

// TODO - DataWrangler goes here
object SummerCruncher {
  def main(args: Array[String]) {
    if (args.length != 2) System.err.println("\nPlease input a file to polarize, and output file\n")
    if (args.length != 2) System.exit(1)
    val sc = new SparkContext("local[64]", "Crunchin-dem-tweets")
    val cp = new CalculatePolarities(sc)
    //CalculatePolarities(sc, args(0))
    //Sun Feb 03 21:34:53 EST 2013
    val df = new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
    // ID DATE-TIME NAME LOCATION BLANK BLANK TWEET LANGUAGE
    val tweets = sc.textFile(args(0)).map(line => line.split("\t")).persist()
    println("\nStarting with : " + tweets.count() +"\n")
    val processed = tweets.filter(line => line.length == 8)
      .filter(line => line(0) != "" && line(1) != "" && line(7) == "en")
      // contains keyword?
      .map(line => {
        //println("\nline: " + line.mkString(","))
        //println("\n\n" + line(1) + "\n\n")
        val epoch = df.parse(line(1)).getTime()
        cp.compute(line(6)).mkString(" ") +","+ epoch.toString +","+ line(6)
      })
      .persist()
  
    println("\nAnalyizing : " + processed.count() + " tweets\n")
    processed.saveAsTextFile(args(1) + "/Polarity_Time_Tweet/")
    // I don't care about this inefficiency, do I?
    processed.map(_.split(",")).map(_.head).saveAsTextFile(args(1) + "/Polarity/")
  }
}
