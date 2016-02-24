package org.geekolator.singham.configuration

import akka.actor.ActorSystem
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import spray.http.{HttpRequest, HttpResponse, HttpCharsets, Uri}
import spray.httpx.{RequestBuilding, ResponseTransformation}
import spray.http.HttpMethods._

import spray.json.pimpString
import scala.concurrent.Await
import spray.can.Http

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.auth.InstanceProfileCredentialsProvider

import java.io.ByteArrayOutputStream

object S3 extends Logging {

  def downloadAsString_Blocking(path: String): String = {
    
    logger.info("Downloading from s3 using instance profiling")
    
    val client = new AmazonS3Client(new InstanceProfileCredentialsProvider())
    
    val request = new GetObjectRequest("key-vault", path)
    
    val s3Object = client.getObject(request)
    
    val stream = s3Object.getObjectContent
    
    val secrets = scala.io.Source.fromInputStream(stream).getLines().mkString("\n")
    
    stream.close
    
    return secrets
    
  }
}