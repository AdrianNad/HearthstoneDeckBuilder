package com.controller

import javafx.scene.Scene
import javafx.scene.image.ImageView
import javafx.stage.Stage
import scalafx.Includes._
import scalafx.scene.Parent
import scalafx.scene.image.Image
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{AnchorPane, GridPane}
import scalafxml.core.{FXMLLoader, FXMLView, NoDependencyResolver}
import scalafxml.core.macros.sfxml

@sfxml
class ManageDeckController(protected val gridPane: GridPane) {
  val warriorImageView: ImageView = gridPane.getChildren.get(0).asInstanceOf[ImageView]
  warriorImageView.setImage(new Image(getClass.getResource("/images/warrior.jpg").toString))
  warriorImageView.onMouseClicked() = (event: MouseEvent) => {
    openDeckEditor("Warrior")
  }
  val shamanImageView: ImageView = gridPane.getChildren.get(1).asInstanceOf[ImageView]
  shamanImageView.setImage(new Image(getClass.getResource("/images/shaman.jpg").toString))
  shamanImageView.onMouseClicked() = (event: MouseEvent) => {
    openDeckEditor("Shaman")
  }
  val druidImageView: ImageView = gridPane.getChildren.get(2).asInstanceOf[ImageView]
  druidImageView.setImage(new Image(getClass.getResource("/images/druid.jpg").toString))
  druidImageView.onMouseClicked() = (event: MouseEvent) => {
    openDeckEditor("Druid")
  }
  val paladinImageView: ImageView = gridPane.getChildren.get(3).asInstanceOf[ImageView]
  paladinImageView.setImage(new Image(getClass.getResource("/images/paladin.jpg").toString))
  paladinImageView.onMouseClicked() = (event: MouseEvent) => {
    openDeckEditor("Paladin")
  }
  val mageImageView: ImageView = gridPane.getChildren.get(4).asInstanceOf[ImageView]
  mageImageView.setImage(new Image(getClass.getResource("/images/mage.jpg").toString))
  mageImageView.onMouseClicked() = (event: MouseEvent) => {
    openDeckEditor("Mage")
  }
  val priestImageView: ImageView = gridPane.getChildren.get(5).asInstanceOf[ImageView]
  priestImageView.setImage(new Image(getClass.getResource("/images/priest.jpg").toString))
  priestImageView.onMouseClicked() = (event: MouseEvent) => {
    openDeckEditor("Priest")
  }
  val hunterImageView: ImageView = gridPane.getChildren.get(6).asInstanceOf[ImageView]
  hunterImageView.setImage(new Image(getClass.getResource("/images/hunter.jpg").toString))
  hunterImageView.onMouseClicked() = (event: MouseEvent) => {
    openDeckEditor("Hunter")
  }
  val rogueImageView: ImageView = gridPane.getChildren.get(7).asInstanceOf[ImageView]
  rogueImageView.setImage(new Image(getClass.getResource("/images/rogue.jpg").toString))
  rogueImageView.onMouseClicked() = (event: MouseEvent) => {
    openDeckEditor("Rogue")
  }
  val warlockImageView: ImageView = gridPane.getChildren.get(8).asInstanceOf[ImageView]
  warlockImageView.setImage(new Image(getClass.getResource("/images/warlock.jpg").toString))
  warlockImageView.onMouseClicked() = (event: MouseEvent) => {
    openDeckEditor("Warlock")
  }

  def openDeckEditor(title: String): Unit = {
    val resource = getClass.getResource("/fxml/DeckEditor.fxml")
    println(resource)
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val root = loader.getRoot[javafx.scene.layout.AnchorPane]
    val controller = loader.getController[DeckEditorControllerInterface]
    controller.setPlayerClass(title)
    val stage = new Stage
    val scene = new Scene(root)
    stage.setTitle(title)
    stage.setScene(scene)
    stage.show()
  }
}
