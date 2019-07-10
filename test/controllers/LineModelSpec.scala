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

    "first number is unique and equal to zero" should {
      "NOT have a result unique for 0, 1, 1" in {
        val fixture = Line(numbers = (0, 1, 1))
        assertResult(Line.RESULT_UNIQUE_FIRST == fixture.result)(false)
      }
      "have a result equal two for 0, 1, 1" in {
        val fixture = Line(numbers = (0, 1, 1))
        assertResult(Line.RESULT_EQUAL_TWO)(fixture.result)
      }
      "have a result first unique for 0, 2, 2" in {
        val fixture = Line(numbers = (0, 2, 2))
        assertResult(Line.RESULT_UNIQUE_FIRST)(fixture.result)
      }
      "have a result invalid for 0, 3, 3" in {
        val fixture = Line(numbers = (0, 3, 3))
        assertResult(Line.RESULT_INVALID)(fixture.result)
      }
      "have a result first unique for 0, 1, 2" in {
        val fixture = Line(numbers = (0, 1, 2))
        assertResult(Line.RESULT_UNIQUE_FIRST)(fixture.result)
      }
      "have a result first unique for 0, 2, 1" in {
        val fixture = Line(numbers = (0, 2, 1))
        assertResult(Line.RESULT_UNIQUE_FIRST)(fixture.result)
      }
    }

    "first number is unique and equal to one" should {
      "have a result first unique for 1, 0, 0" in {
        val fixture = Line(numbers = (1, 0, 0))
        assertResult(Line.RESULT_UNIQUE_FIRST)(fixture.result)
      }
      "have a result first unique for 1, 2, 2" in {
        val fixture = Line(numbers = (1, 2, 2))
        assertResult(Line.RESULT_UNIQUE_FIRST)(fixture.result)
      }
      "have a result invalid for 1, 3, 3" in {
        val fixture = Line(numbers = (1, 3, 3))
        assertResult(Line.RESULT_INVALID)(fixture.result)
      }
      "have a result first unique for 1, 0, 2" in {
        val fixture = Line(numbers = (1, 0, 2))
        assertResult(Line.RESULT_UNIQUE_FIRST)(fixture.result)
      }
      "have a result first unique for 1, 2, 0" in {
        val fixture = Line(numbers = (1, 2, 0))
        assertResult(Line.RESULT_UNIQUE_FIRST)(fixture.result)
      }
    }
  }
}
