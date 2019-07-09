package viewmodels

import play.api.libs.json.{Json, Reads}

/** A view model representing the number of lines on a lottery ticket
  */
case class LineView(lines: Int)

object LineView {
  implicit val lineViewReads: Reads[LineView] = Json.reads[LineView]
}
