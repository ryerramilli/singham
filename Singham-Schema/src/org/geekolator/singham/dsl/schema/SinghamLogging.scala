package org.geekolator.singham.dsl.schema

import com.typesafe.scalalogging.Logger

trait SinghamLogging {
  
  import com.typesafe.scalalogging._
  import org.slf4j.LoggerFactory
  
  val logger = Logger(LoggerFactory.getLogger(getClass))
  
}