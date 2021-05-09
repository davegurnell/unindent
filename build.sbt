enablePlugins(GitVersioning)
enablePlugins(GitBranchPrompt)

// Basic settings -------------------------------

organization := "com.davegurnell"
name := "unindent"

// Versioning
git.useGitDescribe := true // Use "1.2.3-4-aabbccdde-SNAPSHOT" versioning
git.gitUncommittedChanges := git.gitCurrentTags.value.isEmpty // Put "-SNAPSHOT" on a commit if it's not a tag

ThisBuild / scalaVersion := "3.0.0-RC1"

ThisBuild / crossScalaVersions := Seq("3.0.0-RC1")

ThisBuild / scalacOptions ++= Seq(
  "-feature",
  "-unchecked",
  "-deprecation",
  "-rewrite",
  "-new-syntax"
)

ThisBuild / libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "3.2.4" % Test)

// Github Actions -------------------------------

ThisBuild / githubWorkflowJavaVersions := Seq("adopt@1.11")
ThisBuild / githubWorkflowPublishTargetBranches := Seq()

// Publishing

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

// // scalafmtOnCompile := true
