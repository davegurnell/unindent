organization := "com.davegurnell"

name := "unindent"

scalaVersion := "3.0.0-RC1"

crossScalaVersions := Seq("2.12.12", "2.13.3")

scalacOptions ++= Seq(
  "-feature",
  "-unchecked",
  "-deprecation",
  "-rewrite",
  "-new-syntax"
)

libraryDependencies ++= Seq(
  // "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "3.2.4" % Test
)

// // Versioning

// // A lot of the versioning, publishing, and Travis-related code below is adapted from:
// //
// //   - https://alexn.org/blog/2017/08/16/automatic-releases-sbt-travis.html
// //   - http://caryrobbins.com/dev/sbt-publishing/

// enablePlugins(GitVersioning)
// enablePlugins(GitBranchPrompt)

// // Use "1.2.3-4-aabbccdde-SNAPSHOT" versnining:
// git.useGitDescribe := true

// // Put "-SNAPSHOT" on a commit if it's not a tag:
// git.gitUncommittedChanges := git.gitCurrentTags.value.isEmpty

// // Publishing

// publishMavenStyle := true

// isSnapshot := version.value.endsWith("SNAPSHOT")

// publishTo in ThisBuild := sonatypePublishTo.value

// usePgpKeyHex("7888516955DFB3F8")

// pgpPublicRing := file("./travis/local.pubring.asc")
// pgpSecretRing := file("./travis/local.secring.asc")

// licenses in ThisBuild += ("Apache-2.0", url("http://apache.org/licenses/LICENSE-2.0"))

// homepage in ThisBuild := Some(url("https://github.com/davegurnell/unindent"))

// scmInfo in ThisBuild := Some(
//   ScmInfo(
//     url("https://github.com/davegurnell/unindent.git"),
//     "scm:git@github.com:davegurnell/unindent.git"
//   )
// )

// developers in ThisBuild := List(
//   Developer(
//     id = "davegurnell",
//     name = "Dave Gurnell",
//     email = "dave@underscore.io",
//     url = url("https://twitter.com/davegurnell")
//   )
// )

// // Travis

// // Sonatype credentials are on Travis in a secret:
// credentials ++= {
//   val travisCredentials = for {
//     user <- sys.env.get("SONATYPE_USER")
//     pass <- sys.env.get("SONATYPE_PASS")
//   } yield Credentials(
//     "Sonatype Nexus Repository Manager",
//     "oss.sonatype.org",
//     user,
//     pass
//   )

//   travisCredentials.toSeq
// }

// // Password to the PGP certificate is on Travis in a secret:
// pgpPassphrase := sys.env.get("PGP_PASS").map(_.toArray)

// // Command Aliases

// addCommandAlias("ci", ";clean ;coverage ;compile ;test ;coverageReport")
// addCommandAlias("release", ";+publishSigned ;sonatypeReleaseAll")

// // Formatting

// scalafmtOnCompile := true
