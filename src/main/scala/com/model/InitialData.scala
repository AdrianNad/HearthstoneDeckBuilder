package com.model

import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._
//import slick.jdbc.H2Profile.api._

trait InitialData {

  self: DatabaseSchema =>

  def db: Database

  def insertInitialData(): Future[Unit] = {
    val queries = DBIO.seq(
      cards.delete, decks.delete,
      decks += Deck(className =  "Warrior"),
      decks += Deck(className = "Mage"),
      decks += Deck(className = "Warlock"),
      decks += Deck(className = "Shaman"),
      decks += Deck(className = "Priest"),
      decks += Deck(className = "Druid"),
      decks += Deck(className = "Hunter"),
      decks += Deck(className = "Rogue"),
      decks += Deck(className = "Paladin")

      /*cards += Card(name_ = "Karta 1", cost_ = 5, count_ = 5, image_ = "url", rarity_ = "Legend", deckId = Some(9)),
      cards += Card(name_ = "Karta 2", cost_ = 7, count_ = 5, image_ = "url", rarity_ = "Legend", deckId = Some(9))*/

    )
    db.run(queries)
  }
}
