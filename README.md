# Unindent

Indent-adjusted multiline string literals for Scala.

Copyright 2015 Dave Gurnell. Licensed [Apache 2][license].

[![Build Status](https://travis-ci.org/davegurnell/unindent.svg?branch=develop)](https://travis-ci.org/davegurnell/unindent)
[![Coverage status](https://img.shields.io/codecov/c/github/davegurnell/unindent/develop.svg)](https://codecov.io/github/davegurnell/unindent)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.davegurnell/unindent_2.11/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.davegurnell/unindent_2.11)

## Getting Started

Grab the code from Bintray by adding the following to your `build.sbt`:

~~~ scala
libraryDependencies += "com.davegurnell" %% "unindent" % "<<VERSION>>"
~~~

## Synopsis

Unindent's `i"..."` string interpolator is like Scala's `s"..."` interpolator, except it removes the indent applied in the source file. The behaviour is very similar to Coffeescript's multiline string literals.

~~~ scala
import unindent._

val example =
  i"""
  This is an indented multi-line string.
  This line ends up unindented.
    This line ends up indented by two spaces.
  It supports interpolation too: ${1 + 1}.
  """

println("[" + example + "]")
// [This is an indented multi-line string.
// This line ends up unindented.
//   This line ends up indented by two spaces.
// It supports interpolation too: 2.]
~~~

See the [tests] for more examples.

[license]: http://www.apache.org/licenses/LICENSE-2.0
[tests]: https://github.com/davegurnell/unindent/blob/master/src/test/scala/unindent/UnindentSpec.scala
