package unindent

import scala.quoted.*

extension (inline ctx: StringContext)
  inline def i(inline args: Any *): String =
    ${ unindentImpl('ctx, 'args) }

private val prefixRegex = """^\n""".r
private val suffixRegex = """\n[ \t]*$""".r
private val indentRegex = """\n[ \t]+""".r

def unindentImpl(ctx: Expr[StringContext], args: Expr[Seq[Any]])(using Quotes): Expr[String] =
  println("starting macro expansion")
  println("ctx " + ctx.show)

  // TODO: Get the string literals passed to `new StringContext(...)`:
  val parts: Seq[String] =
    ctx match
      case '{ new StringContext(${Varargs(parts)} *) } =>
        println("parts " + parts)
        parts.map(_.show)
      case nope =>
        println("match failed " + nope)
        ???

  val numParts = parts.length

  val minIndent: Int =
    parts
      .flatMap(indentRegex.findAllIn)
      .map(_.length)
      .foldLeft(Int.MaxValue)(math.min)

  val unindented: Seq[String] =
    parts.zipWithIndex.map {
      case (part, index) =>
        import scala.util.matching.Regex.Match

        // De-indent newlines:
        var ans = indentRegex.replaceAllIn(part, (m: Match) => "\n" + (" " * (m.group(0).length - minIndent)))

        // Strip any initial newline from the beginning of the string:
        if index == 0 then
          ans = prefixRegex.replaceFirstIn(ans, "")

        // Strip any final newline from the end of the string:
        if index == numParts - 1 then
          ans = suffixRegex.replaceFirstIn(ans, "")

        ans
    }

  '{ new StringContext(${Varargs(unindented)} *).s($args *) }
