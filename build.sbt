ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "Exam"
  )

val http4sVersion = "0.21.33"
val circeVersion = "0.13.0"
val doobieVersion = "0.13.4"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-generic-extras" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion
  ,
  "org.http4s" %% "http4s-circe" % http4sVersion
  ,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion
  ,
  "org.http4s" %% "http4s-dsl" % http4sVersion
  ,
  "org.http4s" %% "http4s-jdk-http-client" % "0.3.6"
  ,
  "org.http4s" %% "http4s-json4s-native" % http4sVersion,
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres-circe" % doobieVersion,
  "org.tpolecat" %% "doobie-hikari" % doobieVersion
)