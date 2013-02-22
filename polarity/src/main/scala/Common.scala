package main.scala

case class PolarExample(label: Int, features: Array[Float]) extends breeze.data.Example[Int, Array[Float]] {
  val id: String = label.toString + features map (x => x.toString) reduce(_+_)
  override def toString : String = label.toString + {features map (x => " " + x.toString) reduce(_+_)}
}

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

  // read in Files of the form "Text,Label1,Label2,..."
  def readLabeledCSV(fileName: String) : List[Sample] = {
    import io.Source
    val lines = Source.fromFile(fileName).getLines()
    lines.toList map(line => {
      val fields = line.split(",").toList
      new Sample(fields(0), fields.tail)
    })
  }
}
