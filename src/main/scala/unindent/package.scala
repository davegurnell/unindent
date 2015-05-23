import scala.util.matching.Regex
import scala.util.matching.Regex.Match

package object unindent {
  private val prefixRegex = """^\n""".r
  private val suffixRegex = """\n[ \t]*$""".r
  private val indentRegex = """\n[ \t]+""".r

  implicit class UnindentHelper(val ctx: StringContext) extends AnyVal {
    def i(args: Any*): String = {
      val parts = ctx.parts
      val numParts = parts.length

      val minIndent = parts.
        flatMap(indentRegex.findAllIn(_)).
        map(_.length).
        foldLeft(Int.MaxValue)(math.min)

      val updatedParts = parts.zipWithIndex.map {
        case (part, index) =>

          // De-indent newlines:
          var ans = indentRegex.replaceAllIn(part, (m: Match) => "\n" + " " * (m.group(0).length - minIndent))

          // Strip any initial newline from the beginning of the string:
          if(index == 0) {
            ans = prefixRegex.replaceFirstIn(ans, "")
          }

          // Strip any final newline from the end of the string:
          if(index == numParts - 1) {
            ans = suffixRegex.replaceFirstIn(ans, "")
          }

          ans
      }

      val updatedCtx = StringContext(updatedParts : _*)

      updatedCtx.s(args : _*)
    }
  }
}