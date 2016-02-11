package org.geekolator.singham.storage

import org.geekolator.singham.SinghamLogging

trait Transactable extends SinghamLogging {
  
  def run(code: () => Unit) {

    try {

      code()

    } finally {
      SinghamStore.graph.close
      logger.info("...Done")
    }

  }

  def runTransaction(code: () => Unit) {
    
    //TODO: refactor this to runTransaction[Unit](code)

    try {

      SinghamStore.graph.tx().open

      code()

      SinghamStore.graph.tx().commit()

    } finally {
      
      SinghamStore.shutdown
      
      logger.info("...Done")
    }

  }
  
  def runTransaction[T](code: () => T) : T = {

      SinghamStore.openTransaction

      val t = code()

      SinghamStore.commitTransaction
    
      return t

  }

}