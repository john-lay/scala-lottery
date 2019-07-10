package controllers

import models.Line
import org.scalatest.WordSpec

class LineModelSpec extends WordSpec {

  "A Line" when {
    "the sum of all numbers equals two" should {
      "have a result equal to two for 1, 1, 0" in {
        val fixture = Line(numbers = (1, 1, 0))
        assertResult(Line.RESULT_EQUAL_TWO)(fixture.result)
      }
      "have a result equal to two for 1, 0, 1" in {
        val fixture = Line(numbers = (1, 0, 1))
        assertResult(Line.RESULT_EQUAL_TWO)(fixture.result)
      }
      "have a result equal to two for 0, 1, 1" in {
        val fixture = Line(numbers = (0, 1, 1))
        assertResult(Line.RESULT_EQUAL_TWO)(fixture.result)
      }
      "have a result equal to two for 2, 0, 0" in {
        val fixture = Line(numbers = (2, 0, 0))
        assertResult(Line.RESULT_EQUAL_TWO)(fixture.result)
      }
      "have a result equal to two for 0, 2, 0" in {
        val fixture = Line(numbers = (0, 2, 0))
        assertResult(Line.RESULT_EQUAL_TWO)(fixture.result)
      }
      "have a result equal to two for 0, 0, 2" in {
        val fixture = Line(numbers = (0, 0, 2))
        assertResult(Line.RESULT_EQUAL_TWO)(fixture.result)
      }
      "have a result equal to invalid for -1, 0, 3" in {
        val fixture = Line(numbers = (-1, 0, 3))
        assertResult(Line.RESULT_INVALID)(fixture.result)
      }
    }

    "all numbers are the same" should {
      "have a result all same for 0, 0, 0" in {
        val fixture = Line(numbers = (0, 0, 0))
        assertResult(Line.RESULT_ALL_SAME)(fixture.result)
      }
      "have a result all same for 1, 1, 1" in {
        val fixture = Line(numbers = (1, 1, 1))
        assertResult(Line.RESULT_ALL_SAME)(fixture.result)
      }
      "have a result all same for 2, 2, 2" in {
        val fixture = Line(numbers = (2, 2, 2))
        assertResult(Line.RESULT_ALL_SAME)(fixture.result)
      }
      "have a result invalid for 3, 3, 3" in {
        val fixture = Line(numbers = (3, 3, 3))
        assertResult(Line.RESULT_INVALID)(fixture.result)
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

    "all numbers are unique" should {
      "have a result unique first" in {
        val fixture = Line(numbers = (0, 1, 2))
        assertResult(Line.RESULT_UNIQUE_FIRST)(fixture.result)
      }
    }

    "first number is unique, but the line total equals two" should {
      "have a result equal two" in {
        val fixture = Line(numbers = (0, 1, 1))
        assertResult(Line.RESULT_EQUAL_TWO)(fixture.result)
      }
    }
  }
}
