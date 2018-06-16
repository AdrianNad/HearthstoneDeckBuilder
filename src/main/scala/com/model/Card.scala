package com.model

import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.scene.image.Image

class Card(val name_ : String, val cost_ : Int, val rarity_ : String, val image_ : Image, var count_ : Int) {
  var name = new StringProperty(this, "name", name_)
  var cost = new StringProperty(this, "cost", cost_.toString)
  var rarity = new ObjectProperty(this, "rarity",
    rarity_)
  var image = new ObjectProperty(this, "image",
    image_)
  var count = new StringProperty(this, "count", count_.toString)
}
