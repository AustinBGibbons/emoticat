package main.scala

import spark._
import spark.SparkContext._

import io.Source

// TODO - DataWrangler goes here
object SummerCruncher {
  def main(args: Array[String]) {
    if (args.length < 2) System.err.println("\nPlease input a file to polarize, and output file [--format] [sparkcontext]\n")
    if (args.length < 2) System.exit(1)
    val spark_string = if (args.length > 3) args(3) else "local[16]"
    val sc = new SparkContext(spark_string, "Crunchin-dem-tweets")
    val cp = new CalculatePolarities(sc)
    //Sun Feb 03 21:34:53 EST 2013
    val df = new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
    // ID DATE-TIME NAME LOCATION BLANK BLANK TWEET LANGUAGE
    val tweets = sc.textFile(args(0)).map(line => line.split("\t")).persist()
    println("\nStarting with : " + tweets.count() +"\n")
    val cleaned = tweets.filter(line => line.length == 8)
      .filter(line => line(0) != "" && line(1) != "" && line(7) == "en")
      //.map() datestring => epoch
    val processed = 
        if (args(2) == "--libsvm")
          cleaned.map(line => {
            val epoch = df.parse(line(1)).getTime()
            "0 " + cp.compute(line(6)).zip(1 to line(6).length).map{case(e,i) => i+":"+e}.mkString(" ") +","+ epoch.toString +","+ line(6)
          })
        else
          cleaned.map(line => {
            val epoch = df.parse(line(1)).getTime()
            cp.compute(line(6)).mkString(" ") +","+ epoch.toString +","+ line(6)
          })
      .persist()
  
    println("\nAnalyizing : " + processed.count() + " tweets\n")
    processed.saveAsTextFile(args(1) + "/Polarity_Time_Tweet/")
    processed.map(_.split(",")).map(_.head).saveAsTextFile(args(1) + "/Polarity/")
  }
}
