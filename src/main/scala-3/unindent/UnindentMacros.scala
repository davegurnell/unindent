package unindent

import scala.quoted.*
import scala.util.matching.Regex

object UnindentMacros:
  private val prefixRegex = """^\n""".r
  private val singlePrefixRegex = """^\n(?!\n)""".r
  private val suffixRegex = """\n[ \t]*$""".r
  private val singleSuffixRegex = """(?<!\n)\n(?!\n)[ \t]*$""".r
  private val indentRegex = """\n[ \t]+""".r
  private val newlinesRegex = """\n+""".r

  def unindentMacro(ctx: Expr[StringContext],
                    args: Expr[Seq[Any]],
                    folded: Boolean)
                    (using Quotes): Expr[String] =
    val indentedStrings: Seq[String] =
      ctx match
        case '{ StringContext(${Varargs(indentedStrings)} *) } =>
          indentedStrings.map { case Expr(part) => part }
        case other =>
          sys.error("""Error matching on StringContext""")

    val numIndentedStrings = indentedStrings.length

    val minIndent: Int =
      indentedStrings
        .flatMap(indentRegex.findAllIn)
        .map(_.length)
        .foldLeft(Int.MaxValue)(math.min)

    val unindentedStrings: Seq[String] =
      indentedStrings.zipWithIndex.map { case (part, index) =>
        // De-indent newlines:
        var ans = indentRegex
          .replaceAllIn(part, (m: Regex.Match) => "\n" + (" " * (m.group(0).length - minIndent)))

        // Strip any initial newline from the beginning of the string:
        if index == 0 then
          ans = prefixRegex.replaceFirstIn(ans, "")

        // Strip any final newline from the end of the string:
        if index == numIndentedStrings - 1 then
          ans = suffixRegex.replaceFirstIn(ans, "")

        ans
      }

    val foldedUnindentedStrings: Seq[String] =
      indentedStrings.zipWithIndex.map { case (part, index) =>
        // De-indent newlines:
        var ans = indentRegex
          .replaceAllIn(part, (m: Regex.Match) => "\n" + (" " * (m.group(0).length - minIndent)))

        // Strip any single initial newline from the beginning of the string:
        if index == 0 then
          ans = singlePrefixRegex.replaceFirstIn(ans, "")

        // Strip any single final newline from the end of the string:
        if index == numIndentedStrings - 1 then
          ans = singleSuffixRegex.replaceFirstIn(ans, "")

        // Remove non-consecutive newlines:
        ans = newlinesRegex.replaceAllIn(ans, (m: Regex.Match) =>
          if m.group(0).length == 1 then " " else "\n" * (m.group(0).length - 1))

        ans
      }

    val unindentedExprs: Seq[Expr[String]] =
      if folded then
        foldedUnindentedStrings.map(Expr(_))
      else
        unindentedStrings.map(Expr(_))

    '{ StringContext(${Varargs(unindentedExprs)} *).s($args *) }
