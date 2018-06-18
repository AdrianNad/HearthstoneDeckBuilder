package com.model

import slick.jdbc.MySQLProfile.api._
//import slick.jdbc.H2Profile.api._

trait DatabaseSchema {

  class Decks(tag: Tag) extends Table[Deck](tag, "Deck") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")

    def * = (id, name) <>(Deck.tupled, Deck.unapply)
  }

  val decks = TableQuery[Decks]

  val allSchemas = decks.schema

}
