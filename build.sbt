import sbt.Keys.libraryDependencies

ThisBuild / organization := "com.kijima"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.8"

cancelable in Global := true

val typesafeConfig = "com.typesafe" % "config" % "1.3.4"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5" % "test"
val scalaz = "org.scalaz" %% "scalaz-core" % "7.2.27"

lazy val commonSettings = Seq(
    libraryDependencies ++= Seq(typesafeConfig, scalaz, scalaTest)
)

lazy val app = (project in file("Server")).
  settings(
    name := "server",
    commonSettings
  )


lazy val client = (project in file("Client")).
  settings(
    name := "client",
    commonSettings
  )


