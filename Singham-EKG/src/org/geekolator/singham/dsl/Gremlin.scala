package org.geekolator.singham.dsl

import gremlin.scala._
import com.thinkaurelius.titan.core.TitanGraph

object Gremlin {
  
  implicit class Sugar(scalaGraph: ScalaGraph[TitanGraph]) {
    
    def singhamVertices(label: String) = scalaGraph.V.hasLabel(label)
    
    def singhamVertex(label: String, name: String) = scalaGraph.V.has(label, Key[String]("name"), name)
    
    def addOpportunity(properties: Map[String,Any]) = scalaGraph.addVertex("Opportunity", properties)
    
    def ebit() =  singhamVertex("Kpi", "Ebit")
    
    def opportunities = singhamVertices("Opportunity")
    
    def opportunity(name: String) = singhamVertex("Opportunity", name)
    
    def roadmaps = singhamVertices("Roadmap")
    
    def roadmap(name: String) = singhamVertex("Roadmap", name)
    
  }
}