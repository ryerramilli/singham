package org.geekolator.singham.apps
import org.geekolator.singham.dsl.schema.SchemaPrimitives
import org.geekolator.singham.dsl.schema.SchemaDSL.VertexImplicit
import org.geekolator.singham.dsl.schema.SchemaDSL.EdgeImplicit
import com.thinkaurelius.titan.core.Cardinality
import  org.geekolator.singham.dsl.schema.SinghamLogging

object DefineSchema extends App  with SinghamLogging with SchemaPrimitives {
  
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
      
      edge("delivers").isDescribedBy(role).connects(roadmap , opportunity)
      edge("impacts").connects(opportunity , capability)
      edge("influences").connects(event , roadmap)
      edge("carries").connects(opportunity , risk)
      edge("contributes").connects(opportunity , kpi)
      
      // Global nodes
      //SinghamStore.graph.addVertex(node.label, "name" -> node.name)
      
    
    })
  
}