package example6.twitter.model

/**
  * Created by ALEJANDRO on 26/09/2017.
  */
class Twitter {

}

final case class Author(handle: String)

final case class Hashtag(name: String)

case class Tweet(author: Author, timestamp: Long, body: String) {
  def hashtags: Set[Hashtag] = body.split(" ").collect {
    case t if t.startsWith("#") => Hashtag(t.replaceAll("[^#\\w]", ""))
  }.toSet
}


