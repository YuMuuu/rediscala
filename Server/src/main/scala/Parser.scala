import java.io.{BufferedReader, InputStream, InputStreamReader}

import model.Token


/**
  * input:  InputStream
  * output:
  */

class Parser {
  def fromInputStream(in: InputStream): List[Either[Exception, Token]] = {
    val br = new BufferedReader(new InputStreamReader(in))

    //これ実装間違えていて動かない
    Iterator.continually(br.readLine()).takeWhile(it => it != null && !it.isEmpty).map {
      it =>
        val key = it.substring(0, 1)
        val value = it.substring(2)
        RequestValidate.validator(key, value)
    }.toList
  }
}

object Parser {
  def apply: Parser = new Parser()


}