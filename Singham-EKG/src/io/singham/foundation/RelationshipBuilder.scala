package io.singham.foundation

import gremlin.scala.Key
import gremlin.scala.wrap
import io.singham.dsl.SinghamEdge
import io.singham.dsl.SinghamNode

object RelationshipBuilder
    extends io.singham.foundation.SinghamLogging {

  import org.apache.tinkerpop.gremlin.structure.Vertex
  import io.singham.foundation.SinghamStore

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

    logger.info(s"Looking in graph store for vertex ${node.label} with name ${node.name}")

    val existing = SinghamStore.graph.V.hasLabel(node.label).has(Name, P.eq(node.name)).headOption()

    return existing.getOrElse(
      {

        logger.info(s"....Vertex ${node.label} with ${node.name} does not exist in graph store. Creating new.")
        SinghamStore.graph.addVertex(node.label, "name" -> node.name)

      })

  }

}
