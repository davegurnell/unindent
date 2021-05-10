import scala.language.experimental.macros

package object unindent {
  implicit class UnindentSyntax(val ctx: StringContext) extends AnyVal {
    def i(args: Any*): String = macro UnindentMacros.unindentMacro
  }
}
