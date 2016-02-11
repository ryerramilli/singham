package org.geekolator.singham.dsl.crud

import org.geekolator.singham.SinghamLogging
import org.geekolator.singham.storage.SinghamStore

import org.geekolator.singham.dsl.{ Opportunity, Person, Roadmap }
import org.geekolator.singham.foundation.RelationshipBuilder
import org.geekolator.singham.dsl.graphConversions.Implicits.ForVertex
import org.geekolator.singham.dsl.Gremlin.Sugar

object Implicits extends SinghamLogging {

  implicit class ForOpportunity(opportunity: Opportunity) extends Savable[Opportunity]{

    def save(): Opportunity = {

      logger.debug(s"Saving opportunity ${opportunity} to datastore")
            
      import gremlin.scala.{ label => ignore, _ }

      import org.apache.tinkerpop.gremlin.process.traversal.P
      import org.geekolator.singham.dsl.graphConversions.ToMap.FromOpportunity

      val Name = Key[String]("name")

      logger.info(s"Looking in graph store for vertex Opportunity with name ${opportunity.name}")

      val existing = SinghamStore.graph.opportunity(opportunity.name).headOption()

      return existing.getOrElse(
        {

          logger.info(s"Vertex, Opportunity, with ${opportunity.name} does not exist in graph store. Creating new.")
          val opportunityVertex = SinghamStore.graph.addOpportunity(opportunity.toMap)

          val ebitVertex = SinghamStore.graph.ebit().head()

          opportunity.ebit match {
            case Some(ebit) => opportunityVertex.addEdge("contributes", ebitVertex,
              "amount", ebit.amount.asInstanceOf[java.lang.Long],
              "confidence", ebit.confidence.asInstanceOf[java.lang.Float])
            case _ => opportunityVertex.addEdge("contributes", ebitVertex)
          }

          opportunityVertex

        }).toOpportunity() 
    }
    

  }

  implicit class ForPerson(person: Person) extends Savable[Person] {
    def save() = saveX(person).toPerson()
  }

  implicit class ForRoadmap(roadmap: Roadmap) extends Savable[Roadmap] {
    def save() = saveX(roadmap).toRoadmap()
  }

}