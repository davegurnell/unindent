# Unindent

Indent-adjusted multiline string literals for Scala.
No more `stripMargin`!

~~~ scala
import coffeestring._

val example =
  i"""
  This is an indented multi-line string.
  This line ends up unindented.
    This line ends up indented by two spaces.
  It supports interpolation, too: ${1 + 1}.
  """

println("[" + example + "]")
// [This is an indented multi-line string.
// This line ends up unindented.
//   This line ends up indented by two spaces.
// It supports interpolation, too: 2.]
~~~

Inspired by Coffeescript multiline string literals.