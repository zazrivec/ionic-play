name := """ionic-play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, net.litola.SassPlugin)

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  filters,
  cache,
  ws,
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23"
)

JsEngineKeys.engineType := JsEngineKeys.EngineType.Node

pipelineStages := Seq(/*rjs, */digest, gzip)
