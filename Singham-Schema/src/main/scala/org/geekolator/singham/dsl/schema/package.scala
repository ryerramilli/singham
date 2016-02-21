package org.geekolator.singham.dsl

import com.typesafe.config.{Config,ConfigFactory}

package object schema {
  
  case class Setup(environemnt : String, backend: String)
  
  val config = ConfigFactory.load()
    
  val setup = Setup(config.getString("singham.environment"),
    config.getString("singham.titan-backend"))
      
}