package com.ghurtchu.loadbalancer

import cats.effect.{IO, IOApp, Ref}

object Main extends IOApp.Simple {

  private val BackendsRef: IO[Ref[IO, Backends]] =
    Ref.of(Backends("http://localhost:9002/hello", "http://localhost:9003/hello"))

  val run: IO[Unit] =
    for {
      backends <- BackendsRef
      _ <- LoadbalancerServer.run(backends)
    } yield ()
}
