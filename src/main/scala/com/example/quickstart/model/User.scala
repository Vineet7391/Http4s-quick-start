package com.example.quickstart.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import org.http4s.EntityEncoder
import org.http4s.circe.jsonEncoderOf

case class User(id: String, name: String, department: String, address: String)

object User {
  implicit val userEncoder: Encoder[User] = deriveEncoder[User]

  implicit val userDecoder: Decoder[User] = deriveDecoder[User]

  implicit def userEntityEncoder[F[_]]: EntityEncoder[F, User] = jsonEncoderOf[F, User]

  implicit def usersEntityEncoder[F[_]]: EntityEncoder[F, List[User]] = jsonEncoderOf[F, List[User]]

}
