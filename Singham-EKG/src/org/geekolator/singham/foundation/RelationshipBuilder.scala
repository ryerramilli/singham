package org.geekolator.singham.foundation

import org.geekolator.singham.storage.SinghamStore
import gremlin.scala.Key
import gremlin.scala.wrap
import org.geekolator.singham.dsl.SinghamEdge
import org.geekolator.singham.dsl.SinghamNode
import org.geekolator.singham.dsl.crud.DbMapping.FromObject

object RelationshipBuilder
    extends org.geekolator.singham.SinghamLogging {

  import org.apache.tinkerpop.gremlin.structure.Vertex

  def build[A,B](nodeA: SinghamNode[A], edge: SinghamEdge[A,B], nodeB: SinghamNode[B]) {

    logger.info("Buiding relationships...")

    assert(nodeA != null, "nodeA must be provided")
    assert(nodeB != null, "nodeB must be provided")
    assert(nodeA != null, "edge must be privded")

    val vertexA = createVertexInDb(nodeA)
    val vertexB = createVertexInDb(nodeB)
    
    linkVerticesInDb(vertexA, vertexB, edge)

  }

  private def linkVerticesInDb[A,B](vertexA: Vertex, vertexB: Vertex, edge: SinghamEdge[A,B]) {
    
    import gremlin.scala._

    try
      vertexA --- edge.label --> vertexB
    catch {
      case ex: com.thinkaurelius.titan.core.SchemaViolationException => logger.warn(ex.getMessage)
    }
    
  }

  private def createVertexInDb[N](node: SinghamNode[N]): Vertex = {

    import gremlin.scala.{ label => ignore, _ }
    import org.apache.tinkerpop.gremlin.process.traversal.P

    val Name = Key[String]("name")

    logger.info(s"Looking in graph store for vertex ${node.getLabel} with name ${node.name}")

    val existing = SinghamStore.graph.V.hasLabel(node.getLabel).has(Name, P.eq(node.name)).headOption()

    return existing.getOrElse(
      {

        logger.info(s"....Vertex ${node.getLabel} with ${node.name} does not exist in graph store. Creating new.")
        SinghamStore.graph.addVertex(node.getLabel, "name" -> node.name)

      })

  }

}
