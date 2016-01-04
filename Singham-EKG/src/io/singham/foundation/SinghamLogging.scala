package io.singham.foundation

trait SinghamLogging {
  
  import com.typesafe.scalalogging._
  import org.slf4j.LoggerFactory
  
  val logger = Logger(LoggerFactory.getLogger(getClass))
  
}