name := "unindent"

organization := "com.davegurnell"

version := "1.0.0"

licenses += ("Apache-2.0", url("http://apache.org/licenses/LICENSE-2.0"))

scalaVersion := "2.11.6"

initialCommands in console := """
  |import unindent._
""".trim.stripMargin

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

bintrayPackageLabels in bintray := Seq("scala", "string", "utility")

bintrayRepository in bintray := "maven"
