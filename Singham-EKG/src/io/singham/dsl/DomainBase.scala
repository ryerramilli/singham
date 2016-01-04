package io.singham.dsl

import io.singham.foundation.RelationshipBuilder

abstract class SinghamNode[A]() extends io.singham.foundation.SinghamLogging {

  def label: String = getClass.getSimpleName

  def name: String
    
  protected def startEdge[B](nodeA: SinghamNode[A], edge: SinghamEdge[A,B]):  SinghamEdge[A,B] = {
    
    logger.info(s"Attaching ${edge.getClass.getSimpleName} edge")

    //TODO: This bothers me
    // why do I need to store state in edge?
    // why can't I use this
    edge.nodeA = nodeA
    
    return edge
    
  }

}

class SinghamEdge[A,B]() extends io.singham.foundation.SinghamLogging {
  
  def label: String = getClass.getSimpleName
  var nodeA: SinghamNode[A] = null
  
  protected def endEdge (nodeB: SinghamNode[B]): B = {

    logger.info("Attaching ${nodeB.getClass.getName} node")

    RelationshipBuilder.build(nodeA, this, nodeB)
    
    return nodeB.asInstanceOf[B]

  }
  
  protected def endEdge (nodes: List[SinghamNode[B]]): Unit = {

    logger.info("Attaching multiple nodes")

    nodes.foreach { node => endEdge(node) }

  }
  
}
