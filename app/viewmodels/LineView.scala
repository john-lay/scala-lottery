package viewmodels

import play.api.libs.json.{Json, Reads}

case class LineView(lines: Int)

object LineView {
  implicit val lineViewReads: Reads[LineView] = Json.reads[LineView]
}
