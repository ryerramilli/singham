package org.geekolator.singham

import spray.json.DefaultJsonProtocol
import org.geekolator.singham.dsl._
import org.geekolator.singham.wiremodels._

import spray.json._
import spray.routing.HttpServiceActor
import spray.json.DefaultJsonProtocol._
import spray.httpx.SprayJsonSupport._


class GraphServiceActor extends HttpServiceActor {
  
  object SinghamJsonFormats extends DefaultJsonProtocol {
 
    implicit val appFormat = jsonFormat2(App)
    implicit val ebitFormat = jsonFormat2(Ebit)
    implicit val opportunityFormat = jsonFormat5(Opportunity)
    implicit val personFormat = jsonFormat2(Person)
    implicit val roadmapFormat = jsonFormat2(Roadmap)
    implicit val subjectAreaFormat = jsonFormat2(SubjectArea)
    implicit val vendorFormat = jsonFormat2(Vendor)
    implicit val roadmapDeliverableFormat = jsonFormat2(RoadmapDeliverable)
    
  }
  
  import SinghamJsonFormats._

  def receive = runRoute {
    
    path("meta") {
        get {
          
          respondWithMediaType(spray.http.MediaTypes.`application/json`)  {
            complete {
              About.details.toJson(mapFormat).toString()
            }
          }
        }
    } ~
    path("apps" / IntNumber) {
      name =>
        get {
          complete {
            List(99)
          }
        }
    } ~
      path("apps") {
        get {
          respondWithMediaType(spray.http.MediaTypes.`application/json`) {
            complete {
              Query.apps.toJson(listFormat).toString()
            }
          }
        } ~
          post {
            complete {
              List(99)
            }
          }
      } ~
      path("opportunities") {
        get {
          respondWithMediaType(spray.http.MediaTypes.`application/json`) {
            complete {
              Query.opportunities.toJson(listFormat).toString()
            }
          }
        } ~
        post {
          respondWithMediaType(spray.http.MediaTypes.`application/json`) {
            
            entity(as[Opportunity]) { opportunity =>
              detach() {
                complete {
                  Command.createOpportunity(opportunity).toJson(opportunityFormat).toString()
                }
              }
            }
            
          }
        }
      } ~
      path("opportunities" / Segment / "roadmaps") {
        (opportunity) => 
        get {
          respondWithMediaType(spray.http.MediaTypes.`application/json`) {
            complete {
              Query.roadmapsCarryingOpportunity(opportunity).toJson(listFormat).toString()
            }
          }
        }
      } ~
      path("opportunities" / Segment / "investmentRisks") {
         (opportunity) => 
         post {
           respondWithMediaType(spray.http.MediaTypes.`application/json`) {
             complete {
                //Command.attachInvestmentRisk(opportunity).toJson(personFormat).toString()
              List(opportunity)
            }
           }
         }
      } ~
      path("persons") {
        get {
          respondWithMediaType(spray.http.MediaTypes.`application/json`) {
            complete {
              Query.persons.toJson(listFormat).toString()
            }
          }
        } ~
        post {
          respondWithMediaType(spray.http.MediaTypes.`application/json`) {
            
            entity(as[Person]) { person =>
              detach() {
                complete {
                  Command.createPerson(person).toJson(personFormat).toString()
                }
              }
            }
            
          }
        }
      } ~
      path("roadmaps") {
          get {
            respondWithMediaType(spray.http.MediaTypes.`application/json`) {
              complete {
                Query.roadmaps.toJson(listFormat).toString()
              }
            }
          } ~
          post {
            respondWithMediaType(spray.http.MediaTypes.`application/json`) {
              
              entity(as[Roadmap]) { roadmap => 
                detach() {
                  complete {
                    Command.createRoadmap(roadmap).toJson(roadmapFormat).toString()
                  }
                }
              }
              
            }
          }
      } ~
      path("roadmaps" / Segment / "opportunities") {
        (roadmap) =>
        post {
          respondWithMediaType(spray.http.MediaTypes.`application/json`) {
            entity(as[List[RoadmapDeliverable]]) { deliverables => 
                  detach() {
                    complete {
                      
                      Command.linkRoadmapToOpportunities(roadmap, deliverables)
                      Query.opportunitiesOnRoadmap(roadmap).toJson(listFormat).toString()
                      
                }
              }
            }
          }
        } ~
        get {
          respondWithMediaType(spray.http.MediaTypes.`application/json`) {
            complete {
              Query.opportunitiesOnRoadmap(roadmap).toJson(listFormat).toString()
            }
          }
          
        }
      } ~
      path("subjectAreas") {
        get {
          complete {
           Query.subjectAreas.toJson(listFormat).toString()
          }
        } ~
          post {
            complete {
              List(99)
            }
          }
      } ~
      path("vendors" / Segment) {
        name =>
          get {
            complete {
              List("Brady", "Wilson")
            }
          }
      } ~
      path("vendors") {
        get {
            complete {
              
              import com.typesafe.config._
              
              val conf = ConfigFactory.load()
              
              List("Brady", "Wilson", 
                  conf.getString("spray.servlet.boot-class"),
                  conf.getString("singham.titan-backend"),
                  conf.getString("singham.environment")
                  )
              
            }
          } ~
            post {
              complete {
                List("Wilfork")
              }
            }
          
      }
  }
}