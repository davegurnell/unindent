# Unindent

Indent-adjusted multiline string literals for Scala.

Copyright 2015 Dave Gurnell. Licensed [Apache 2][license].

[![Scala 2.12](https://img.shields.io/maven-central/v/com.davegurnell/unindent_2.12?label=Scala%202.12)](https://search.maven.org/artifact/com.davegurnell/unindent_2.12)
[![Scala 2.13](https://img.shields.io/maven-central/v/com.davegurnell/unindent_2.13?label=Scala%202.13)](https://search.maven.org/artifact/com.davegurnell/unindent_2.13)
[![Scala 3](https://img.shields.io/maven-central/v/com.davegurnell/unindent_3?label=Scala%203.0.0)](https://search.maven.org/artifact/com.davegurnell/unindent_3)

## Getting Started

Add the following to your `build.sbt`:

```scala
libraryDependencies += "com.davegurnell" %% "unindent" % "<<VERSION>>"
```

## Synopsis

Unindent's `i"..."` string interpolator is like Scala's `s"..."` interpolator, except it removes the indent applied in the source file. The behaviour is very similar to Coffeescript's multiline string literals.

```scala
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
```

See the [tests] for more examples.

[license]: http://www.apache.org/licenses/LICENSE-2.0
[tests]: https://github.com/davegurnell/unindent/blob/master/src/test/scala/unindent/UnindentSpec.scala
