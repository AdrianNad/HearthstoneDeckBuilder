package com.model

import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._
//import slick.jdbc.H2Profile.api._

trait InitialData {

  self: DatabaseSchema =>

  def db: Database

  def insertInitialData(): Future[Unit] = {
    val queries = DBIO.seq(
      decks.delete,
      decks += Deck(1, "Deck 1"),
      decks += Deck(2, "Deck 2")
    )
    db.run(queries)
  }
}
