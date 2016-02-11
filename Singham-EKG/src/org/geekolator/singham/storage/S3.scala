package org.geekolator.singham.storage

import org.geekolator.singham.SinghamLogging

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

object S3 extends SinghamLogging {

  def downloadAsString_Blocking(path: String): String = {

    val file = "https://s3-us-west-2.amazonaws.com/singham-configuration/%s".format(path)
    logger.info(s"... Downloading : $file")
    
    implicit val timeout: Timeout = Timeout(30.seconds)
    
    implicit val system: ActorSystem = ActorSystem()
    import system.dispatcher // implicit execution context

    val futureResponse = (IO(Http) ? HttpRequest(GET, Uri(file))).mapTo[HttpResponse]

    logger.info(s"...Blocking till key is downloaded from s3")
    val response = Await.result(futureResponse, timeout.duration)

    response.entity.asString(HttpCharsets.`UTF-8`)

  }
}