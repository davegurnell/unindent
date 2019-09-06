name := "unindent"
organization := "com.davegurnell"
version := "1.1.1"

scalaVersion in ThisBuild := "2.13.0"
crossScalaVersions in ThisBuild := Seq("2.11.12", "2.12.9", "2.13.0")

licenses += ("Apache-2.0", url("http://apache.org/licenses/LICENSE-2.0"))

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)

// Publishing

publishMavenStyle := true

isSnapshot := version.value endsWith "SNAPSHOT"

publishTo := sonatypePublishTo.value

licenses += ("Apache-2.0", url("http://apache.org/licenses/LICENSE-2.0"))

homepage := Some(url("https://github.com/davegurnell/unindent"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/davegurnell/unindent.git"),
    "scm:git@github.com:davegurnell/unindent.git"))

developers := List(
  Developer(
    id    = "davegurnell",
    name  = "Dave Gurnell",
    email = "dave@underscore.io",
    url   = url("https://twitter.com/davegurnell")))
