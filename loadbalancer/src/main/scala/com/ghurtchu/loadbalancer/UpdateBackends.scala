package com.ghurtchu.loadbalancer

import cats.effect.{IO, Ref}

trait UpdateBackends {
  def next: IO[Backends]
}

object UpdateBackends {
  def of(backends: Ref[IO, Backends]): UpdateBackends = new UpdateBackends {
    override def next: IO[Backends] =
      backends.updateAndGet(_.next)
  }
}
