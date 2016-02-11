package org.geekolator.singham.data

import org.geekolator.singham.storage.SinghamStore

object SampleData
    extends App
    with org.geekolator.singham.storage.Transactable {

  import org.geekolator.singham.dsl._

  runTransaction(() =>
    {
      
      Person(None, "Bill Quan") --> EnterpriseArchitect() --> List(SubjectArea(None, "Product"), SubjectArea(None, "Recommendation"))

      Person(None, "Dianna Mistele") --> EnterpriseArchitect() --> List(SubjectArea(None, "Bank"), SubjectArea(None, "Loyalty"))

      Person(None, "Dewayne Cowles") --> EnterpriseArchitect() --> List(SubjectArea(None, "Payments"), SubjectArea(None, "Gift Card"))

      Person(None, "Gopi Parasurama") --> EnterpriseArchitect() --> List(SubjectArea(None, "Person HR"), SubjectArea(None, "Inventory"), SubjectArea(None, "Customer Order"), SubjectArea(None, "Routing"), SubjectArea(None, "Availability"), SubjectArea(None, "Supply Chain"))

      Person(None, "Ken Wolfson") --> EnterpriseArchitect() --> List(SubjectArea(None, "Price"), SubjectArea(None, "Promotions"), SubjectArea(None, "Merch Transactions"))

      Person(None, "Rajashree Parthasarathy") --> EnterpriseArchitect() --> List(SubjectArea(None, "Price"), SubjectArea(None, "Promotions"), SubjectArea(None, "Loyalty"))

      Person(None, "Rupender Malik") --> EnterpriseArchitect() --> List(SubjectArea(None, "Product"), SubjectArea(None, "Merch Transactions"))

      Person(None, "Sanjay Doiphode") --> EnterpriseArchitect() --> List(SubjectArea(None, "Product"), SubjectArea(None, "Vendor"))

      Person(None, "Syam Kunjukrishnan") --> EnterpriseArchitect() --> SubjectArea(None, "Customer Order")

      Person(None, "Wei Manfredi") --> EnterpriseArchitect() --> SubjectArea(None, "Customer")
      
      App(None, "Sparx")  --> Supplier() --> Vendor(None, "Sparx Systems")
      
      App(None, "IgniteXML") --> Supplier() --> Vendor(None, "digitalML")

    })

}