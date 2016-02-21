package org.geekolator.singham.configuration

import org.apache.commons.configuration.PropertiesConfiguration
object Configuration  extends Logging {
  
  def asProperties () : PropertiesConfiguration = {
    
    val p = clearProperties
    
    setup.environemnt match {
      case "local" => logger.warn("There are no secrets when running locally")
      case _ => addSecrets(p)
    }
    
    return p
  }
  
  private def clearProperties() : PropertiesConfiguration = {

    val propFile = "%s.%s.titandb.properties".format(
      setup.environemnt,
      setup.backend)

    val p = new PropertiesConfiguration(propFile)
    
    logger.info(s"Configuring titan using ${p.getFile}")
    
    return p
    
  }

  private def addSecrets(properties: PropertiesConfiguration) {

    val secret = SecretsStore.secret
   
    setup.backend match {
      case "dynamodb" => {
        //TODO: Can i not use IAM to do this?
        properties.addProperty("storage.dynamodb.client.credentials.constructor-args",
          "%s,%s".format(secret.username, secret.password))
      }
      case _ =>
    }

  }
}