package io.singham.foundation

trait SchemaPrimitives extends SinghamLogging {
  
  import com.thinkaurelius.titan.core._
  
  val graph =  TitanFactory.open("learn_titan.properties")

  val manager = graph.openManagement

  def defineVertex(vertex: String) {
    if (manager.containsVertexLabel(vertex))
      logger.warn(s"Vertex,$vertex, already exists, skipping creation")
    else {
      
      val vertexLabel = manager.makeVertexLabel(vertex).make
      
      val nameKey = manager.getPropertyKey("name")
      manager.buildIndex(s"unique-vertex-$vertex-name", classOf[TitanVertex])
          .addKey(nameKey)
          .indexOnly(vertexLabel)
          .unique()
          .buildCompositeIndex()
          
    }
  }

  def defineEdge(edge: String) {
    
    import com.thinkaurelius.titan.core.Multiplicity
    
    if (manager.containsEdgeLabel(edge))
      logger.warn(s"Edge,$edge, already exists, skipping creation")
    else
      manager.makeEdgeLabel(edge).multiplicity(Multiplicity.SIMPLE).make
  }

  def defineProperty(property: String) {
    {
      import com.thinkaurelius.titan.core._

      if (manager.containsPropertyKey(property))
        logger.warn(s"Property,$property, already exists, skipping creation")
      else {
        val propertyKey = manager.makePropertyKey(property)
          .dataType(classOf[java.lang.String])
          .cardinality(Cardinality.SINGLE)
          .make

      }
    }
  }

  def ddlTransaction(code: () => Unit) {
    try {

      code()

      manager.commit

    } finally {
      graph.close
      logger.info("...Done")
    }
  }
}