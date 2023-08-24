ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "cut",
    assembly / mainClass := Some("Main"),
    assembly / assemblyJarName := "cut.jar",
    libraryDependencies ++= Seq(Dependencies.Testing.Munit),
  )