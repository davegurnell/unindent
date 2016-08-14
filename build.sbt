name := "unindent"

organization := "com.davegurnell"

version := "1.0.0"

scalaVersion := "2.11.6"

initialCommands in console := """
  |import unindent._
""".trim.stripMargin

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

sonatypeProfileName := "com.davegurnell"

pomExtra in Global := {
  <url>https://github.com/davegurnell/unindent</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:github.com/davegurnell/unindent</connection>
    <developerConnection>scm:git:git@github.com:davegurnell/unindent</developerConnection>
    <url>github.com/davegurnell/unindent</url>
  </scm>
  <developers>
    <developer>
      <id>davegurnell</id>
      <name>Dave Gurnell</name>
      <url>http://davegurnell.com</url>
      <organization>Underscore</organization>
      <organizationUrl>http://underscore.io</organizationUrl>
    </developer>
  </developers>
}
