package io.singham.apps

object SinghamSchema extends App with io.singham.foundation.SchemaPrimitives {

  ddlTransaction(createSchema)

  def createSchema() {

    import scala.collection.immutable.ListSet

    ListSet("Person", "SubjectArea", "Vendor", "Product", "opportunity", "strategy").foreach(defineVertex)
    ListSet("EnterpriseArchitect", "Supplier").foreach(defineEdge)
    ListSet("depth_chart", "name").foreach(defineProperty)
    

  }

}