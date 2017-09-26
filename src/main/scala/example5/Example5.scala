package example5

import java.nio.file.Paths

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString

import scala.concurrent.Future

object Example5  extends App{


  implicit val system = ActorSystem("example-5")
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val source:Source[Int,NotUsed] = Source( 1 to 100)

  val done:Future[Done] = source.runForeach( i => println(i))

  /**
    * El source puede ser reutilizado e incorporado en otros blueprint.
    */
  val factorials = source.scan(BigInt(1))((acc,next) => acc * next)


  val result:Future[IOResult] = factorials.map(num => ByteString(s"$num\n")).runWith(FileIO.toPath(Paths.get("")))

  done.onComplete(_ => system.terminate())

}
