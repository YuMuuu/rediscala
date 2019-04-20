import java.net.{ServerSocket, Socket}

import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}
import utils.IOUtil.using


object Server {

  def main(args: Array[String]): Unit = {

    implicit val ec:ExecutionContext = ExecutionContext.Implicits.global

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
            * パーサーでパースする
            */
          //val request = ???

          /**
            * パース結果をRequestHandler型にmapする
            *
            */
          //val response = ???

          /**
            * 評価結果をoutputStreamに出力する
            */
           //response.foreach(_.writeTo(out))
        }
      }
    }
  }
}
