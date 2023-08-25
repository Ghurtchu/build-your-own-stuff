package com.ghurtchu.loadbalancer

import cats.effect.{IO, Ref}
import org.http4s.{HttpRoutes, Uri}
import org.http4s.dsl.Http4sDsl
import org.http4s.client.Client

object LoadbalancerRoutes {

  def requestRoutes(backends: Ref[IO, Backends], client: Client[IO]): HttpRoutes[IO] = {
    val dsl = new Http4sDsl[IO] {}
    import dsl._
    HttpRoutes.of[IO] {
      case request @ GET -> Root =>
        for {
          b <- backends.getAndUpdate(_.next)
          a <- client.expect[String] {
            request.withUri(Uri.unsafeFromString(b.current))
          }
            .recover(_ => "server is dead")
          resp <- Ok(a)
        } yield resp
    }
  }

  def helloRoutes: HttpRoutes[IO] = {
    val dsl = new Http4sDsl[IO] {}
    import dsl._
    HttpRoutes.of[IO] {
      case GET -> Root / "hello" =>
        for {
          resp <- Ok("hello")
        } yield resp
    }
  }

}