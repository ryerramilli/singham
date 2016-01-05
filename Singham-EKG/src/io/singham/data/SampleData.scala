package io.singham.data

import io.singham.foundation.SinghamStore

object SampleData
    extends App
    with io.singham.foundation.GraphApp {

  import io.singham.dsl._

  runTransaction(() =>
    {
      
      Person("Bill Quan") --> EnterpriseArchitect() --> List(SubjectArea("Product"), SubjectArea("Recommendation"))

      Person("Dianna Mistele") --> EnterpriseArchitect() --> List(SubjectArea("Bank"), SubjectArea("Loyalty"))

      Person("Dewayne Cowles") --> EnterpriseArchitect() --> List(SubjectArea("Payments"), SubjectArea("Gift Card"))

      Person("Gopi Parasurama") --> EnterpriseArchitect() --> List(SubjectArea("Person HR"), SubjectArea("Inventory"), SubjectArea("Customer Order"), SubjectArea("Routing"), SubjectArea("Availability"), SubjectArea("Supply Chain"))

      Person("Ken Wolfson") --> EnterpriseArchitect() --> List(SubjectArea("Price"), SubjectArea("Promotions"), SubjectArea("Merch Transactions"))

      Person("Rajashree Parthasarathy") --> EnterpriseArchitect() --> List(SubjectArea("Price"), SubjectArea("Promotions"), SubjectArea("Loyalty"))

      Person("Rupender Malik") --> EnterpriseArchitect() --> List(SubjectArea("Product"), SubjectArea("Merch Transactions"))

      Person("Sanjay Doiphode") --> EnterpriseArchitect() --> List(SubjectArea("Product"), SubjectArea("Vendor"))

      Person("Syam Kunjukrishnan") --> EnterpriseArchitect() --> SubjectArea("Customer Order")

      Person("Wei Manfredi") --> EnterpriseArchitect() --> SubjectArea("Customer")
      
      App("Sparx")  --> Supplier() --> Vendor("Sparx Systems")
      
      App("IgniteXML") --> Supplier() --> Vendor("digitalML")

    })

}