package unindent

import scala.reflect.macros.blackbox.Context
import scala.util.matching.Regex

class UnindentMacros(val c: Context) {
  import c.universe._

  private val prefixRegex = """^\n""".r
  private val suffixRegex = """\n[ \t]*$""".r
  private val indentRegex = """\n[ \t]+""".r

  def unindentMacro(args: c.Tree*): c.Tree = {
    val indentedStrings: Seq[String] =
      c.prefix.tree match {
        case Apply(_, List(Apply(_, partTrees))) =>
          partTrees.map { case Literal(Constant(part: String)) => part }

        case _ => c.abort(c.enclosingPosition, "Unbelievable badness just happened")
      }

    val numIndentedStrings = indentedStrings.length

    val minIndent: Int =
      indentedStrings
        .flatMap(indentRegex.findAllIn(_))
        .map(_.length)
        .foldLeft(Int.MaxValue)(math.min)

    val unindentedStrings: Seq[String] =
      indentedStrings.zipWithIndex.map { case (part, index) =>
        // De-indent newlines:
        var ans = indentRegex
          .replaceAllIn(part, (m: Regex.Match) => "\n" + (" " * (m.group(0).length - minIndent)))

        // Strip any initial newline from the beginning of the string:
        if (index == 0) {
          ans = prefixRegex.replaceFirstIn(ans, "")
        }

        // Strip any final newline from the end of the string:
        if (index == numIndentedStrings - 1) {
          ans = suffixRegex.replaceFirstIn(ans, "")
        }

        ans
      }

    q"_root_.scala.StringContext(..$unindentedStrings).s(..$args)"
  }
}
