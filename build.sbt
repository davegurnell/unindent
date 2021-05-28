enablePlugins(GitVersioning)
enablePlugins(GitBranchPrompt)

// Basic settings -------------------------------

organization := "com.davegurnell"
name := "unindent"

ThisBuild / scalaVersion := "3.0.0"

ThisBuild / crossScalaVersions := Seq("3.0.0", "2.13.5", "2.12.14")

ThisBuild / scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, _)) =>
      Seq(
        "-feature",
        "-unchecked",
        "-deprecation",
      )

    case _ =>
      Seq(
        "-feature",
        "-unchecked",
        "-deprecation",
        "-rewrite",
        "-new-syntax",
      )
  }
}

ThisBuild / libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, _)) =>
      Seq(
        "org.scalameta" %% "munit"         % "0.7.26" % Test,
        "org.scala-lang" % "scala-reflect" % scalaVersion.value
      )
    case _ =>
      Seq("org.scalameta" %% "munit" % "0.7.26" % Test)
  }
}

// Versioning -----------------------------------

ThisBuild / versionScheme := Some("early-semver")

git.gitUncommittedChanges := git.gitCurrentTags.value.isEmpty // Put "-SNAPSHOT" on a commit if it's not a tag

// Github Actions -------------------------------

ThisBuild / githubWorkflowJavaVersions := Seq("adopt@1.11")

// Publishing -----------------------------------

usePgpKeyHex("7888516955DFB3F8")

ThisBuild / versionScheme := Some("early-semver")

ThisBuild / githubWorkflowTargetTags ++= Seq("v*")

ThisBuild / githubWorkflowPublishTargetBranches := Seq(RefPredicate.StartsWith(Ref.Tag("v")))

ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    List("ci-release"),
    env = Map(
      "PGP_PASSPHRASE"    -> "${{ secrets.PGP_PASSPHRASE }}",
      "PGP_SECRET"        -> "${{ secrets.PGP_SECRET }}",
      "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
      "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
    )
  )
)

ThisBuild / licenses += ("Apache-2.0", url("http://apache.org/licenses/LICENSE-2.0"))

ThisBuild / homepage := Some(url("https://github.com/davegurnell/unindent"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/davegurnell/unindent.git"),
    "scm:git@github.com:davegurnell/unindent.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id = "davegurnell",
    name = "Dave Gurnell",
    email = "dave@underscore.io",
    url = url("https://twitter.com/davegurnell")
  )
)
