package com.service

import com.model.Card

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future
import scala.util.Try

class FullyPreparedMethodsService {

  val db = Database.forConfig("mysql")
  val hearthstoneService = new HearthstoneService(db)

  def getCardsFromDeck(className: String): Seq[Card] = {
    val returnedCards: Future[Seq[Card]] = hearthstoneService.getCardsFromDeck("Paladin")
    Await.ready(returnedCards, Duration.Inf)
    val optionCards: Option[Try[Seq[Card]]] = returnedCards.value
    val result = optionCards.get
    return result.get
  }

  private def getDeckIdByClassName(className: String): Long = {
    val returnedDecks: Future[Seq[Long]] = hearthstoneService.getDeckIdByClassName(className)
    Await.ready(returnedDecks, Duration.Inf)

    val options: Option[Try[Seq[Long]]] = returnedDecks.value
    val result = options.get
    return result.get.head
  }

  def addCardsToDeck(cards: Seq[Card], className: String): Unit = {
    for (card <- cards) {
      Await.ready(hearthstoneService.addCardToDeck(getDeckIdByClassName(className), card), Duration.Inf)
    }
  }

  def deleteCardsFromDeck(className: String): Unit = {
    val cardsToDelete: Seq[Card] = getCardsFromDeck(className)

    for (card <- cardsToDelete) {
      Await.ready(hearthstoneService.deleteCardFromDeck(card.deckId.get), Duration.Inf)
    }
  }

}
