organization := "com.davegurnell"
name := "unindent"
scalaVersion := "2.13.3"
crossScalaVersions := Seq("2.11.12", "2.12.12", "2.13.3")

licenses += ("Apache-2.0", url("http://apache.org/licenses/LICENSE-2.0"))

scalacOptions ++= Seq(
  "-feature",
  "-unchecked",
  "-deprecation"
)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scalatest"  %% "scalatest"    % "3.0.8" % Test
)

homepage := Some(url("https://github.com/davegurnell/unindent"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/davegurnell/unindent.git"),
    "scm:git@github.com:davegurnell/unindent.git"
  )
)

developers := List(
  Developer(
    id = "davegurnell",
    name = "Dave Gurnell",
    email = "dave@underscore.io",
    url = url("https://twitter.com/davegurnell")
  )
)

pgpPublicRing := file("./travis/local.pubring.asc")
pgpSecretRing := file("./travis/local.secring.asc")

// Command Aliases

addCommandAlias("ci", ";clean ;coverage ;compile ;+test ;coverageReport")

addCommandAlias("release", ";releaseEarly")
