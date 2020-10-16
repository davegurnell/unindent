organization in ThisBuild := "com.davegurnell"
name in ThisBuild := "unindent"
scalaVersion in ThisBuild := "2.13.3"
crossScalaVersions in ThisBuild := Seq("2.12.12", "2.13.3")

// Versioning

// A lot of the versioning, publishing, and Travis-related code below is adapted from:
//
//   - https://alexn.org/blog/2017/08/16/automatic-releases-sbt-travis.html
//   - http://caryrobbins.com/dev/sbt-publishing/

enablePlugins(GitVersioning)
enablePlugins(GitBranchPrompt)

// Use "1.2.3-4-aabbccdde-SNAPSHOT" versnining:
git.useGitDescribe := true

// Put "-SNAPSHOT" on a commit if it's not a tag:
git.gitUncommittedChanges := git.gitCurrentTags.value.isEmpty

// This is what release tags look like:
val ReleaseTag = """^([\d\.]+)$""".r

git.gitTagToVersionNumber := {
  case ReleaseTag(v) => Some(v)
  case _             => None
}

// Publishing

publishMavenStyle := true

isSnapshot := version.value.endsWith("SNAPSHOT")

publishTo in ThisBuild := sonatypePublishTo.value

usePgpKeyHex("7888516955DFB3F8")

pgpPublicRing := file("./travis/local.pubring.asc")
pgpSecretRing := file("./travis/local.secring.asc")

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

// Travis

// Sonatype credentials are on Travis in a secret:
credentials ++= {
  val travisCredentials = for {
    user <- sys.env.get("SONATYPE_USER")
    pass <- sys.env.get("SONATYPE_PASS")
  } yield Credentials(
    "Sonatype Nexus Repository Manager",
    "oss.sonatype.org",
    user,
    pass
  )

  travisCredentials.toSeq
}

// Password to the PGP certificate is on Travis in a secret:
pgpPassphrase := sys.env.get("PGP_PASS").map(_.toArray)

// Command Aliases

addCommandAlias("ci", ";clean ;coverage ;compile ;test ;coverageReport")
addCommandAlias("release", ";+publishSigned ;sonatypeReleaseAll")

// Formatting

scalafmtOnCompile := true
