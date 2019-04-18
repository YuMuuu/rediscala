import java.net._

import scala.io._
import java.io._
import java.util.Scanner

object Server {
  val CRLF = "\r\n"

  def main(args: Array[String]): Unit = {
    //サーバーソケットを生成しポート番号をバインドする
    val serverSocket = new ServerSocket(4545)
    val socket: Socket = serverSocket.accept()
    println("Serve on host:localhost port:4545 ...")
    while (true) {
      val input = new BufferedSource(socket.getInputStream()).getLines()
      val output = new PrintStream(socket.getOutputStream)
      if(input.hasNext) {
        val commands = getInput(input, Nil)
        println(commands)
        output.print(exec(commands)+CRLF)
        output.flush()
      }
    }
    socket.close()
  }


  def exec(commands:List[Any]): String = commands(0).asInstanceOf[String].toUpperCase match{
    case "PING" => "+PONG"
    case "EXISTS" => "0"
    case "SET" => store.set(commands(1).asInstanceOf[String], commands(2))
    case "GET" => "+" + store.get(commands(1).asInstanceOf[String]).toString
    case "COMMAND" => "+0"
    case _ => "+a"
  }


  //CRLFでsplitされたPESP配列からコマンドだけの配列を返す [*3,$5,index,$4,desc,$2,ss]
  def getInput(input: Iterator[String], commands: List[Any]): List[Any] = {
    val head = input.next()
    head.toCharArray.toList match {
      case List('+', _*) => List(head)
      case Nil => commands
      case List('*', i, _*) => getInputWithLength(input, Nil, i - '0')
      case List('$', '-', '1', _*) => getInput(input, commands :+ null)
      case List('$', _*) => getInput(input, commands :+ input.next())
      case _ => getInput(input, commands :+ head.toInt)
    }
  }

  def getInputWithLength(input: Iterator[String], commands: List[Any], length: Int): List[Any] = {
    if (commands.length == length) {
      return commands
    } else {
      val head = input.next()
      head.toCharArray.toList match {
        case Nil => commands
        case List('$', '-', '1', _*) => getInputWithLength(input, commands :+ null, length)
        case List('$', _*) => getInputWithLength(input, commands :+ input.next(), length)
        case _ => getInputWithLength(input, commands :+ head.toInt, length)
      }
    }
  }

  object store {
    var data:Map[String, Any] = Map()

    def set(key:String, value:Any):String = {
      println("SET")
      data = data + (key -> value)
      "+OK"
    }

    def get(key:String):Any = {
      data.getOrElse(key, "-Error Record Not Found")
    }
  }
}
