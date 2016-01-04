package io.singham.apps

object SinghamPrintf extends App with io.singham.foundation.GraphApp {

  run(print)

  def print() {
    
    import io.singham.foundation.SinghamStore
    
    List("Person", "Product").foreach { island =>
      
      SinghamStore.graph.V.hasLabel(island).sideEffect(v => logger.info(v.property("name").value()))
        .out().sideEffect(v => logger.info("==" + v.property("name").value()))
        .toList()
   
    }

  }
}