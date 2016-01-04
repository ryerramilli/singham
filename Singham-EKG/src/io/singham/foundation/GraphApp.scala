package io.singham.foundation

trait GraphApp 
  extends SinghamLogging {

  def run(code: () => Unit) {

    try {

      code()

    } finally {
      SinghamStore.graph.close
      logger.info("...Done")
    }

  }

  def runTransaction(code: () => Unit) {

    try {

      SinghamStore.graph.tx().open

      code()

      SinghamStore.graph.tx().commit()

    } finally {
      
      SinghamStore.shutdown
      
      logger.info("...Done")
    }

  }

}