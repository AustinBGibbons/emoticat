package main.scala

case class Sample(text: String, label: List[String])
case class Feature(tokens: List[String], labels: List[String])
case class Polarity(polarity: Map[String, Float], serializationId :Long = 542805478259L)

trait Base {
  def goodbye(message : String, errorCode: Int = -1) {
    System.err.println("\n\t" + message + "\n")
    System.exit(errorCode)
  } 
}
