package controllers

import akka.stream.ConnectionException
import domain.Servers
import play.api.PlayException

import javax.inject._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.ws.ahc.AhcWSResponse

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.impl.Promise
import scala.util.{Failure, Success, Try}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class LoadBalancer @Inject()(ws: WSClient, val controllerComponents: ControllerComponents) extends BaseController {

  implicit val ec: ExecutionContext = controllerComponents.executionContext

  // thread unsafe
  val servers = Servers.from(
    "http://localhost:9001/all",
      "http://localhost:9002/all",
      "http://localhost:9003/all"
  )

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val url = servers.next

    ws.url(url)
      .withBody(request.body.toString)
      .execute()
      .map(resp => Ok(resp.body))
      .recover { case _ => Ok(s"could not connect to $url") }
  }
}
