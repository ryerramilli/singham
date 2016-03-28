package org.geekolator.singham.dsl.schema

import com.thinkaurelius.titan.core.Cardinality
import com.thinkaurelius.titan.core.{EdgeLabel, PropertyKey, TitanVertex, VertexLabel}

import org.apache.commons.configuration.PropertiesConfiguration

trait Nouns extends SinghamLogging with SchemaPrimitives{
  
  def vertex(label: String) : VertexLabel = {
    
    if (manager.containsVertexLabel(label)) {
      
      logger.warn(s"Vertex,$label, already exists, skipping creation")
      
      manager.getVertexLabel(label)
      
    }
    else {
      
      val vertexLabel = manager.makeVertexLabel(label).make
      
      val nameKey = manager.getPropertyKey("name")
      manager.buildIndex(s"unique-vertex-$label-name", classOf[TitanVertex])
          .addKey(nameKey)
          .indexOnly(vertexLabel)
          .unique()
          .buildCompositeIndex()
          
      return vertexLabel
          
    }
    
  }

  def edge(label: String) : EdgeLabel = {
    
    import com.thinkaurelius.titan.core.Multiplicity
    
    if (manager.containsEdgeLabel(label)) {
      logger.warn(s"Edge,$label, already exists, skipping creation")
      
      manager.getEdgeLabel(label)
      
    }
    else
      manager.makeEdgeLabel(label).multiplicity(Multiplicity.SIMPLE).make
  }

  def defineProperty(property: String, cardinality: Cardinality = Cardinality.SINGLE, 
      dataType: Class[_] = classOf[java.lang.String]) : PropertyKey = {
    {
      import com.thinkaurelius.titan.core._

      if (manager.containsPropertyKey(property)) {
        logger.warn(s"Property,$property, already exists, skipping creation")
        
        manager.getPropertyKey(property)
        
      }
      else {
       
        logger.info(s"Creating a new property: key=$property, cardinality=$cardinality, dataType=$dataType")
        
        manager.makePropertyKey(property)
          .dataType(dataType)
          .cardinality(cardinality)
          .make

      }
    }
  }
  
}