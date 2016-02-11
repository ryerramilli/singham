package org.geekolator.singham.dsl

/*** Nodes ***/
case class Person(override val internalId : Option[String], override val name: String) extends SinghamNode[Person] {
  
  def this(name: String) = this(None, name)
  
  def -->(edge: EnterpriseArchitect): EnterpriseArchitect = startEdge(this, edge).asInstanceOf[EnterpriseArchitect]
}

case class Vendor(override val internalId : Option[String], override val name: String) extends SinghamNode[Vendor] {
  def this(name: String) = this(None, name)
}

case class SubjectArea(override val internalId : Option[String], override val name: String) extends SinghamNode[SubjectArea] {
  
  def this(name: String) = this(None, name)
}

case class App(override val internalId : Option[String], override val name: String) extends SinghamNode[App] {
  
  def this(name: String) = this(None, name)
    
  def -->(edge: Supplier): Supplier = startEdge(this, edge).asInstanceOf[Supplier]
  def -->(edge: Contact): Contact = startEdge(this, edge).asInstanceOf[Contact]
}

case class Roadmap(override val internalId : Option[String], override val name: String) extends SinghamNode[Roadmap] {
  
  def this(name: String) = this(None, name)
  
  def -->(edge: Delivers): Delivers = startEdge(this, edge).asInstanceOf[Delivers]
}

case class Ebit(val amount: Long, val confidence: Float)

case class Opportunity(
    override val internalId : Option[String], 
    override val name: String,  
    val ebit : Option[Ebit], 
    val startDate: Option[Long], 
    val endDate : Option[Long]) extends SinghamNode[Opportunity] {
  
  def this(name: String) = this(None, name, None, None, None)
  
  def -->(edge: Impacts): Impacts = startEdge(this, edge).asInstanceOf[Impacts]
}

case class Event(override val internalId : Option[String], override val name: String) extends SinghamNode[Event] {
  
  def this(name: String) = this(None, name)
  
  def -->(edge: Influences): Influences = startEdge(this, edge).asInstanceOf[Influences]
}

case class Capability(override val internalId : Option[String], override val name: String) extends SinghamNode[Capability] {
  
  def this(name: String) = this(None, name)
  
}


/*** Edges ***/
case class EnterpriseArchitect() extends SinghamEdge[Person,SubjectArea] {
  
  def --> (nodeB: SubjectArea): SubjectArea = endEdge(nodeB)
  def --> (nodes: List[SubjectArea]): Unit = endEdge(nodes)

}

case class Supplier() extends SinghamEdge[App,Vendor] {

  def --> (nodeB: Vendor): Vendor = endEdge(nodeB)
  def --> (nodes: List[Vendor]): Unit = endEdge(nodes)

}

case class Contact() extends SinghamEdge[App,Person] {

  def --> (nodeB: Person): Person = endEdge(nodeB)
  def --> (nodes: List[Person]): Unit = endEdge(nodes)

}

case class Delivers() extends SinghamEdge[Roadmap,Opportunity] {

  def --> (nodeB: Opportunity): Opportunity = endEdge(nodeB)
  def --> (nodes: List[Opportunity]): Unit = endEdge(nodes)

}

case class Influences() extends SinghamEdge[Event,Roadmap] {

  def --> (nodeB: Roadmap): Roadmap = endEdge(nodeB)
  def --> (nodes: List[Roadmap]): Unit = endEdge(nodes)

}

case class Impacts() extends SinghamEdge[Opportunity,Capability] {

  def --> (nodeB: Capability): Capability = endEdge(nodeB)
  def --> (nodes: List[Capability]): Unit = endEdge(nodes)

}