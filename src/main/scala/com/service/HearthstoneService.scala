package com.service

import com.model.{DatabaseSchema, Deck}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class HearthstoneService(db: Database) extends DatabaseSchema {

  def addDeck(deck: Deck): Future[Deck] = {
    val decksReturningId = decks returning decks.map(_.id) into ((_, newId) => deck.copy(id = Some(newId)))
    val query = decksReturningId += deck
    db.run(query)
  }

  def findAllDecks: Future[Seq[Deck]] = db.run(decks.result)
}
