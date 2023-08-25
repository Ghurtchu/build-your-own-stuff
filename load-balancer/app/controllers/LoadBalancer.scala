package controllers

import com.typesafe.config.Config
import domain.Servers
import play.api.{ConfigLoader, Configuration}

import javax.inject._
import play.api.mvc._
import play.api.libs.ws._

import collection.JavaConverters._

import scala.concurrent.ExecutionContext

@Singleton
final case class LoadBalancer @Inject()(
    ws: WSClient,
    controllerComponents: ControllerComponents,
    config: Configuration
    ) extends BaseController {

  implicit val ec: ExecutionContext = controllerComponents.executionContext

  // thread unsafe

  val servers = Servers.from {
    config.getOptional[String]("servers")
      .getOrElse("http://localhost:9001/,http://localhost:9002/")
      .split(",").toIndexedSeq: _*
  }

  def run: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val url = servers.next

    ws.url(url)
      .withBody(request.body.toString)
      .execute()
      .map(resp => Ok(resp.body))
      .recover { case _ => Ok(s"could not connect to $url") }
  }
}
