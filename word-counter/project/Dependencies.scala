import sbt._

object Dependencies {

  object Testing {
    private object Version {
      val Munit = "0.7.29"
    }

    val Munit = "org.scalameta" %% "munit" % Version.Munit % Test

  }

}
