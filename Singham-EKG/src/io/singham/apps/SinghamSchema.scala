package io.singham.apps

object SinghamSchema extends App with io.singham.foundation.SchemaPrimitives {

  ddlTransaction(createSchema)

  def createSchema() {

    import scala.collection.immutable.ListSet

    ListSet("depth_chart", "name").foreach(defineProperty)
    ListSet("Person", "SubjectArea", "Vendor", "App", "opportunity", "strategy").foreach(defineVertex)
    ListSet("EnterpriseArchitect", "Supplier", "Contact").foreach(defineEdge)
    
  }

}