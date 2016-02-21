package org.geekolator.singham.dsl.schema

import com.thinkaurelius.titan.core._

object SchemaDSL extends SinghamLogging  {

  implicit class VertexImplicit(vertex: VertexLabel) {

    def isDescribedBy(properties: PropertyKey*): VertexLabel = {

      import gremlin.scala._
      
      properties.foreach ( property => {

        try {
          
          logger.info(s"Attaching property, ${property.name}, to vertex, ${vertex.name}")
          
          vertex.addEdge("meta.schema-link", property)
  
        } catch {
          case ex: com.thinkaurelius.titan.core.SchemaViolationException => logger.warn(ex.getMessage)
        }
        
      })

      return vertex

    }

  }
  
  implicit class EdgeImplicit(edge: EdgeLabel) {
    
    def connects(vertexA: VertexLabel, vertexB: VertexLabel) {
      
      import gremlin.scala._

      try {
        
        logger.info(s"Attaching edge, ${edge.name}, to vertex, ${vertexA.name}")
        vertexA.addEdge("meta.schema-link", edge)

      } catch {
        case ex: com.thinkaurelius.titan.core.SchemaViolationException => logger.warn(ex.getMessage)
      }
      
      try {
        
        logger.info(s"Attaching edge, ${edge.name}, to vertex, ${vertexB.name}")
        vertexB.addEdge("meta.schema-link", edge)

      } catch {
        case ex: com.thinkaurelius.titan.core.SchemaViolationException => logger.warn(ex.getMessage)
      }
     
    }
    
    //TODO: Below is boiler place code
    def isDescribedBy(properties: PropertyKey*): EdgeLabel = {

      import gremlin.scala._
      
      properties.foreach ( property => {

        try {
          
          logger.info(s"Attaching property, ${property.name}, to edge, ${edge.name}")
          
          edge.addEdge("meta.schema-link", property)
  
        } catch {
          case ex: com.thinkaurelius.titan.core.SchemaViolationException => logger.warn(ex.getMessage)
        }
        
      })

      return edge

    }
    
  }
}