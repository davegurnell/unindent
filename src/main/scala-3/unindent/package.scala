package unindent

import scala.quoted.*

extension (inline ctx: StringContext)
  /**
   * Alternative to `s"..."` that removes the indent applied in the source file.
   * Example follows:
   *
   * {{{
   * import unindent._
   *
   * val example =
   *   i"""
   *   This is an indented multi-line string.
   *   This line ends up unindented.
   *     This line ends up indented by two spaces.
   *   It supports interpolation too: ${1 + 1}.
   *   """
   *
   * println("[" + example + "]")
   * // [This is an indented multi-line string.
   * // This line ends up unindented.
   * //   This line ends up indented by two spaces.
   * // It supports interpolation too: 2.]
   * }}}
   */
  inline def i(inline args: Any *): String =
    ${ UnindentMacros.unindentMacro('ctx, 'args, false) }

  /**
   * Alternative to `s"..."` that removes the indent applied in the source file
   * and folds "paragraphs" of text into single lines
   * (similar to YAML folded multiline strings).
   * Example follows:
   *
   * {{{
   * val example =
   *   i1"""
   *   This is the first line.
   *   This line is appended to the first.
   *
   *   This line follows a line break.
   *     This line ends up indented by two spaces.
   *   """
   *
   * println("[" + example + "]")
   * // [This is the first line. This line is appended to the first.
   * // This line follows a line break.   This line ends up indented by two spaces.]
   * }}}
   */
  inline def i1(inline args: Any *): String =
    ${ UnindentMacros.unindentMacro('ctx, 'args, true) }
