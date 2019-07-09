package models

import play.api.libs.json.{Json, Writes}

/** A model representing a lottery ticket
  */
case class Ticket(id: String,
                  lines: Seq[Line],
                  amended: Boolean)

object Ticket {
  implicit val ticketWrites: Writes[Ticket] = Json.writes[Ticket]
}
