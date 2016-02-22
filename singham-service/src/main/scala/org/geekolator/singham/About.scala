package org.geekolator.singham

import com.typesafe.config.{Config,ConfigFactory}

object About {
  
  def details : Map[String,String]  = {
   
    val config = ConfigFactory.load()
          
    Map( "environment" -> config.getString("singham.environment"), 
        "titan.backend" -> config.getString("singham.titan-backend")
        )
      
  }
    
}