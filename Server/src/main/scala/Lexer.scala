import model.Token


implicit var data: Map[Key, Value] = Map()

trait Reply

trait SimpleStrings extends Reply //+

trait PING extends SimpleStrings {
  def apply = "PONG"
}

object PING {
  def apply()(implicit ping: PING) = ping
}


trait SET extends SimpleStrings {
  def apply: String = "OK"
}

object SET {
  def apply(key: Key, value: Value)(implicit set: SET) = {
    data + (key -> value)
    set
  }
}

trait SELECT extends SimpleStrings

trait SAVE extends SimpleStrings

trait BGSAVE extends SimpleStrings

trait SHUTDOWN extends SimpleStrings

trait RENAME extends SimpleStrings

trait LPUSH extends SimpleStrings

trait RPUSH extends SimpleStrings

trait LSET extends SimpleStrings

trait LTRIM extends SimpleStrings


trait ErrorReply extends Reply //-


trait Integers extends Reply //:

trait SETNX extends Integers

trait DEL extends Integers

trait EXISTS extends Integers

trait INCR extends Integers

trait INCRBY extends Integers

trait DECR extends Integers

trait DECRBY extends Integers

trait DBSIZE extends Integers

trait LASTSAVE extends Integers

trait RENAMENX extends Integers

trait MOVE extends Integers

trait LLEN extends Integers

trait SADD extends Integers

trait SREM extends Integers

trait SISMEMBER extends Integers

trait SCARD extends Integers


trait BlukStrings extends Reply //$


trait Arrays extends Reply //*

class Lexer {

  //  private var data: Map[String, Long] = Map()

  def apply(tokens: List[Token]): List[Reply] = {
    tokens.map {
      it =>
        it.data match {
          case i if i == "SET" =>
        }
    }
  }

}

object Lexer {

  def apply(tokens: List[Token]): List[Reply] = new Lexer().apply(tokens)

}

