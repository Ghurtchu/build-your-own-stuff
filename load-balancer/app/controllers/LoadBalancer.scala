package controllers

import domain.Servers

import javax.inject._
import play.api.mvc._
import play.api.libs.ws._

import scala.concurrent.ExecutionContext

@Singleton
final case class LoadBalancer @Inject()(ws: WSClient, controllerComponents: ControllerComponents) extends BaseController {

  implicit val ec: ExecutionContext = controllerComponents.executionContext

  // thread unsafe
  val servers = Servers.from(
    "http://localhost:9001/all",
      "http://localhost:9002/all",
      "http://localhost:9003/all"
  )
  def run: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val url = servers.next

    ws.url(url)
      .withBody(request.body.toString)
      .execute()
      .map(resp => Ok(resp.body))
      .recover { case _ => Ok(s"could not connect to $url") }
  }
}
