import java.io.PrintStream
import java.net.{ServerSocket, Socket}

import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}
import scala.io.BufferedSource
import utils.IOUtil.using

object Server {
  implicit val ec = ExecutionContext

  val config = ConfigFactory.load()

  def main(args: Array[String])(implicit ec: ExecutionContext): Unit = {
    val config = ConfigFactory.load()
    val port = config.getInt("port")

    val parser = Parser

    //サーバーソケットを生成しポート番号をバインドする
    val serverSocket = new ServerSocket(port)

    while (true) {
      val socket: Socket = serverSocket.accept()
      println(s"Serve on host:localhost port:${port} ...")

      Future {
        using(socket) { s =>
          val input: Iterator[String] = new BufferedSource(s.getInputStream).getLines
          val output: PrintStream = new PrintStream(s.getOutputStream)

          val request = parser(input)
          request.foreach(output.print(_))
          output.flush
        }
      }
    }
  }
}
