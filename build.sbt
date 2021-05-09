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

// Publishing -----------------------------------

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
