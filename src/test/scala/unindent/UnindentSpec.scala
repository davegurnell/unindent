package unindent

import org.scalatest._

class UnindentSpec extends FreeSpec with MustMatchers {
  "i-strings" - {
    "are unindented automatically" in {
      (
        i"""This is an indented multi-line string.
        This line ends up unindented.
          This line ends up indented by two spaces."""
      ) must equal(
        s"""This is an indented multi-line string.
        |This line ends up unindented.
        |  This line ends up indented by two spaces.
        """.trim.stripMargin
      )
    }

    "are stripped of initial and final line breaks" in {
      (
        i"""
        This is an indented multi-line string.
        This line ends up unindented.
          This line ends up indented by two spaces.
        """
      ) must equal(
        s"""This is an indented multi-line string.
        |This line ends up unindented.
        |  This line ends up indented by two spaces.
        """.trim.stripMargin
      )
    }

    "are not stripped of double initial and final line breaks" in {
      (
        i"""

        This is an indented multi-line string.
        This line ends up unindented.
          This line ends up indented by two spaces.

        """
      ) must equal(
        s"""
        |
        |This is an indented multi-line string.
        |This line ends up unindented.
        |  This line ends up indented by two spaces.
        |
        """.trim.stripMargin
      )
    }

    "allow interpolation" in {
      (
        i"""
        This is an intepolated bit ${1 + 1}
          ${2 + 2} that's one too
        This one is in the ${3 + 3} middle of a line
        """
      ) must equal(
        s"""
        |This is an intepolated bit 2
        |  4 that's one too
        |This one is in the 6 middle of a line
        """.trim.stripMargin
      )
    }

    "handle corner cases" - {
      "empty string" in {
        i"" must equal(s"")
      }

      "solitary interpolation" in {
        i"${1 + 1}" must equal(s"2")
      }
    }
  }
}
