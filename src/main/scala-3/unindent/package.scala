package unindent

import scala.quoted.*

extension (inline ctx: StringContext)
  inline def i(inline args: Any *): String =
    ${ UnindentMacros.unindentMacro('ctx, 'args, false) }
  inline def y(inline args: Any *): String =
    ${ UnindentMacros.unindentMacro('ctx, 'args, true) }
