package unindent

import scala.quoted.*
import scala.util.matching.Regex

object UnindentMacros:
  private val prefixRegex = """^\n""".r
  private val suffixRegex = """\n[ \t]*$""".r
  private val indentRegex = """\n[ \t]+""".r

  def unindentMacro(ctx: Expr[StringContext], args: Expr[Seq[Any]])(using Quotes): Expr[String] =
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

    val unindentedExprs: Seq[Expr[String]] =
      unindentedStrings.map(Expr(_))

    '{ StringContext(${Varargs(unindentedExprs)} *).s($args *) }
