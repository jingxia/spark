package stackoverflow
    
import scala.xml.{ NodeSeq, MetaData }
import java.io.File
import scala.io.{ BufferedSource, Source }

object Post extends StackTable[Post] {

//   val file = new File("data/Posts.xml")
//   assert(file.exists)

  override def parseXml(x: scala.xml.Elem): Post = Post(
    getInt(x \ "@Id"),
    getInt(x \ "@PostTypeId"),
    getInt(x \ "@AcceptedAnswerId"),
    getDate(x \ "@CreationDate"),
    getInt(x \ "@Score"),
    getInt(x \ "@ViewCount"),
    (x \ "@Body").text,
    getInt(x \ "@OwnerUserId"),
    getDate(x \ "@LastActivityDate"),
    (x \ "@Title").text,
    getTags(x \ "@Tags"),
    getInt(x \ "@AnswerCount"),
    getInt(x \ "@CommentCount"),
    getInt(x \ "@FavoriteCount"),
    getDate(x \ "@CommunityOwnedDate"))

  def getTags(x: scala.xml.NodeSeq): Array[String] = x.text match {
    case "" => Array()
    case s => s.drop(1).dropRight(1).split("><")
  }
}

case class Post(
  id: Int,
  postTypeId: Int,
  acceptedAnswerId: Int,
  creationDate: Long,
  score: Int,
  viewCount: Int,
  body: String,
  ownerUserId: Int,
  lastActivityDate: Long,
  title: String,
  tags: Array[String],
  answerCount: Int,
  commentCount: Int,
  favoriteCount: Int,
  communityOwnedDate: Long) 