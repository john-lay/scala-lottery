package controllers

import javax.inject._
import play.api.libs.json.{JsError, JsSuccess}
import play.api.mvc._
import viewmodels.LineView

/** This controller creates an `Action` to handle HTTP requests to the
  * application's lottery tickets.
  */
@Singleton
class LotteryController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  private val log = play.api.Logger(getClass).logger

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
  def createTicket = Action { request: Request[AnyContent] =>
    val body: AnyContent = request.body
    extractLine(body).fold(BadRequest("Expecting application/json request body")) { line =>
      Ok(line.lines.toString).as("application/json")
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
}
