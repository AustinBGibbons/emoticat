import sbt._
import Keys._

object Root extends Build {

  lazy val root = Project(
    id = "root",
    base = file("."),
    aggregate = Seq(polarity, twitter_cruncher)
  )

  lazy val polarity = Project(
    id = "polarity",
    base = file("polarity")
  )

  lazy val twitter_cruncher = Project(
    id = "twitter_cruncher",
    base = file("twitter_cruncher")
  ) dependsOn(polarity)
/*
  lazy val rest = play Project(
    "emoticat-rest", "1.0", Nil
  )
*/
}
