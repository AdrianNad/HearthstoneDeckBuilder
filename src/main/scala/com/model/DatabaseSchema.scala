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

  class Cards(tag: Tag) extends Table[CardDb](tag, "Card") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def cost = column[Int]("cost")
    def rarity = column[String]("rarity")
    def image = column[String]("image")
    def quantity = column[Int]("quantity")
    def deckId = column[Long]("deckId")
    def deck = foreignKey("fk_deck", deckId, decks)(_.id)

    def * = (id, name, cost, rarity, image, quantity, deckId) <> (CardDb.tupled, CardDb.unapply)
  }

  val cards = TableQuery[Cards]

  val allSchemas = decks.schema ++ cards.schema
}
