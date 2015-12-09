package stackoverflow
    
import scala.xml.{ NodeSeq, MetaData }
import java.io.File
import scala.io.{ BufferedSource, Source }

abstract class StackTable[T] {


  def getDate(n: scala.xml.NodeSeq): Long = n.text match {
    case "" => 0
    case s => dateFormat.parse(s).getTime
  }

  def dateFormat = {
    import java.text.SimpleDateFormat
    import java.util.TimeZone
    val f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
    f.setTimeZone(TimeZone.getTimeZone("GMT"))
    f
  }

  def getInt(n: scala.xml.NodeSeq): Int = n.text match {
    case "" => 0
    case x => x.toInt
  }

  def parseXml(x: scala.xml.Elem): T

  def parse(s: String): Option[T] =
    try{
        if (s.startsWith("  <row ")) Some(parseXml(scala.xml.XML.loadString(s)))
        else None
    }catch{
        //org.xml.sax.SAXParseException
        case _ => None // matches any error, and return None
    }
}