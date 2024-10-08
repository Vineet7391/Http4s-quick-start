package com.example.quickstart

import cats.Applicative
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._

trait HelloWorld[F[_]] {
  def hello(n: HelloWorld.Name): F[HelloWorld.Greeting]
}

object HelloWorld {
  final case class Name(name: String) extends AnyVal

  final case class Greeting(greeting: String) extends AnyVal

  object Greeting {

    implicit val greetingEncoder: Encoder[Greeting] = (a: Greeting) => Json.obj(("message", Json.fromString(a.greeting)))

    implicit def greetingEntityEncoder[F[_]]: EntityEncoder[F, Greeting] =
      jsonEncoderOf[F, Greeting]
  }

  def impl[F[_] : Applicative]: HelloWorld[F] = new HelloWorld[F] {
    def hello(n: HelloWorld.Name): F[HelloWorld.Greeting] =
      Greeting("Hello, " + n.name).pure[F]
  }

}
