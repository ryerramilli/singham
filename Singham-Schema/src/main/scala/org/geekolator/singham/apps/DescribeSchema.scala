package org.geekolator.singham.apps

object DescribeSchema extends App with org.geekolator.singham.dsl.schema.SchemaPrimitives {
  
  import scala.collection.JavaConversions._
  
  getVertices().foreach { v => {
    
    println(v)
    
  }}
  
  
  println("===============================")
  
  
  this.getProperties().foreach { p => {
    
      println(">>>>>>>>>>")
      println( p.name())
      println( p.dataType())
      println( p.cardinality())
      println("<<<<<<<<<<<<")
    }
  }
  
  
  println("===============================")
  
  this.getEdges().foreach { e => {
    
      println("+++++")
      println(e.name)
      println(e.multiplicity)
      println(e.isDirected())
      println(e.isUnidirected())
      println("*****")
    }
  }
  
  println("&&&&&&&")
  
  this.getIndexes()
  
  System exit 0
  
}