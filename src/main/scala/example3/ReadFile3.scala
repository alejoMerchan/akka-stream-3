package example3

import java.nio.file.Paths

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{FileIO, Flow, Framing, Sink}
import akka.util.ByteString

import scala.concurrent.Await
import scala.concurrent.duration.Duration


/**
  * Ejemplo de  un  flow apartir de un archivo de texto y la conversion en mensajes de negocio.
  */
object ReadFile3 extends App{

  implicit val system = ActorSystem("read-file-3")
  implicit val materializer = ActorMaterializer()

  val file = Paths.get("D:/2017/streams/prueba3.txt")

  case class Letra(nombre:String)


  /**
    * Decodificacion del stream continuo en un stream de elementos discretos (framing)
    */
  private val framing:Flow[ByteString, ByteString, NotUsed] =
    Framing.delimiter(ByteString("\n"),maximumFrameLength = 256,allowTruncation = true)

  /**
    * COnversion de cada byteString del stream a un array de strings.
    */
  private val parsing:ByteString => Array[String] = _.utf8String.split(",")

  /**
    * Conversion de los elementos del sarray de strings en objetos de negocio.
    */
  private val conversation:Array[String] => Letra = s => Letra(nombre = s(0))


  val result = FileIO.fromPath(file).via(framing).map(parsing).map(conversation)
    .to(Sink.foreach(println(_))).
    run()

  Await.ready(result,Duration.Inf)
  system.terminate()

}
