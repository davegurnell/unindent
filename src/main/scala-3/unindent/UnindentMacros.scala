package unindent

import scala.quoted.*

object UnindentMacros:
  val prefixRegex = """^\n""".r
  val suffixRegex = """\n[ \t]*$""".r
  val indentRegex = """\n[ \t]+""".r

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
      indentedStrings.zipWithIndex.map {
        case (part, index) =>
          import scala.util.matching.Regex.Match

          // De-indent newlines:
          var ans = indentRegex.replaceAllIn(part, (m: Match) => "\n" + (" " * (m.group(0).length - minIndent)))

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
