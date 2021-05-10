package unindent

import scala.quoted.*

extension (inline ctx: StringContext)
  inline def i(inline args: Any *): String =
    ${ UnindentMacros.unindentMacro('ctx, 'args) }
