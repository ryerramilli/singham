package org.geekolator.singham

import akka.actor.{ActorSystem, Props}
import spray.servlet.WebBoot

class Bootstrap extends WebBoot {
  
  val system = ActorSystem("graph")
  
  val serviceActor = system.actorOf(Props[GraphServiceActor])
  
}
