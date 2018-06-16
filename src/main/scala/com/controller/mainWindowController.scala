package com.controller

import javafx.scene.Scene
import javafx.stage.Stage
import scalafx.application.JFXApp
import scalafx.scene.control.{Button, Label}
import scalafxml.core.macros.sfxml
import scalafxml.core.{FXMLView, NoDependencyResolver}

@sfxml
class mainWindowController(protected val button: Button
                           , protected val label: Label) extends JFXApp{

  def click(): Unit = {
    label.setText("Hello world")
  }

  def exitPressed(): Unit = {
  }

  def manageDeckPressed(): Unit = {
    val resource = getClass.getResource("/fxml/ManageDeckWindow.fxml")
    println(resource)
    val root = FXMLView(resource, NoDependencyResolver)
    val scene = new Scene(root)
    val stage = new Stage
    stage.setTitle("Deck manager")
    stage.setScene(scene)
    stage.show()
  }

}
