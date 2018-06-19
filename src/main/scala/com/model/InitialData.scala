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
      decks += Deck(1, "Deck 1"),
      decks += Deck(2, "Deck 2"),

      cards += CardDb(1, "Card 1", 5, "Legend", "image URL", 5, 1),
      cards += CardDb(2, "Card 2", 10, "Legend", "image URL", 5, 1)

    )
    db.run(queries)
  }
}
