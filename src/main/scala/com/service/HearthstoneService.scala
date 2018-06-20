package com.service

import com.model.{Card, DatabaseSchema, Deck}
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

  def getCardsFromDeck(className: String) : Future[Seq[Card]] = {
    val query = for {
      (c, d) <- cards join decks.filter(_.name === className) on (_.deckId === _.id)
    } yield c
    db.run(query.result)
  }

  def getDeckIdByClassName(className: String) : Future[Seq[Long]] = {
    val query = for {
      d <- decks if d.name === className
    } yield d.id
    db.run(query.result)
  }

  def addCardToDeck(deckId: Long, card: Card): Future[Card] = {
    card.deckId = Some(deckId)

    val cardsReturningId = cards returning cards.map(_.id) into ((_, newId) => card.copy(id = Some(newId)))
    val query = cardsReturningId += card
    db.run(query)
  }

  def deleteCardFromDeck(deckId: Long): Future[Int] = {
    val query = for {
      c <- cards.filter(_.deckId === deckId)
    } yield c
    db.run(query.delete)
  }
}
