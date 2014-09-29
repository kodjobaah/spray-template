package com.example

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._



// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {



  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {

  val JavaScriptHome = "C:\\\\Users\\\\whatamidoing\\\\peerius\\\\spray-template\\\\javascript\\\\"
  val myRoute =
    path("") {
      get {
        respondWithMediaType(`text/html`) {
          // XML is marshalled to `text/xml` by default, so we simply override here
          println("getting pages -- 1")
          complete {
            com.peerius.pages.html.index("kodjo", 22).toString
          }
        }
      }
    } ~
    pathPrefix("javascript" / Segment / Segment) { (javascriptType,javascriptFile) =>
        //  println("looking:" + javascriptType)
        //  println("javacript file:"+ javascriptFile)


          javascriptType match {

            case "bootstrap" =>
              println("--in boot strap:"+javascriptFile)
              path(Segments) { (file) =>

                val loc: String = JavaScriptHome + javascriptType + "\\" + javascriptFile + "\\" + file.mkString("/")
                println("bootstrap:"+loc)
                getFromFile(loc)
              }


              /*
              path(Segment / Segment ) { (fileType, fileName) =>
                val loc: String = JavaScriptHome + javascriptType + "\\" + javascriptFile + "\\" + fileType+"\\" + fileName
                println("datepicker:"+loc)
                getFromFile(loc)
              }
              */


            case _ =>
              val loc: String = JavaScriptHome + javascriptType + "\\" + javascriptFile
              println("default:"+loc)
              getFromFile(loc)

          }
    } ~
    path("startProcessor") {

      parameters('appServer.as[String],'startDate.as[String]) { (as,sd) =>
          println("[startDate="+sd+"][appServer="+as+"]")
          complete {
          "done"
        }
      }


    }



}