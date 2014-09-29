
import com.typesafe.sbt.SbtNativePackager._
import twirl.sbt.TwirlPlugin._
import spray.revolver.RevolverPlugin.Revolver

organization  := "com.peerius"

version       := "1.0"

scalaVersion  := "2.11.2"

name := "access-log-processor"

//packagerSettings
net.virtualvoid.sbt.graph.Plugin.graphSettings

packageArchetype.java_application

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

//twirlImports := Seq("com.peerius.pages")

seq(Twirl.settings: _*)

libraryDependencies ++= {
  val akkaV = "2.3.5"
  val sprayV = "1.3.1"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test"
  )
}

Revolver.settings
