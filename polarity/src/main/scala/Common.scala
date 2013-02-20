package main.scala

case class Sample(text: String, label: List[String])
case class Feature(tokens: List[String], labels: List[String])
case class Polarity(polarity: Map[String, Float], serializationId :Long = 542805478259L) {
  def get(x: String) : Option[Float] = polarity.get(x)
  def getOrElse(x: String, y: Float) : Float = polarity.getOrElse(x, y)
  def foreach(f: ((String,Float)) => Unit) : Unit = polarity.foreach(f)
}

trait Base extends Serializable {
  def goodbye(message : String, errorCode: Int = -1) {
    System.err.println("\n\t" + message + "\n")
    System.exit(errorCode)
  } 
}
