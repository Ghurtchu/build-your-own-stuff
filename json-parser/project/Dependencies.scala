import sbt._

object Dependencies {
  object Parsing {
    private object VersionOf {
      val Parsing = "2.2.0"
    }

    val ScalaParserCombinator = "org.scala-lang.modules" %% "scala-parser-combinators" % VersionOf.Parsing
  }
}
