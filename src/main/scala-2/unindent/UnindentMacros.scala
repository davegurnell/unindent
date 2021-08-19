package unindent

import scala.reflect.macros.blackbox.Context
import scala.util.matching.Regex

class UnindentMacros(val c: Context) {
  import c.universe._

  private val prefixRegex = """^\n""".r
  private val singlePrefixRegex = """^\n(?!\n)""".r
  private val suffixRegex = """\n[ \t]*$""".r
  private val singleSuffixRegex = """(?<!\n)\n(?!\n)[ \t]*$""".r
  private val indentRegex = """\n[ \t]+""".r
  private val newlinesRegex = """\n+""".r

  def unindentMacroUnfolded(args: c.Tree*): c.Tree = {
    unindentMacro(false, args: _*)
  }

  def unindentMacroFolded(args: c.Tree*): c.Tree = {
    unindentMacro(true, args: _*)
  }

  def unindentMacro(folded: Boolean, args: c.Tree*): c.Tree = {
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

    val foldedUnindentedStrings: Seq[String] =
      indentedStrings.zipWithIndex.map { case (part, index) =>
        // De-indent newlines:
        var ans = indentRegex
          .replaceAllIn(part, (m: Regex.Match) => "\n" + (" " * (m.group(0).length - minIndent)))

        // Strip any single initial newline from the beginning of the string:
        if (index == 0) {
          ans = singlePrefixRegex.replaceFirstIn(ans, "")
        }

        // Strip any single final newline from the end of the string:
        if (index == numIndentedStrings - 1) {
          ans = singleSuffixRegex.replaceFirstIn(ans, "")
        }

        // Remove non-consecutive newlines:
        ans = newlinesRegex.replaceAllIn(ans, (m: Regex.Match) => if (m.group(0).length == 1) " " else "\n" * (m.group(0).length - 1))

        ans
      }

    if (folded)
      q"_root_.scala.StringContext(..$foldedUnindentedStrings).s(..$args)"
    else
      q"_root_.scala.StringContext(..$unindentedStrings).s(..$args)"
  }
}
