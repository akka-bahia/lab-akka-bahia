import play.PlayJava

name := """akka-labs"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.apache.commons" % "commons-email" % "1.3.3",
  "com.amazonaws" % "aws-java-sdk" % "1.8.4"
)
