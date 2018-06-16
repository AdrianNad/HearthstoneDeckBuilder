package com.controller

import com.model.Card
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{TableColumn, TableRow, TableView}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.scene.Node

@sfxml
class DeckEditorController(protected var deckTableView: TableView[Card], protected val cardImageView: ImageView) {

  val cards = ObservableBuffer[Card](
    new Card("Alexstrasza ", 9, "legendary"
      , new Image("https://d1u5p3l4wpay3k.cloudfront.net/hearthstone_gamepedia/thumb/b/b4/Alexstrasza%28303%29.png/200px-Alexstrasza%28303%29.png?version=c2e14cd0f7beca42513d8100860acb27"), 1),
    new Card("Deathwing ", 10, "legendary"
      , new Image("https://d1u5p3l4wpay3k.cloudfront.net/hearthstone_gamepedia/thumb/d/df/Deathwing%28474%29.png/200px-Deathwing%28474%29.png?version=34418c4b5bfaf2ae24089756e01038a7"), 2)
  )
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
  deckTableView.getColumns.addAll(tableColumnName, tableColumnCost, tableColumnCount)
  deckTableView.items = cards

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
            if (deckTableView.getItems.size() == 0) {
              cardImageView.setImage(null)
            }
          } else if (event.clickCount == 1) {
            cardImageView.setImage(row.getItem.image_)
          }
        }
      }
      row
    }

    call(tv)
  }
}