package example6.twitter

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import example6.twitter.model.{Author, Tweet}
import twitter4j._
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder

import scala.collection.JavaConverters._
import scala.collection._
import scala.util.Try

/**
  *
  * Cliente que crea conexion stream a twitter.
  *
  * Created by ALEJANDRO on 26/09/2017.
  */
class TwitterClient (val actorSystem: ActorSystem){

  val factory = new TwitterStreamFactory(new ConfigurationBuilder().build())
  val twitterStream = factory.getInstance()


  def init = {
    twitterStream.setOAuthConsumer("","")
    twitterStream.setOAuthAccessToken(new AccessToken("",""))
    twitterStream.addListener(listener)
    twitterStream.sample()
  }

  def listener = new StatusListener {override def onStallWarning(warning: StallWarning): Unit = ???

    override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = ???

    override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = ???

    override def onStatus(status: Status): Unit = {
      actorSystem.eventStream.publish(Tweet(Author(status.getUser.getScreenName),status.getCreatedAt.getTime,status.getText))
    }

    override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = ???

    override def onException(ex: Exception): Unit = ???
  }


}
