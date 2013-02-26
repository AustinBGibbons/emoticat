import sbt._
import Keys._

object Root extends Build {

  lazy val root = Project(
    id = "root",
    base = file("."),
    aggregate = Seq(polarity, testadventures)
  )

  lazy val polarity = Project(
    id = "polarity",
    base = file("polarity")
  )

  lazy val testadventures = Project(
    id = "testadventures",
    base = file("testadventures")
  )
}
