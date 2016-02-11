package org.geekolator.singham.wiremodels

import org.geekolator.singham.dsl.graphConversions.Implicits.ForVertex
import org.geekolator.singham.dsl.Opportunity
import org.apache.tinkerpop.gremlin.structure.Vertex

import gremlin.scala.Edge

object Conversions extends org.geekolator.singham.SinghamLogging {
  
  implicit class FromVertex(vertex: Vertex) {
    
    def toRoadmapDeliverable(edge: Option[Edge]) : RoadmapDeliverable = {
      
      val opportunity = vertex.to[Opportunity]
      
      edge match {
        case Some(e) => { 
          e.keys().contains("role") match {
            case true => RoadmapDeliverable(opportunity, Some(e.property("role").value()))
            case _ => RoadmapDeliverable(opportunity, None)
          }
          
        }
        case _ => throw new Exception("This is unexpecated. There must always be edge")
      }
    }
    
  }
}