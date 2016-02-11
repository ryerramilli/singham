package org.geekolator.singham.dsl.graphConversions

import org.geekolator.singham.SinghamLogging
import org.geekolator.singham.storage.SinghamStore
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.geekolator.singham.dsl.{ App, Opportunity, Person, Roadmap, Vendor, SinghamNode, Ebit }
import gremlin.scala._
import org.apache.tinkerpop.gremlin.process.traversal.P

object Implicits extends SinghamLogging {

  implicit class ForVertex(v: Vertex) {

    private def valueOf[T](key: String): Option[T] =
      v.keys().contains(key) match { case true => Some(v.value(key)) case _ => None }

    def toApp(): App = {

      logger.debug("Converting vertex to a app object")

      val name = if (v.value("name") != null) v.value("name") else "--"

      App(Some(v.id.toString), name)

    }

    def toOpportunity(): Opportunity = {

      logger.info("::::Converting vertex to a opportunity object:_:_:")

      import org.geekolator.singham.dsl.Gremlin.Sugar

      val optionalEbit = SinghamStore.graph.opportunity(v.value("name").toString())
        .outE().filter { edge => "Kpi" == edge.inVertex().label()  && "Ebit" == edge.inVertex().property("name").value()  }
        .map { edge =>
          {

            def getOrElse0[T](key: String, default: T): T = if (edge.keys().contains(key)) {
              logger.info("Property exists: " + edge.property(key).value())
              edge.property(key).value().asInstanceOf[T]
            } else {
              logger.warn("Property does not exist, using default")
              default
            }

            val amount = getOrElse0[Long]("amount", 0L)
            val confidence = getOrElse0[Float]("confidence", 0f)

            new Ebit(amount, confidence)

          }
        }
        .headOption()

      logger.info("optional Ebit " + optionalEbit)
      Opportunity(Some(v.id.toString), v.value("name"),
        optionalEbit, valueOf[Long]("startDate"), valueOf[Long]("endDate"))

    }

    def toRoadmap(): Roadmap = {

      logger.debug("Converting vertex to a roadmap object")

      val name = if (v.value("name") != null) v.value("name") else "--"

      Roadmap(Some(v.id.toString), name)

    }

    def toPerson(): Person = {

      logger.debug("Converting vertex to a roadmap object")

      val name = if (v.value("name") != null) v.value("name") else "--"

      Person(Some(v.id.toString), name)

    }

    def toVendor(): Vendor = {

      logger.debug("Converting vertex to a vendor object")

      val name = if (v.value("name") != null) v.value("name") else "--"

      Vendor(Some(v.id.toString), name)

    }

    def to[T <: SinghamNode[T]](): T = {

      v.label match {

        case "Opportunity" => v.toOpportunity().asInstanceOf[T]
        case "Person"      => v.toPerson().asInstanceOf[T]
        case "Roadmap"     => v.toRoadmap().asInstanceOf[T]

        case _             => throw new IllegalStateException("Hey, implement this thing...");
      }

    }

  }
}