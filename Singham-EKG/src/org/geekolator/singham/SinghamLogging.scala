package org.geekolator.singham

import com.typesafe.scalalogging.Logger

trait SinghamLogging {
  
  import com.typesafe.scalalogging._
  import org.slf4j.LoggerFactory
  
  val logger = Logger(LoggerFactory.getLogger(getClass))
  
}