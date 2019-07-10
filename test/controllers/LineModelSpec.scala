package controllers

import models.Line
import org.scalatest.WordSpec

class LineModelSpec extends WordSpec {

  "A Line" when {
    "the sum of all numbers equals two" should {
      "have a result equal to two" in {
        val fixture = Line(numbers = (1, 1, 0))
        assertResult(Line.RESULT_EQUAL_TWO)(fixture.result)
      }
    }

    "all numbers are zero" should {
      "have a result all same" in {
        val fixture = Line(numbers = (0, 0, 0))
        assertResult(Line.RESULT_ALL_SAME)(fixture.result)
      }
    }

    "all numbers are one" should {
      "have a result all same" in {
        val fixture = Line(numbers = (1, 1, 1))
        assertResult(Line.RESULT_ALL_SAME)(fixture.result)
      }
    }

    "all numbers are two" should {
      "have a result all same" in {
        val fixture = Line(numbers = (2, 2, 2))
        assertResult(Line.RESULT_ALL_SAME)(fixture.result)
      }
    }
  }
}
