package com

import java.io.IOException

import com.model._
import com.service.{FullyPreparedMethodsService, HearthstoneService}

import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.stage.WindowEvent
import scalafxml.core.{FXMLView, NoDependencyResolver}
import slick.basic.DatabaseConfig
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future
import scala.util.{Success, Try}
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

  val service: FullyPreparedMethodsService = new FullyPreparedMethodsService()

  /*val seq: Seq[Card] = Seq(Card(name_ = "Karta 1", cost_ = 5, count_ = 5, image_ = "url", rarity_ = "Legend"),
                          Card(name_ = "Karta 2", cost_ = 7, count_ = 5, image_ = "url", rarity_ = "Legend"))
  service.addCardsToDeck(seq, "Shaman")*/
  //val test: Seq[Card] = service.getCardsFromDeck("Paladin")

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
