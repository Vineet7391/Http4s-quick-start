package DBconnection


import cats.effect.Async
import com.example.quickstart.model.{ErrorMessage, User}
import doobie.Transactor
import doobie.implicits._


class PostgresBDConnection[F[_] : Async] {
  private val xa = Transactor.fromDriverManager[F](
    driver = "org.postgresql.Driver", // JDBC driver class name
    url = "jdbc:postgresql:postgres", // Connect URL
    user = "postgres", // Database user name
    password = "750050", // Database password
    logHandler = None, // Don't setup logging for now. See Logging page for how to log events in detail

  )


  def getAllUserDb: F[Either[ErrorMessage, List[User]]] = {
    val q = sql"SELECT * FROM users".query[User].to[List]
    q.attemptSql.map {
      case Left(error) => Left(ErrorMessage(error.getErrorCode, error.getMessage))
      case Right(users) => Right(users)
    }.transact(xa)

  }


  def getUserDb(id: String): F[Either[ErrorMessage, User]] = {
    println(id)
    val q = sql"SELECT * FROM users WHERE id = $id"
      .query[User]
    q.unique.attemptSql.map {
      case Left(error) => Left(ErrorMessage(error.getErrorCode, error.getMessage))
      case Right(user) => Right(user)
    }.transact(xa)
  }
}
