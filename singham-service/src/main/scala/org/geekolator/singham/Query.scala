package org.geekolator.singham

import org.geekolator.singham.storage.SinghamStore
import org.geekolator.singham.wiremodels.Conversions.FromVertex
import org.geekolator.singham.wiremodels.RoadmapDeliverable

import org.geekolator.singham.dsl._
import org.geekolator.singham.dsl.graphConversions.Implicits.ForVertex
import org.geekolator.singham.dsl.Gremlin.Sugar
import gremlin.scala._

import org.apache.tinkerpop.gremlin.process.traversal.P
import org.apache.tinkerpop.gremlin.structure.Vertex

object Query extends SinghamLogging {

  def apps = getNodes[App]("App")
  def opportunities = getNodes[Opportunity]("Opportunity")
  def persons = getNodes[Person]("Person")
  def roadmaps = getNodes[Roadmap]("Roadmap")
  def subjectAreas = getNodes[SubjectArea]("SubjectArea")
  def vendors = getNodes[Vendor]("Vendor")
  
  def getNodes[T <: SinghamNode[T]](nodeLabel: String) : List[T] = 
     SinghamStore.graph.V.hasLabel(nodeLabel).map ( _.to[T]).toList()
  
  def opportunitiesOnRoadmap(roadmap: String) : List[RoadmapDeliverable] = {

      var edge : Option[gremlin.scala.Edge] = None
      SinghamStore.graph.roadmap(roadmap)
        .outE("delivers").sideEffect { e => edge = Some(e) }.inV().map { _.toRoadmapDeliverable(edge) }.toList()
      
  }
  
  def roadmapsCarryingOpportunity(opportunity: String) : List[Roadmap] = {
    
    SinghamStore.graph.opportunity(opportunity)
      .inE("delivers").outV().map { x => x.to[Roadmap] }.toList()
    
  }
  
}