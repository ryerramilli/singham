package org.geekolator.singham.apps

import org.geekolator.singham.storage.{SinghamStore,Transactable}

object WellKnownNodes
    extends App
    with Transactable {

  import org.geekolator.singham.dsl._

  runTransaction(() =>
    {
      
      SinghamStore.graph.addVertex("Kpi", "name" -> "Ebit")

    })

}