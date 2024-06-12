package com.example.quickstart

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object QuickstartRoutes {

  def jokeRoutes[F[_] : Sync](J: Jokes[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "joke" =>
        for {
          joke <- J.get
          resp <- Ok(joke)
        } yield resp
    }
  }

  def helloWorldRoutes[F[_] : Sync](H: HelloWorld[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "hello" / name =>
        for {
          greeting <- H.hello(HelloWorld.Name(name))
          resp <- Ok(greeting)
        } yield resp
    }
  }

  def uuidRoute[F[_] : Sync](u: UUIDs[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "UUID" =>
        for {
          uuid <- u.get
          resp <- Ok(uuid)
        } yield resp

      case GET -> Root / "UUID" / no =>
        for {
          uuids <- u.getAll(no.toInt)
          resp <- Ok(uuids)
        } yield resp
    }
  }
}