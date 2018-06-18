package com

import java.io.IOException

import com.model.{DatabaseSchema, InitialData, Magic}
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.stage.WindowEvent
import scalafxml.core.{FXMLView, NoDependencyResolver}
import slick.basic.DatabaseConfig
import slick.jdbc.MySQLProfile.api._
//import slick.jdbc.H2Profile.api._
import slick.driver.JdbcProfile

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object HearthstoneDeckBuilderApp extends JFXApp with DatabaseSchema with InitialData with Magic {

  val db = Database.forConfig("mysql")
  private val future = createSchemaIfNotExists.flatMap(_ => insertInitialData())
  Await.ready(future, Duration.Inf)
  db.close()

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
