package com.ghurtchu.loadbalancer

import cats.effect.{IO, Ref}
import cats.implicits.toSemigroupKOps
import com.comcast.ip4s._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger

object LoadbalancerServer {

  def run(backends: Ref[IO, Backends]): IO[Unit] = {
    for {
      client <- EmberClientBuilder.default[IO].build

      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract segments not checked
      // in the underlying routes.
      httpApp =
          (LoadbalancerRoutes.requestRoutes(backends, client) <+>
            LoadbalancerRoutes.helloRoute)
            .orNotFound

      // With Middlewares in place
      finalHttpApp = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

      _ <- 
        EmberServerBuilder.default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"9003")
          .withHttpApp(finalHttpApp)
          .build
    } yield ()
  }.useForever
}
