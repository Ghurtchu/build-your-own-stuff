package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.ws._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Main @Inject()(ws: WSClient, val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    println(s"Received request from $request")
    println(s"${request.method}")
    println(s"Host: ${request.host}")
    println(s"User-Agent: ${request.headers}")

    val url = "http://localhost:9001/all"
    val req: WSRequest = ws.url(url)
      .withBody(request.body.toString)

    val resp: Future[WSResponse] = req.get()

    resp.map { wsResponse =>
      Ok(wsResponse.body)
    }
  }

  def getNumbers: Action[AnyContent] = Action { _ =>
    Ok {
      (1 to 10)
        .map(_.toString)
        .reduce(_ concat "\n" concat _)
    }
  }
}
