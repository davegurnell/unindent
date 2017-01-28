name         := "unindent"
organization := "com.davegurnell"
version      := "1.1.0-SNAPSHOT"
scalaVersion := "2.12.0"

crossScalaVersions := Seq("2.11.8", "2.12.0")

licenses += ("Apache-2.0", url("http://apache.org/licenses/LICENSE-2.0"))

libraryDependencies ++= Seq(
  "org.scala-lang"  % "scala-reflect" % scalaVersion.value,
  "org.scalatest"  %% "scalatest"     % "3.0.1" % "test"
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
