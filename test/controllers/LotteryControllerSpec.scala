package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class LotteryControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "LotteryController GET all tickets" should {

    "return an empty array from a new instance of controller" in {
      val controller = new LotteryController(stubControllerComponents())
      val allTickets = controller.allTickets().apply(FakeRequest(GET, "/ticket"))

      status(allTickets) mustBe OK
      contentType(allTickets) mustBe Some("application/json")
      contentAsString(allTickets) must include ("[]")
    }

    "return an empty array from the application" in {
      val controller = inject[LotteryController]
      val allTickets = controller.allTickets().apply(FakeRequest(GET, "/ticket"))

      status(allTickets) mustBe OK
      contentType(allTickets) mustBe Some("application/json")
      contentAsString(allTickets) must include ("[]")
    }

    "return an empty array from the router" in {
      val request = FakeRequest(GET, "/ticket")
      val allTickets = route(app, request).get

      status(allTickets) mustBe OK
      contentType(allTickets) mustBe Some("application/json")
      contentAsString(allTickets) must include ("[]")
    }
  }
}
