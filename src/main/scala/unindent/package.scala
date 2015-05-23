import scala.language.experimental.macros

package object unindent {
  implicit class UnindentSyntax(val ctx: StringContext) extends AnyVal {
    def i(args: Any*): String = macro UnindentMacros.unindentMacro
  }
}

package unindent {
  import scala.reflect.macros.blackbox.Context

  class UnindentMacros(val c: Context) {
    import c.universe._

    private val prefixRegex = """^\n""".r
    private val suffixRegex = """\n[ \t]*$""".r
    private val indentRegex = """\n[ \t]+""".r

    def unindentMacro(args: c.Tree *): c.Tree =
      c.prefix.tree match {
        case Apply(_, List(Apply(_, partTrees))) =>
          val parts = transform(partTrees map {
            case Literal(Constant(part: String)) => part
          })

          q"_root_.scala.StringContext(..$parts).s(..$args)"

        case _ => c.abort(c.enclosingPosition, "Unbelievable badness just happened")
      }

    private def transform(parts: List[String]) = {
      import scala.util.matching.Regex.Match

      val numParts = parts.length

      val minIndent = parts.
        flatMap(indentRegex.findAllIn(_)).
        map(_.length).
        foldLeft(Int.MaxValue)(math.min)

      parts.zipWithIndex.map {
        case (part, index) =>
          // De-indent newlines:
          var ans = indentRegex.replaceAllIn(part, (m: Match) =>
            "\n" + (" " * (m.group(0).length - minIndent)))

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
    }
  }
}