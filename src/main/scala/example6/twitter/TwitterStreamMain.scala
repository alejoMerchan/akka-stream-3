package example6.twitter

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import example6.twitter.model.{Author, Hashtag, Tweet}

/**
  * Created by ALEJANDRO on 26/09/2017.
  */
object TwitterStreamMain extends App{

  implicit val system = ActorSystem("example-twitter")
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val akkaTag = Hashtag("#akka")

  val twitterClient = new TwitterClient(system)

  twitterClient.init

  val tweets:Source[Tweet,NotUsed]

  val authors: Source[Author,NotUsed] = tweets.filter(_.hashtags.contains(akkaTag)).map(_.author)

  authors.runWith(Sink.foreach(println))

}
