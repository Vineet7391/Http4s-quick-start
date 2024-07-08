package com.example.quickstart.repositories

import DBconnection.PostgresBDConnection
import cats.effect.Async
import com.example.quickstart.model.{ErrorMessage, User}

trait UserT[F[_]] {

  def get(id: String): F[Either[ErrorMessage, User]]

  def getAll: F[Either[ErrorMessage, List[User]]]
}


class UserRepo[F[_] : Async] extends PostgresBDConnection with UserT[F] {
  //  def impl[F[_] : Applicative]: UserT[F] = new UserT[F] {
  ////    override def get(id: String): F[User] = getUserDb(id)
  //
  ////    override def getAll: F[List[User]] = allUserDb
  //  }

  override def get(id: String): F[Either[ErrorMessage, User]] = getUserDb(id)


  override def getAll: F[Either[ErrorMessage, List[User]]] = getAllUserDb
}