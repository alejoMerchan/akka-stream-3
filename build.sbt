name := "akka-stream-3"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-http-core" % "10.0.0",
  "com.typesafe.akka" %% "akka-http" % "10.0.0",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.0",
  "com.typesafe.akka" %% "akka-http-jackson" % "10.0.0",
  "com.typesafe.akka" %% "akka-http-xml" % "10.0.0",
  "commons-io" % "commons-io" % "2.5"

)