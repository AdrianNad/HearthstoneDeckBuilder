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
