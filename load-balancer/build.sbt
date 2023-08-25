name := "load-balancer"
organization := "com.ghurtchu"

version := "1.0-SNAPSHOT"

val dependencies = Seq(guice, ws, "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test)

lazy val root = (project in file("."))
  .settings(
    name := "lb",
    assembly / mainClass := Some("Main"),
    assembly / assemblyJarName := "lb.jar",
    libraryDependencies ++= dependencies,
  ).enablePlugins(PlayScala)

scalaVersion := "2.13.11"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.ghurtchu.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.ghurtchu.binders._"
