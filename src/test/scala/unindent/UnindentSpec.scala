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
