import java.net.{ServerSocket, Socket}

import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}
import utils.IOUtil.using


object Server {

  def main(args: Array[String]): Unit = {

    implicit val ec: ExecutionContext = ExecutionContext.Implicits.global

    val config = ConfigFactory.load()

    //    val port = config.getInt("port")
    val port = 4545

    //    val parser = Parser

    //サーバーソケットを生成しポート番号をバインドする
    val serverSocket = new ServerSocket(port)

    while (true) {
      val socket: Socket = serverSocket.accept
      println(s"Serve on host:localhost port:${port} ...")

      Future {
        using(socket) { s =>
          val in = s.getInputStream
          val out = s.getOutputStream


          /**
            * 構文解析
            */
          val parser = ???

          /**
            * 字句解析
            */
          val lexer = ???

          /**
            * 評価
            */
          val eval: String = ???

          /**
            * 表示
            */
          out.write(eval.getBytes())
        }
      }
    }
  }
}
