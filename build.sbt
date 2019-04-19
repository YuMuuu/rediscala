import sbt.Keys.libraryDependencies

ThisBuild / organization := "com.kijima"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.1"

cancelable in Global := true

val typesafeConfig = "com.typesafe" % "config" % "1.3.4"

lazy val commonSettings = Seq(
    libraryDependencies ++= Seq(typesafeConfig)
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


