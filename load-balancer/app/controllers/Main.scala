package controllers

import domain.Servers

import javax.inject._
import play.api.mvc._
import play.api.libs.ws._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Main @Inject()(ws: WSClient, val controllerComponents: ControllerComponents) extends BaseController {

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
    println(s"sending request to $url")
    val req: WSRequest = ws.url(url)
      .withBody(request.body.toString)

    req.execute.map { wsResponse => Ok(wsResponse.body) } (controllerComponents.executionContext)
  }

  def getNumbers: Action[AnyContent] = Action { _ =>
    Ok {
      (1 to 10)
        .map(_.toString)
        .reduce(_ concat "\n" concat _)
    }
  }
}
