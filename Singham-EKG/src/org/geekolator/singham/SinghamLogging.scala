package org.geekolator.singham

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

trait SinghamLogging {
   
  val logger = Logger(LoggerFactory.getLogger(getClass))
  
}