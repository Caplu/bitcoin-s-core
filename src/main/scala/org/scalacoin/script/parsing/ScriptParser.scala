package org.scalacoin.script.parsing

import org.scalacoin.script._

import scala.annotation.tailrec

/**
 * Created by chris on 1/7/16.
 */
trait ScriptParser extends  {


  /**
   * Parses a script inside of a transaction
   * @param str
   * @tparam T
   * @return
   */
  def parseInputScript(str : String) : List[ScriptToken] = ???

  /**
   * Parses an output script of a transaction
   * @param str
   * @return
   */
  def parseOutputScript(str : String) : List[ScriptToken] = {
    @tailrec
    def loop(operations : List[String], accum : List[ScriptToken]) : List[ScriptToken] = {
      operations match {
        case h :: t if (ScriptOperationFactory.fromString(h).isDefined) =>
          loop(t,ScriptOperationFactory.fromString(h).get :: accum)
        case h :: t => loop(t, ScriptConstantImpl(h) :: accum)
        case h :: t if (h == "0") => loop(t, OP_0 :: accum)
        case Nil => accum
      }
    }
    loop(str.split(" ").toList.reverse, List())
  }
}
