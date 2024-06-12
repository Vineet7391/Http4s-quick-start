package com.example.quickstart

import cats.effect.IO
import munit.CatsEffectSuite
import org.http4s.{Method, Request, Response, Status}
import org.http4s.implicits._

class UUIDsSpec extends CatsEffectSuite {

  private val retUUID: IO[Response[IO]] = {
    val getUUID = Request[IO](Method.GET, uri"/UUID")
    val uuid = UUIDs.impl[IO]
    QuickstartRoutes.uuidRoute(uuid).orNotFound(getUUID)
  }


  test("UUID return status code 200") {
    assertIO(retUUID.map(_.status), Status.Ok)
  }

}
