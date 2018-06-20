package com.controller

import com.model.Card
import com.service.FullyPreparedMethodsService
import scalafx.Includes._
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.image.ImageView
import scalafx.scene.input.MouseEvent
import scalafxml.core.macros.sfxml
import scalaj.http.Http
import spray.json.{JsValue, _}

@sfxml
class DeckEditorController(protected var deckTableView: TableView[Card], protected val cardImageView: ImageView
                           , protected var cardSearchTableView: TableView[Card], protected var cardCounterLabel: Label) extends DeckEditorControllerInterface {
  var deckCardCounter: Int = 0
  var playerClass: String = _
  val database: FullyPreparedMethodsService = new FullyPreparedMethodsService
  def prepareTables(): Unit = {
    val tableColumnName: TableColumn[Card, String] = new TableColumn[Card, String]("name")
    tableColumnName.cellValueFactory = {
      _.value.name
    }
    val tableColumnCost: TableColumn[Card, String] = new TableColumn[Card, String]("cost")
    tableColumnCost.cellValueFactory = {
      _.value.cost
    }
    val tableColumnCount: TableColumn[Card, String] = new TableColumn[Card, String]("count")
    tableColumnCount.cellValueFactory = {
      _.value.count
    }

    val tableColumnNameCard: TableColumn[Card, String] = new TableColumn[Card, String]("name")
    tableColumnNameCard.cellValueFactory = {
      _.value.name
    }
    val tableColumnCostCard: TableColumn[Card, String] = new TableColumn[Card, String]("cost")
    tableColumnCostCard.cellValueFactory = {
      _.value.cost
    }
    deckTableView.getColumns.addAll(tableColumnName, tableColumnCost, tableColumnCount)
    cardSearchTableView.getColumns.addAll(tableColumnNameCard, tableColumnCostCard)

    deckTableView.rowFactory = (tv) => {
      def call(tv: TableView[Card]): TableRow[Card] = {
        var row = new TableRow[Card]()
        row.onMouseClicked = (event: MouseEvent) => {
          if (row.getItem != null) {
            if (event.clickCount == 2) {
              row.getItem.count_ = row.getItem.count_ - 1
              row.getItem.count = new StringProperty(this, "cost", row.getItem.count_.toString)
              deckTableView.refresh()
              if (row.getItem.count_ == 0) {
                deckTableView.getItems.remove(deckTableView.getSelectionModel.getSelectedItem)
              }
              deckCardCounter -= 1
              cardCounterLabel.setText(deckCardCounter.toString)
              if (deckTableView.getItems.size() == 0) {
                //                cardImageView.setImage(null)
              }
            } else if (event.clickCount == 1) {
              //              cardImageView.setImage(new Image(row.getItem.image_))
            }
          }
        }
        deckTableView.refresh()
        row
      }

      call(tv)
    }

    cardSearchTableView.rowFactory = (tv) => {
      def call(tv: TableView[Card]): TableRow[Card] = {
        var row = new TableRow[Card]()
        row.onMouseClicked = (event: MouseEvent) => {
          if (row.getItem != null) {
            if (event.clickCount == 2 && deckCardCounter < 30) {
              var clickedCard: Card = row.getItem
              var isInDeckAlready: Boolean = false
              deckTableView.getItems.foreach(card => {
                if (card.name_ == clickedCard.name_) {
                  isInDeckAlready = true
                  if (card.rarity_ != "Legendary" && card.count_ < 2) {
                    card.count_ += 1
                    card.count = new StringProperty(this, "cost", card.count_.toString)
                    deckTableView.refresh()
                    deckCardCounter += 1
                    cardCounterLabel.setText(deckCardCounter.toString)
                  } else {
                    new Alert(AlertType.Warning, "You've reached max number of card: " + card.name_).showAndWait()
                  }
                }
              })
              if (!isInDeckAlready) {
                clickedCard.count_ = 1
                clickedCard.count = new StringProperty(this, "cost", clickedCard.count_.toString)
                deckTableView.getItems.add(clickedCard)
                deckTableView.refresh()
                deckCardCounter += 1
                cardCounterLabel.setText(deckCardCounter.toString)
              }
            } else if (event.clickCount == 1) {
              //              cardImageView.setImage(new Image(row.getItem.image_))
            } else if (deckCardCounter == 30) {
              new Alert(AlertType.Warning, "You've reached max number of cards").showAndWait()
            }
          }
        }
        row
      }

      call(tv)
    }
  }

  def setPlayerClass(playerClass: String): Unit = {
    prepareTables()
    this.playerClass = playerClass
    fillCards(playerClass)
    fillTable(playerClass)
  }


  def fillCards(playerClass: String) {
    val cardsAll = ObservableBuffer[Card]()
    var response = Http("https://irythia-hs.p.mashape.com/cards")
      .header("X-Mashape-Key", "UdPl9JESCImshHjThNPHpjkGtMqkp1azcuGjsnMjcx078Gd7Z0").asString
    val json: JsValue = response.body.parseJson
    val jsonArray: JsArray = json.asInstanceOf[JsArray]
    jsonArray.elements.foreach(x => {
      val fields = x.asInstanceOf[JsObject].fields
      val cardClass = fields.getOrElse("card_class", "").toString
      if (cardClass == playerClass || cardClass == "null") {
        val name = fields.getOrElse("name", "name").toString.replace("\"", "")
        val rarity = fields.getOrElse("rarity", "rarity").toString.replace("\"", "")
        val cost = fields.getOrElse("cost", "0").toString.toInt
        val img = fields.getOrElse("img", "").toString.replace("\"", "")
        if (img != "") {
          var card: Card = new Card(name_ = name, cost_ = cost, rarity_ = rarity, image_ = img, count_ = 0)
          cardsAll add card
        } else {
          var card: Card = new Card(name_ = name, cost_ = cost, rarity_ = rarity, image_ = "https://firstfiveeight.com.au/wp-content/uploads/2018/05/image-default.png", count_ = 0)
          cardsAll add new Card(name_ = name, cost_ = cost, rarity_ = rarity, image_ = "https://firstfiveeight.com.au/wp-content/uploads/2018/05/image-default.png", count_ = 0)
        }
      }
    })
    cardSearchTableView.items = cardsAll
  }

  def fillTable(playerClass: String): Unit = {
    val deckCards = ObservableBuffer[Card]()
    var cards = database.getCardsFromDeck(playerClass)
    cards.foreach(c => {
      deckCards add c
    })
    deckTableView.items = deckCards
    deckCardCounter = deckTableView.getItems.size()
    cardCounterLabel.setText(deckCardCounter.toString)
  }

  def savePressed(): Unit = {
    database.deleteCardsFromDeck(playerClass)
    database.addCardsToDeck(deckTableView.getItems, playerClass)
  }

  def clearPressed(): Unit = {
    deckTableView.getItems.clear()
    deckCardCounter = 0
  }
}