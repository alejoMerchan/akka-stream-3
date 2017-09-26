package example4

import java.io.File
import java.nio.file.{Path, Paths, StandardOpenOption}
import java.nio.file.StandardOpenOption._

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl.{FileIO, RunnableGraph, Sink, Source}
import akka.util.ByteString

import scala.concurrent.Future

object ReadFile4 extends App{

  implicit val system = ActorSystem("read-file-4")
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val file = Paths.get("D:/2017/streams/prueba3.txt")

  val source:Source[ByteString, Future[IOResult]] = FileIO.fromPath(file)

  val sink: Sink[ByteString , Future[IOResult]] = FileIO.toPath(file,Set(CREATE , WRITE, APPEND))

  val runnableGraph:RunnableGraph[Future[IOResult]] = source.to(sink)

  runnableGraph.run().foreach{
    result =>
      println(s"${result.status}, ${result.count} bytes read.")
      system.terminate()
  }


}
