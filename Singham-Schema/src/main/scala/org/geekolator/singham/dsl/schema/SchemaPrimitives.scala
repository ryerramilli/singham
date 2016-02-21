package org.geekolator.singham.dsl.schema

import com.thinkaurelius.titan.core.Cardinality

import com.thinkaurelius.titan.core.EdgeLabel
import com.thinkaurelius.titan.core.Multiplicity
import com.thinkaurelius.titan.core.PropertyKey
import com.thinkaurelius.titan.core.TitanVertex
import com.thinkaurelius.titan.core.VertexLabel
import org.apache.tinkerpop.gremlin.structure.Vertex
import scala.collection.JavaConversions
import com.thinkaurelius.titan.core.EdgeLabel
import com.thinkaurelius.titan.core.PropertyKey
import com.thinkaurelius.titan.core.TitanVertex
import com.thinkaurelius.titan.core.VertexLabel
import org.apache.tinkerpop.gremlin.structure.Vertex
import com.thinkaurelius.titan.core.EdgeLabel
import com.thinkaurelius.titan.core.PropertyKey
import com.thinkaurelius.titan.core.TitanVertex
import com.thinkaurelius.titan.core.VertexLabel
import org.apache.tinkerpop.gremlin.structure.Vertex

import org.apache.commons.configuration.PropertiesConfiguration

trait SchemaPrimitives extends SinghamLogging {
  
  import com.thinkaurelius.titan.core._
  import org.apache.tinkerpop.gremlin.structure.Vertex
  import com.thinkaurelius.titan.core.schema.TitanManagement
  import scala.collection.JavaConversions._
  import com.typesafe.config.ConfigFactory
  
  private val graphHolder = new ThreadLocal[TitanGraph]
  private val managerHolder = new ThreadLocal[TitanManagement]
  
  graphHolder.set(instantiateGraph)
  managerHolder.set(instantiateManager)
   
  def graph = graphHolder.get
  def manager = managerHolder.get
  
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
  
  def defineRelation(vertexA: Vertex, vertexB: Vertex) {
    
    import gremlin.scala._

    try {
    
      vertexA.addEdge("meta.schema-link", vertexB) 
      
    }
    catch {
      case ex: com.thinkaurelius.titan.core.SchemaViolationException => logger.warn(ex.getMessage)
    }
    
  }

  def ddlTransaction(code: () => Unit) {
    try {

      code()
      logger.debug("Doing commit")
      manager.commit

    } finally {
      graph.close
      logger.info("...Done")
    }
  }
  
  def getVertices() : List[VertexLabel] = manager.getVertexLabels.toList
  
  def getProperties() : List[PropertyKey] = manager.getRelationTypes(classOf[PropertyKey]).toList
  
  def getEdges() : List[EdgeLabel] = manager.getRelationTypes(classOf[EdgeLabel]).toList
  
  def getIndexes()  {
    
    manager.getGraphIndexes(classOf[PropertyKey]).foreach { println _ }
    manager.getGraphIndexes(classOf[EdgeLabel]).foreach { println _ }
    manager.getGraphIndexes(classOf[VertexLabel]).foreach { println _ }
    manager.getGraphIndexes(classOf[Vertex]).foreach { println _ }
    
    this.getProperties().foreach { p => manager.getRelationIndexes(p).foreach { println _ }}
    this.getEdges().foreach { p => manager.getRelationIndexes(p).foreach { println _ }}
    //this.getVertices().foreach { p => manager.getRelationIndexes(p).foreach { println _ }}
    
    val i = manager.getGraphIndex("unique-vertex-Person-name")
    
    // How to Check for Java null??
    if( i != null) i.getFieldKeys.foreach { println _ }
    
    println("#####")
    
  }
  
  private def instantiateGraph() : TitanGraph = {
    
     val properties = org.geekolator.singham.configuration.Configuration.asProperties()
    
    logger.info("Instantiating graph for thread # " + Thread.currentThread.getId)
     
    TitanFactory.open(properties)
    
  }

  
  private def instantiateManager() : TitanManagement = {
    
    logger.info("Instantiating schema manager for thread # " + Thread.currentThread.getId)
    graph.openManagement
  
  }
  
}