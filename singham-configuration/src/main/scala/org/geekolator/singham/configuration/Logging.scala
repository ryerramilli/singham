package org.geekolator.singham.configuration

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

trait Logging {
   
  val logger = Logger(LoggerFactory.getLogger(getClass))
  
}