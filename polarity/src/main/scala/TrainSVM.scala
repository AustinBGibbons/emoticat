package main.scala

import java.io.FileInputStream
import java.io.ObjectInputStream

object TrainSVM extends Base {

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

  def main(args: Array[String]) {
    if(args.length != 2) {
      goodbye("Usage: run-main main.scala.TrainSVM polarities.ser support-vectors.sv")
    }

    val polarities = readSerializedPolarities(args(0))
  }
}
