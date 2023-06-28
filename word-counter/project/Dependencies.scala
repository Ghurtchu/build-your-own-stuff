import sbt._

object Dependencies {
  object Testing {
    private object VersionOf {
      val Munit = "0.7.29"
      val ScalaTestPlus = "3.2.16.0"
    }

    val Munit = "org.scalameta" %% "munit" % VersionOf.Munit % Test
    val ScalaTestPlus = "org.scalatestplus" %% "scalacheck-1-17" % VersionOf.ScalaTestPlus % Test
  }
}
