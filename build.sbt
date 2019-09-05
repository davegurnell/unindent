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

pomExtra in Global := {
  <url>https://github.com/davegurnell/unindent</url>
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
