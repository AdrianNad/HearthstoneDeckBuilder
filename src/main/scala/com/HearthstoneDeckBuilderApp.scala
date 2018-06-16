package com

import java.io.IOException


import com.model.{DatabaseSchema, InitialData, Magic}
import slick.basic.DatabaseConfig
import slick.driver.MySQLDriver
import slick.jdbc.MySQLProfile

import scalafx.Includes._
import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.stage.WindowEvent
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafxml.core.{FXMLView, NoDependencyResolver}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import concurrent.ExecutionContext.Implicits.global

object HearthstoneDeckBuilderApp extends JFXApp with DatabaseSchema with InitialData with Magic {

  //val db = Database.forConfig("db")
  val dbConfig: DatabaseConfig[MySQLProfile] = DatabaseConfig.forConfig("db")
  val db = dbConfig.db

  private val future = createSchemaIfNotExists.flatMap(_ => insertInitialData())
  Await.ready(future, Duration.Inf)

  val resource = getClass.getResource("/fxml/MainWindow.fxml")
  if (resource == null) {
    throw new IOException("Cannot load resource: fxml/MainWindow.fxml")
  }

  val root = FXMLView(resource, NoDependencyResolver)

  stage = new PrimaryStage() {
    title = "Hearthstone deck builder"
    scene = new Scene(root)
    onCloseRequest = (we: WindowEvent) => Platform.exit()
  }

}
