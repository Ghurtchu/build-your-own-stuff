package controllers

import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class Auxiliary @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def getText: Action[AnyContent] = Action { _ => Ok("Auxiliary") }

}
