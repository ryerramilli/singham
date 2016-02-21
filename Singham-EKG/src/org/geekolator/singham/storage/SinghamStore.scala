package org.geekolator.singham.storage

import org.geekolator.singham.SinghamLogging
import org.geekolator.singham.configuration.Configuration
import gremlin.scala.GraphAsScala
import gremlin.scala.ScalaGraph
import org.apache.tinkerpop.gremlin.structure.Transaction

object SinghamStore extends SinghamLogging {

  import com.thinkaurelius.titan.core._
  import gremlin.scala._
  
  private val tl = new ThreadLocal[ScalaGraph[TitanGraph]]

  def graph = {

    logger.info("Fetching graph instance for thread local. Current Thread is " + Thread.currentThread().getId)

    if (tl.get == null) {

      logger.info("Graph instance does not exist in Thread Local. Initializing now. Current Thread is " + Thread.currentThread().getId)
      
      val properties = Configuration.asProperties()

      tl.set(TitanFactory.open(properties).asScala)

    }

    tl.get

  }

  private[storage] def shutdown() {

    logger.info("*** Disconnectiong from graph store ***")
    graph.close

  }

  private[storage] def openTransaction(): Transaction = {

    val t = graph.tx

    if (!t.isOpen) {
      logger.info("Transaction is currently not open. Openning a fresh one")
      graph.tx.open()
    }

    graph.tx

  }

  private[storage] def commitTransaction() {
    graph.tx.commit
  }
}