package stackoverflow

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object Main extends App {
   val minSplits = 4 
   System.setProperty("spark.executor.memory", "4g")
   System.setProperty("spark.executor.cores", "4")
   println("Start Spark.")
    
   val conf = new SparkConf().setAppName("Simple Application")
   val sc = new SparkContext(conf)
       
   val allVoters = sc.textFile("/home/vagrant/miniprojects/spark/data/spark-stats-data/allVotes/part-00*.xml.gz", minSplits)
   val allPosts = sc.textFile("/home/vagrant/miniprojects/spark/data/spark-stats-data/allPosts/part-00*.xml.gz", minSplits)
   val allUsers = sc.textFile("/home/vagrant/miniprojects/spark/data/spark-stats-data/allUsers/part-00*.xml.gz", minSplits)
       
   val top50 = allVoters.flatMap(Vote.parse).map({
   vote => (vote.postId, 
             (if (vote.voteTypeId == 5) 1 else 0, 
              if (vote.voteTypeId == 2) 1 else 0,
              if (vote.voteTypeId == 3) 1 else 0 ))
}).reduceByKey(
    (x,y)=>(x._1 + y._1, x._2 + y._2, x._3 + y._3)
).map({
    x => (x._2._1, (x._2._2, x._2._3))
}).reduceByKey(
    (x, y) => (x._1 + y._1, x._2 + y._2)
).sortByKey(true).take(50)

    top50.foreach(println)  
}