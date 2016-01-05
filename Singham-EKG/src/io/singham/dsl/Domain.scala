package io.singham.dsl

case class Person(override val name: String) extends SinghamNode[Person] {
  def -->(edge: EnterpriseArchitect): EnterpriseArchitect = startEdge(this, edge).asInstanceOf[EnterpriseArchitect]
}


case class Vendor(override val name: String) extends SinghamNode[Vendor]

case class SubjectArea(override val name: String) extends SinghamNode[SubjectArea]

case class App(override val name: String) extends SinghamNode[App] {
  def -->(edge: Supplier): Supplier = startEdge(this, edge).asInstanceOf[Supplier]
  def -->(edge: Contact): Contact = startEdge(this, edge).asInstanceOf[Contact]
}

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