import scala.language.experimental.macros

package object unindent {
  implicit class UnindentSyntax(val ctx: StringContext) extends AnyVal {

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
    def i(args: Any*): String = macro UnindentMacros.unindentMacroUnfolded

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
    def i1(args: Any*): String = macro UnindentMacros.unindentMacroFolded
  }
}
