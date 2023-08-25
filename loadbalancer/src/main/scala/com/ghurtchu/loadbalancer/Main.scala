package com.ghurtchu.loadbalancer

import cats.effect.{IO, IOApp, Ref}

object Main extends IOApp.Simple {

  def backendsRef: IO[Ref[IO, Backends]] =
    Ref.of(Backends("http://localhost:9002/hello", "http://localhost:9003/hello"))

  val run =
    for {
      backends <- backendsRef
      _ <- LoadbalancerServer.run(backends)
    } yield ()
}
