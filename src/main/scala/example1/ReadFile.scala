package example1

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.Source
import org.apache.commons.io.input.{TailerListenerAdapter, Tailer}

/**
 * Created by abelmeos on 2017/05/25.
 */
object ReadFile extends App{

  implicit val system = ActorSystem()
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()



  readContinuosly().runForeach(println)


  def readContinuosly[T](): Source[String,_] = {

    Source.queue[String](bufferSize = 1000,overflowStrategy = OverflowStrategy.fail).
    mapMaterializedValue{ queue =>
      Tailer.create(Paths.get("D:/2017/streams/prueba.txt").toFile, new TailerListenerAdapter{

        override  def handle(line:String):Unit = {
          queue.offer(line)
        }

      })
    }

  }



}
