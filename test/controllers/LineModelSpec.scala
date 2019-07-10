package controllers

import models.Line
import org.scalatest.WordSpec

class LineModelSpec extends WordSpec {

  "A Line" when {
    "first number equals 1 and second number equals 1 and third number equals 0" should {
      "have a result equal to two" in {
        val fixture = Line(numbers = (1, 1, 0))
        assertResult(Line.RESULT_EQUAL_TWO)(fixture.result)
      }
    }
  }
}
