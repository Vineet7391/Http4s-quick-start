package com.example.quickstart.repositories

import DBconnection.PostgresBDConnection
import cats.effect.Async
import com.example.quickstart.model.{ErrorMessage, User}

trait UserT[F[_]] {

  def get(id: String): F[Either[ErrorMessage, User]]

  def getAll: F[Either[ErrorMessage, List[User]]]

  def create(id: String, name: String, department: String, address: String): F[Either[ErrorMessage, String]]
}


class UserRepo[F[_] : Async] extends PostgresBDConnection with UserT[F] {

  override def get(id: String): F[Either[ErrorMessage, User]] = getUserDb(id)

  override def getAll: F[Either[ErrorMessage, List[User]]] = getAllUserDb

  override def create(id: String, name: String, department: String, address: String): F[Either[ErrorMessage, String]] = create(id, name, department, address)
}