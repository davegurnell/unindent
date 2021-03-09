package unindent

import org.scalatest.wordspec.*
import org.scalatest.matchers.should.*

class UnindentSpec extends AnyWordSpec, Matchers:
  "i-strings" should {
    "be unindented automatically" in {
      (
        i"""This is an indented multi-line string.
        This line ends up unindented.
          This line ends up indented by two spaces."""
      ) shouldBe (
        s"""This is an indented multi-line string.
        |This line ends up unindented.
        |  This line ends up indented by two spaces.
        """.trim.stripMargin
      )
    }

    "be stripped of initial and final line breaks" in {
      (
        i"""
        This is an indented multi-line string.
        This line ends up unindented.
          This line ends up indented by two spaces.
        """
      ) shouldBe (
        s"""This is an indented multi-line string.
        |This line ends up unindented.
        |  This line ends up indented by two spaces.
        """.trim.stripMargin
      )
    }

    "not be stripped of double initial and final line breaks" in {
      (
        i"""

        This is an indented multi-line string.
        This line ends up unindented.
          This line ends up indented by two spaces.

        """
      ) shouldBe (
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
      ) shouldBe (
        s"""
        |This is an intepolated bit 2
        |  4 that's one too
        |This one is in the 6 middle of a line
        """.trim.stripMargin
      )
    }

    "handle corner cases" should {
      "empty string" in {
        i"" shouldBe (s"")
      }

      "solitary interpolation" in {
        i"${1 + 1}" shouldBe (s"2")
      }
    }
  }
