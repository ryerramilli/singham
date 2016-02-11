package org.geekolator.singham.apps

import org.geekolator.singham.dsl.Gremlin.Sugar

object SinghamPrintf extends App with org.geekolator.singham.storage.Transactable {
  
  run(print)

  def print() {
    
    import org.geekolator.singham.storage.SinghamStore
    
    List(/*"Vendors", "App", "Roadmap", */"Opportunity").foreach { island =>
      
      SinghamStore.graph.singhamVertices(island).sideEffect(v => logger.info(v.property("name").value()))
        .out().sideEffect(v => logger.info("==" + v.property("name").value()))
        .toList()
   
    }
    
    println("---------------------------------")
    import gremlin.scala._
    import org.apache.tinkerpop.gremlin.process.traversal.P
    
    var edge : Option[Edge] = None
    var vertex : Option[Vertex] = None
    SinghamStore.graph.opportunities.
        outE().sideEffect { e => edge = Some(e) }.outV().
        sideEffect(v => vertex = Some(v)).map (v => edge ).toList().foreach { println _ }
        
   println("####################")     
   SinghamStore.graph.opportunity("TestOpportunity-2")
     .out().toList().foreach { v => logger.info(">>" + v.property("name").value()) }
   
   SinghamStore.graph.opportunity("TestOpportunity-5")
     .out().has("Kpi", Key[String]("name"), "Ebit")
     .inE().toList().foreach { v => logger.info("__" + v.property("amount").value()) }
    
    
    println("*********************************************")
    SinghamStore.graph.opportunities.
        outE("contributes").store("contributes-edge").outV().store("ebit-node").
        has(Key[String]("name"), P.eq("Ebit")).toList().foreach { println _ }
    
    
    println("-----------")
    SinghamStore.graph.ebit().inE().filter { x => {
          println(x.outVertex().property("name").value())
          "TestOpportunity-6" == x.outVertex().property("name").value()
        }
    }
//      .has("Opportunity",Key[String]("name"),  "TestOpportunity-5")
      .toList().foreach { println _ }

  }
}