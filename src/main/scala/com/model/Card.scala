package com.model

import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.scene.image.Image

case class Card(id: Option[Long] = None, val name_ : String, val cost_ : Int, val rarity_ : String, val image_ : String, var count_ : Int, var attack_ : Int, var health_ : Int, var deckId: Option[Long] = None) {
  var name = new StringProperty(this, "name", name_)
  var cost = new StringProperty(this, "cost", cost_.toString)
  var rarity = new ObjectProperty(this, "rarity",
    rarity_)
  var image = new ObjectProperty(this, "image",
    image_)
  var count = new StringProperty(this, "count", count_.toString)
  var attack = new StringProperty(this, "attack", attack_.toString)
  var health = new StringProperty(this, "health", health_.toString)
}
