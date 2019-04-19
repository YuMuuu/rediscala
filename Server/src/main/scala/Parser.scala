class Parser(){
  val store = Store
  val CRLF = "\r\n"

  def apply(input: Iterator[String]): Iterator[String] = {
    val hoge: Iterator[String] = Iterator.continually(input).takeWhile(_.hasNext).map {
      it =>
        val commands = getInput(it, Nil) match {
          case Nil => List("?")
          case i => i
        }
        println(commands)
        (encode(exec(commands)) + CRLF)
    }

    hoge
  }

  private def exec(commands: List[Any]): Any = {
    case "PING" => if (commands.length == 1) "PONG" else commands(1)
    case "SET" => onSet(commands.drop(1))
    case "INCRBY" => store.incrBy(commands(1), commands(2).toString.toLong)
    case "DECRBY" => store.decrBy(commands(1), commands(2).toString.toLong)
    case "GET" => store.get(commands(1))
    case "DEL" => store.delete(commands.drop(1))
    case "EXISTS" => store.exists(commands.drop(1))
    case "COMMAND" => "OK"
    case _ => "?"
  }

  private def getInput(input: Iterator[String], commands: List[Any]): List[Any] = {
    val head = input.next()
    println(head)
    head.toCharArray.toList match {
      case List('+', _*) => List(head)
      case List(':', _*) => getInput(input, commands :+ head.diff(":").toInt)
      case Nil => commands
      case List('*', i, _*) => getInputWithLength(input, Nil, i - '0')
      case List('$', '-', '1', _*) => getInput(input, commands :+ null)
      case List('$', _*) => getInput(input, commands :+ input.next())
      case _ => getInput(input, commands :+ head.toInt)
    }
  }

  private def getInputWithLength(input: Iterator[String], commands: List[Any], length: Int): List[Any] = {
    if (commands.length == length) {
      commands
    } else {
      val head = input.next()
      println(head)
      head.toCharArray.toList match {
        case Nil => commands
        case List('$', '-', '1', _*) => getInputWithLength(input, commands :+ null, length)
        case List('$', _*) => getInputWithLength(input, commands :+ input.next(), length)
        case List(':', _*) => getInputWithLength(input, commands :+ head.diff(":").toInt, length)
        case _ => getInputWithLength(input, commands :+ head.toInt, length)
      }
    }
  }

  private def encode(res: Any): String = res match {
    case List("-", i: String) => "-" + i
    case i: String => "+" + i
    case i: Long => ":" + i.toString
    case i: Any => "+" + i.toString
  }

  private def onSet(commands: List[Any]): Any = commands match {
    case List(key, value) => store.set(key, value)
    case List(key, value, option: String) => setWithOption(key, value, option)
    case _ => List("-", "invalid argument" + commands)
  }

  private def setWithOption(key: Any, value: Any, option: String): Any = option.toUpperCase match {
    case "NX" => store.setNX(key, value)
    case "XX" => store.setXX(key, value)
    case _ => List("-", "option not Supported" + option)
  }
}

object Parser {
  def apply(input: Iterator[String]): Iterator[String] = new Parser().apply(input)
}