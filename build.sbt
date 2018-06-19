import sbt.CrossVersion

name := "HearthstoneDeckBuilder"

version := "0.1"

scalaVersion := "2.12.6"
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"
libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.3"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.2.1",
  "mysql" % "mysql-connector-java" % "5.1.41",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
