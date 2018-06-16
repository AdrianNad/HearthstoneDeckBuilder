package com.controller
import javafx.scene.Scene
import javafx.scene.image.ImageView
import javafx.stage.Stage
import scalafx.Includes._
import scalafx.scene.image.Image
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.GridPane
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafxml.core.macros.sfxml

@sfxml
class ManageDeckController(protected val gridPane: GridPane) {
  val warriorImageView: ImageView = gridPane.getChildren.get(0).asInstanceOf[ImageView]
  warriorImageView.setImage(new Image(getClass.getResource("/images/warrior.jpg").toString))
  warriorImageView.onMouseClicked() = (event: MouseEvent) => {
   openDeckEditor("Warrior")
  }
  def openDeckEditor(title: String ): Unit = {
    val resource = getClass.getResource("/fxml/DeckEditor.fxml")
    println(resource)
    val root = FXMLView(resource, NoDependencyResolver)
    val scene = new Scene(root)
    val stage = new Stage
    stage.setTitle(title)
    stage.setScene(scene)
    stage.show()
  }
}
