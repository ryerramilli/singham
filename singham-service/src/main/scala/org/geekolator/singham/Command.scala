package org.geekolator.singham

import org.geekolator.singham.storage.SinghamStore

import org.geekolator.singham.dsl.{ Opportunity, Person, Roadmap }
import org.geekolator.singham.dsl.crud.Implicits.{ ForOpportunity, ForPerson, ForRoadmap }
import org.geekolator.singham.wiremodels._
import org.geekolator.singham.wiremodels.Conversions
import org.geekolator.singham.dsl.graphConversions.Implicits.ForVertex

import org.apache.tinkerpop.gremlin.structure.Vertex

object Command
    extends org.geekolator.singham.storage.Transactable
    with org.geekolator.singham.SinghamLogging {

  def createOpportunity(opportunity: Opportunity): Opportunity = {

    runTransaction[Opportunity](() => {

      logger.info(s"Creating ${opportunity} in the database")

      opportunity.save()

    })

  }

  def createPerson(person: Person): Person = {

    runTransaction[Person](() => {

      logger.info(s"Creating ${person.name} in the database")

      person.save()

    })

  }

  def createRoadmap(roadmap: Roadmap): Roadmap = {

    logger.info(s"Attempting to save ${roadmap.name}")

    runTransaction[Roadmap](() => {

      roadmap.save()

    })

  }

  def linkRoadmapToOpportunities(roadmapName: String, deliverables: List[RoadmapDeliverable]) {
    
    import org.apache.tinkerpop.gremlin.process.traversal.P
    import org.geekolator.singham.dsl.SinghamNode
    import gremlin.scala._
    import scala.collection.JavaConverters._
    
    def find[T <: SinghamNode[T]](label:String, predicate: P[String]) = SinghamStore.graph.V.hasLabel(label).has(Key[String]("name"), predicate )
    
    runTransaction[Unit]( () => {
      
      val roadmapOption = find[Roadmap]("Roadmap", P.eq(roadmapName)).headOption()
      
      roadmapOption match {
        case Some(roadmapVertex) => {
          
          val oppChoices = deliverables.map(_.opportunity.name).asJava 
          val oppVertices = find[Opportunity]("Opportunity", P.within(oppChoices)).toList()
          
          deliverables.foreach { d => 
            val optional = find[Opportunity]("Opportunity", P.eq(d.opportunity.name)).headOption()
            optional match {
            
              case Some(opportunityVertex) => {
                logger.info(s"$roadmapVertex --- delivers --> $opportunityVertex")
                d.role match {
                  case Some(r) => roadmapVertex.addEdge("delivers", opportunityVertex, "role", r)
                  case _ => roadmapVertex.addEdge("delivers", opportunityVertex)
                }
                
              }
              case _ => logger.info(s"${d.opportunity.name} does not exist. Skipping association with roadmap - $roadmapName.")
            
            }
          }
        }
        case None => logger.warn(s"Roadmap, $roadmapName, does not exist in the system")
      }
    })
  }
}