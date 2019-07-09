package controllers

import java.util.UUID

import javax.inject._
import models.{Line, Ticket}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc._
import viewmodels.{LineView, TicketView}

import scala.collection.mutable
import scala.util.Random

/** This controller creates an `Action` to handle HTTP requests to the
  * application's lottery tickets.
  */
@Singleton
class LotteryController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  private val log = play.api.Logger(getClass).logger
  private val tickets = mutable.HashMap[String, Ticket]()
  private val r = Random

  /** Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  /** Generate a new ticket and add it to the collection of tickets
    * Example (with 3 lines):
    * curl -H "Content-Type: application/json" -X POST -d '{ "lines": 3 }' http://localhost:9000/ticket
    *
    * @return A http 200 response with a body containing the new ticket
    */
  def createTicket = Action { implicit request: Request[AnyContent] =>
    val body: AnyContent = request.body
    extractLine(body).fold(BadRequest("Expecting application/json request body")) { lineView: LineView =>
      val lines = List.fill(lineView.lines)(Line((r.nextInt(3), r.nextInt(3), r.nextInt(3))))
      val ticket = Ticket(UUID.randomUUID.toString, lines, amended = false)
      tickets.put(ticket.id, ticket)

      Ok(Json.toJson(ticket))
    }
  }

  /** Lists all lottery tickets
    * Example: curl -X GET http://localhost:9000/ticket
    *
    * @return A http 200 response with a body containing all tickets as json
    */
  def allTickets = Action { _ =>
    Ok(Json.toJson(tickets.values))
  }

  /** Searches the collection of tickets using the ticket ID as the key
    * Example: curl -X GET http://localhost:9000/ticket/3d8df83f-3b08-479b-b4ac-2aa542de0b58
    *
    * @param id the ticket to search for
    * @return if a corresponding ticket is found a http 200 response with a body containing the ticket,
    *         otherwise a http 400 (not found) is returned
    */
  def findTicket(id: String) = Action { _ =>
    tickets.get(id).fold(NotFound("Could not find specified ticket")) { ticket =>
      Ok(Json.toJson(ticket))
    }
  }

  /** Adds the specified number of lines to the lottery ticket
    * Example (adding 3 lines):
    * curl -H "Content-Type: application/json" -X PUT -d '{ "id": "3d8df83f-3b08-479b-b4ac-2aa542de0b58", "lines": 3 }' http://localhost:9000/ticket
    *
    * @return if a corresponding ticket is found a http 200 response with a body containing the modified ticket,
    *         otherwise a http 400 (not found) is returned
    */
  def addLines = Action { implicit request: Request[AnyContent] =>
    val body: AnyContent = request.body
    extractTicket(body).fold(BadRequest("Expecting application/json request body")) { ticketView: TicketView =>
      tickets.get(ticketView.id).fold(NotFound("Could not find specified ticket")) { ticket =>
        if (ticket.amended) Forbidden("Not allowed to amend ticket")
        else {
          val lines = List.fill(ticketView.lines)(Line((r.nextInt(3), r.nextInt(3), r.nextInt(3))))
          val amendedTicket = Ticket(ticket.id, ticket.lines ++ lines, ticket.amended)
          tickets.remove(ticket.id)
          tickets.put(amendedTicket.id, amendedTicket)

          Ok(Json.toJson(amendedTicket))
        }
      }
    }
  }

  /** Checks the status of a ticket for winning lines and marks it as checked
    * Example: curl -X GET http://localhost:9000/status/3d8df83f-3b08-479b-b4ac-2aa542de0b58
    *
    * @param id the ticket to search for
    * @return if a corresponding ticket is found a http 200 response with a body containing the modified ticket,
    *         otherwise a http 400 (not found) is returned
    */
  def status(id: String) = Action { _ =>
    tickets.get(id).fold(NotFound("Could not find specified ticket")) { ticket =>
      if (ticket.amended) Ok(Json.toJson(ticket))
      else {
        val amendedTicket = ticket.copy(amended = true, lines = ticket.lines.sortBy(_.result).reverse)
        tickets.remove(ticket.id)
        tickets.put(amendedTicket.id, amendedTicket)

        Ok(Json.toJson(amendedTicket))
      }
    }
  }

  private def extractLine(body: AnyContent): Option[LineView] = {
    body.asJson match {
      case None => None
      case Some(json) => json.validate[LineView] match {
        case success: JsSuccess[LineView] =>
          Some(success.get)
        case error: JsError =>
          log.error("failed to deserialise ({}) as body of create ticket request with exception {}", json: Any, error)
          None
      }
    }
  }

  private def extractTicket(body: AnyContent): Option[TicketView] = {
    body.asJson match {
      case None => None
      case Some(json) => json.validate[TicketView] match {
        case success: JsSuccess[TicketView] =>
          Some(success.get)
        case error: JsError =>
          log.error("failed to deserialise ({}) as body of amend ticket request with exception {}", json: Any, error)
          None
      }
    }
  }
}
