package org.geekolator.singham.configuration

object SecretsStore extends Logging {

  def secret(): KeyDetails = {

    try {

      fromS3

    } catch {

      case _: Throwable => {
        
        logger.warn("We prevented from accessing secrets in s3, look in local")
        fromLocal
      }

    }

  }

  def fromS3(): KeyDetails = {

    import spray.json._
    import DefaultJsonProtocol._

    implicit val format = jsonFormat2(KeyDetails)

    val secretsPath = "%s.%s.titandb.json".format(setup.environemnt, setup.backend)

    val content = S3.downloadAsString_Blocking(secretsPath)

    content.parseJson.convertTo[KeyDetails]

  }

  def fromLocal(): KeyDetails = {

    import com.amazonaws.auth.profile.ProfileCredentialsProvider

    val credentials = new ProfileCredentialsProvider().getCredentials()

    new KeyDetails(credentials.getAWSAccessKeyId, credentials.getAWSSecretKey)

  }

  case class KeyDetails(val username: String, val password: String)
}