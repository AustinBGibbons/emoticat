
import sbt._
import Keys._

object Root extends Build {

  lazy val root = Project(
    id = "root",
    base = file("."),
    aggregate = Seq(polarity)
  )

  lazy val polarity = Project(
    id = "polarity",
    base = file("polarity")
  )
}