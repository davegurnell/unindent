package unindent

class UnindentSpec extends munit.FunSuite {
  test("unindent automatically") {
    val actual =
      i"""This is an indented multi-line string.
      This line ends up unindented.
        This line ends up indented by two spaces."""

    val expected =
      s"""This is an indented multi-line string.
      |This line ends up unindented.
      |  This line ends up indented by two spaces.
      """.trim.stripMargin

    assert(actual == expected)
  }

  test("strip initial and final line breaks") {
    val actual =
      i"""
      This is an indented multi-line string.
      This line ends up unindented.
        This line ends up indented by two spaces.
      """

    val expected =
      s"""This is an indented multi-line string.
      |This line ends up unindented.
      |  This line ends up indented by two spaces.
      """.trim.stripMargin

    assert(actual == expected)
  }

  test("don't strip of double initial and final line breaks") {
    val actual =
      i"""

      This is an indented multi-line string.
      This line ends up unindented.
        This line ends up indented by two spaces.

      """

    val expected =
      s"""
      |
      |This is an indented multi-line string.
      |This line ends up unindented.
      |  This line ends up indented by two spaces.
      |
      """.trim.stripMargin

    assert(actual == expected)
  }

  test("allow interpolation") {
    val actual =
      i"""
      This is an intepolated bit ${1 + 1}
        ${2 + 2} that's one too
      This one is in the ${3 + 3} middle of a line
      """

    val expected =
      s"""
      |This is an intepolated bit 2
      |  4 that's one too
      |This one is in the 6 middle of a line
      """.trim.stripMargin

    assert(actual == expected)
  }

  test("corner case - empty string") {
    assert(i"" == s"")
  }

  test("corner case - solitary interpolation") {
    assert(i"${1 + 1}" == s"2")
  }
}

class FoldedUnindentSpec extends munit.FunSuite {
  def dump(input: String): String =
    input.map(c => Integer.toHexString(c.toInt)).mkString(" ")

  test("folded unindent automatically") {
    val actual =
      i1"""
      This is a multi-line string.
      This line is appended to the previous.

      This line begins with a newline.
        This line is prefixed by three spaces."""

    val expected =
      s"""This is a multi-line string. This line is appended to the previous.
      |This line begins with a newline.   This line is prefixed by three spaces.
      """.trim.stripMargin

    println(dump(actual))
    println("")
    println(dump(expected))
    println(actual == expected)

    assert(actual == expected)
  }

  test("strip initial and final line breaks") {
    val actual =
      i1"""
      This is an indented multi-line string.

      This line ends up unindented.

        This line ends up indented by two spaces.
      """

    val expected =
      s"""This is an indented multi-line string.
      |This line ends up unindented.
      |  This line ends up indented by two spaces.
      """.trim.stripMargin

    assert(actual == expected)
  }

  test("don't strip off double initial and final line breaks") {
    val actual =
      i1"""

      This is an indented multi-line string.

      This line ends up unindented.

        This line ends up indented by two spaces.

      """

    val expected =
      s"""
      |
      |This is an indented multi-line string.
      |This line ends up unindented.
      |  This line ends up indented by two spaces.
      |
      """.trim.stripMargin

    assert(actual == expected)
  }

  test("allow interpolation") {
    val actual =
      i1"""
      This is an intepolated bit ${1 + 1}
        ${2 + 2} that's one too

      This one is in the ${3 + 3} middle of a line
      """

    val expected =
      s"""
      |This is an intepolated bit 2   4 that's one too
      |This one is in the 6 middle of a line
      """.trim.stripMargin

    assert(actual == expected)
  }

  test("corner case - empty string") {
    assert(i1"" == s"")
  }

  test("corner case - solitary interpolation") {
    assert(i1"${1 + 1}" == s"2")
  }
}
