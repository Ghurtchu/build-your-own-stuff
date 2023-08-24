import sbt._

object Dependencies {
  object Testing {
    private object VersionOf {
      val Munit = "0.7.29"
    }

    val Munit = "org.scalameta" %% "munit" % VersionOf.Munit % Test
  }
}