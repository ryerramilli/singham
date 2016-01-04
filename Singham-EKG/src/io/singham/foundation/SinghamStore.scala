package io.singham.foundation

object SinghamStore extends SinghamLogging{
  
  import com.thinkaurelius.titan.core._
  import gremlin.scala._
  
  val graph = TitanFactory.open("learn_titan.properties").asScala
  
  def shutdown() {
    
    logger.info("*** Disconnectiong from graph store ***")
    graph.close
    
  }
  
}