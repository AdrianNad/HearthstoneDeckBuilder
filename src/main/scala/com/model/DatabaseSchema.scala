package com.model

import slick.jdbc.MySQLProfile.api._
//import slick.jdbc.H2Profile.api._

trait DatabaseSchema {

  class Decks(tag: Tag) extends Table[Deck](tag, "Deck") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("className")

    def * = (id.?, name) <>(Deck.tupled, Deck.unapply)
  }

  val decks = TableQuery[Decks]

  class Cards(tag: Tag) extends Table[Card](tag, "Card") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name_")
    def cost = column[Int]("cost_")
    def rarity = column[String]("rarity_")
    def image = column[String]("image_")
    def count = column[Int]("count_")
    def attack = column[Int]("attack_")
    def health = column[Int]("health_")
    def deckId = column[Long]("deckId")
    def deck = foreignKey("fk_deck", deckId, decks)(_.id)

    def * = (id.?, name, cost, rarity, image, count, attack, health, deckId.?) <> (Card.tupled, Card.unapply)
  }

  val cards = TableQuery[Cards]

  val allSchemas = decks.schema ++ cards.schema
}
