package viewmodels

import play.api.libs.json.{Json, Reads}

/** A view model representing a lottery ticket to update
  */
case class TicketView(id: String, lines: Int)

object TicketView {
  implicit val ticketViewReads: Reads[TicketView] = Json.reads[TicketView]
}
