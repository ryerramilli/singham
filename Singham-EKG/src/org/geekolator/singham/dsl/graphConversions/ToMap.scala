package org.geekolator.singham.dsl.graphConversions

import org.geekolator.singham.dsl._

object ToMap {
  
  implicit class FromOpportunity(d: Opportunity) {
    
    def toMap() : Map[String,Any] = {
      
      implicit var map = Map[String,Any]( "name" -> d.name)
      
      d.startDate match { case Some(v) => map = map + ("startDate" -> v) case _ => }
      d.endDate match { case Some(v) => map = map + ("endDate" -> v) case _ => }
      
      map
      
    }
    
  }
  
}