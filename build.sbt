import sbt.CrossVersion

name := "HearthstoneDeckBuilder"

version := "0.1"

scalaVersion := "2.12.6"
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"
libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.3"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
