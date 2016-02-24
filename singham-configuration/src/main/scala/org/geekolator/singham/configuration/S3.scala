package org.geekolator.singham.configuration

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.auth.InstanceProfileCredentialsProvider

object S3 extends Logging {

  def downloadAsString_Blocking(path: String): String = {
    
    logger.info("Downloading from s3 using instance profiling")
    
    val client = new AmazonS3Client(new InstanceProfileCredentialsProvider())
    
    val request = new GetObjectRequest("key-vault", path)
    
    val s3Object = client.getObject(request)
    
    val stream = s3Object.getObjectContent
    
    try {
    
      scala.io.Source.fromInputStream(stream).getLines().mkString("\n")
    
    }
    finally {
      stream.close
    }    
  }
}