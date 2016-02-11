package org.geekolator.singham.storage

import org.geekolator.singham.SinghamLogging

object SecretsStore extends SinghamLogging {
  
  def secret(): KeyDetails = {

    import spray.json._
    import DefaultJsonProtocol._

    implicit val format = jsonFormat2(KeyDetails)

    val secretsPath = "%s.%s.titandb.json".format(setup.environemnt, setup.backend)
    
    val content = S3.downloadAsString_Blocking(secretsPath)
    
    content.parseJson.convertTo[KeyDetails]

  }

  case class KeyDetails(val username: String, val password: String)
}