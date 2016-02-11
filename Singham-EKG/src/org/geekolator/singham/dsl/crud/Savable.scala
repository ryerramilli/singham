package org.geekolator.singham.dsl.crud

import org.geekolator.singham.storage.SinghamStore
import org.geekolator.singham.SinghamLogging;
import org.geekolator.singham.dsl.SinghamNode
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.geekolator.singham.dsl.Gremlin.Sugar
import org.geekolator.singham.dsl.crud.DbMapping.FromObject

trait Savable[N] extends SinghamLogging {
  
  def save() : N
  
  protected def saveX(node: SinghamNode[N]): Vertex = {

    import gremlin.scala.{ label => ignore, _ }
    import org.apache.tinkerpop.gremlin.process.traversal.P

    val Name = Key[String]("name")
    
    logger.info(s"Looking in graph store for vertex ${node.getLabel} with name ${node.name}")

    val existing = SinghamStore.graph.singhamVertex(node.getLabel, node.name).headOption()

    return existing.getOrElse(
      {

        logger.info(s"____ Vertex ${node.getLabel} with ${node.name} does not exist in graph store. Creating new.")
        SinghamStore.graph.addVertex(node.getLabel, "name" -> node.name)

      })
  }
  
}