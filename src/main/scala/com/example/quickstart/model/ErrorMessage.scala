package com.example.quickstart.model

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder}
import org.http4s.EntityEncoder
import org.http4s.circe.jsonEncoderOf

case class ErrorMessage(code: Int, message: String) extends Exception(s"$code:$message")

object ErrorMessage {

  implicit val errorEncoder: Encoder[ErrorMessage] = deriveEncoder[ErrorMessage]

  implicit def errorDecoder: Decoder[ErrorMessage] = deriveDecoder[ErrorMessage]

  //  implicit def errorEncode[A]: Encoder[A] = deriveEncoder[A]
  //  implicit def errorA[A]: Encoder[A] = deriveEncoder[A]

  implicit def errorEntityEncoder[F[_]]: EntityEncoder[F, ErrorMessage] = jsonEncoderOf[F, ErrorMessage]

  implicit def eitherEncoder[A](implicit errorEncoder: Encoder[ErrorMessage], aEncoder: Encoder[A]): Encoder[Either[ErrorMessage, A]] =
    Encoder.instance {
      case Left(error) => error.asJson
      case Right(value) => value.asJson
    }

  implicit def eitherEntityEncoder[F[_], A, B](implicit encoder: Encoder[Either[A, B]]): EntityEncoder[F, Either[A, B]] =
    jsonEncoderOf[F, Either[A, B]]

}
