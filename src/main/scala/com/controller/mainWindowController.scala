package com.controller

import scalafx.scene.control.{Button, Label}
import scalafxml.core.macros.sfxml

@sfxml
class mainWindowController(protected val button: Button
                           , protected val label: Label) {


  def click(): Unit = {
    label.setText("Hello world")
  }
}
