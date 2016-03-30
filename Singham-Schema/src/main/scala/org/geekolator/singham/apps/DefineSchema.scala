package org.geekolator.singham.apps
import org.geekolator.singham.dsl.schema.Nouns
import org.geekolator.singham.dsl.schema.Phrases.VertexImplicit
import org.geekolator.singham.dsl.schema.Phrases.EdgeImplicit
import com.thinkaurelius.titan.core.Cardinality

object DefineSchema extends App with Nouns {
  
  ddlTransaction(() =>  
    {
      
      edge("meta.schema-link")
 
      val amount = defineProperty("amount", Cardinality.SINGLE, classOf[java.lang.Long])
      val confidence = defineProperty("confidence", Cardinality.SINGLE, classOf[java.lang.Float])
      val date = defineProperty("date", Cardinality.SINGLE, classOf[java.lang.Long])
      val endDate = defineProperty("endDate", Cardinality.SINGLE, classOf[java.lang.Long])
      val name = defineProperty("name")
      val role = defineProperty("role")
      val startDate = defineProperty("startDate", Cardinality.SINGLE, classOf[java.lang.Long])
      val description = defineProperty("description", Cardinality.SINGLE)
  
      val kpi = vertex("Kpi")
      val event = vertex("Event").isDescribedBy(name, date)
      val capability = vertex("Capability").isDescribedBy(name)
      val opportunity = vertex("Opportunity").isDescribedBy(name, startDate, endDate)
      val roadmap = vertex("Roadmap").isDescribedBy(name)
      val risk = vertex("InvestmentRisk").isDescribedBy(description)
      
      edge("delivers").isDescribedBy(role).itConnects(roadmap , opportunity)
      edge("impacts").itConnects(opportunity , capability)
      edge("influences").itConnects(event , roadmap)
      edge("carries").itConnects(opportunity , risk)
      edge("contributes").itConnects(opportunity , kpi)
      
      // Global nodes
      //SinghamStore.graph.addVertex(node.label, "name" -> node.name)
      
    
    })
    
    System exit 0
  
}