package org.geekolator.singham.apps

object SinghamSchema extends App with org.geekolator.singham.dsl.schema.Nouns {

  ddlTransaction(createSchema)

  def createSchema() {

    import scala.collection.immutable.ListSet

    ListSet("depth_chart", "name").foreach(defineProperty(_))
    ListSet("Person", "SubjectArea", "Vendor", "App", "opportunity", "strategy").foreach(vertex)
    ListSet("EnterpriseArchitect", "Supplier", "Contact").foreach(edge)
    
  }

}