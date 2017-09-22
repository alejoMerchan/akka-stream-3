package example2

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Ejemplo basico de un Flow con un source apartir de un archivo de texto.
  */
object ReadFile2 extends App {

  implicit val system = ActorSystem("read-file")
  implicit val materializer = ActorMaterializer()

  val file = Paths.get("D:/2017/streams/prueba3.txt")

  val result = FileIO.fromPath(file) // se crea Stream Source en base al archivo de lectura.
    .to(Sink.foreach(println(_))). // proceso de los datos del stream con Sink
    run()

  Await.ready(result,Duration.Inf)
  system.terminate()

}
