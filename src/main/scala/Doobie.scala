import Base._
import cats.effect.{IO, IOApp}
import doobie.implicits._
import doobie.util.transactor.Transactor

import java.time.LocalDateTime

object Doobie extends IOApp.Simple{
  val xa: Transactor[IO] = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost:5432/postgres",
    "postgres",
    "1q2w3e4r"
  )

  def blockClient(client: Client): IO[Unit] = {
    val block =
      sql"""INSERT INTO clients (client_id, block_status, created_at, reason)
            VALUES (${client.clientId}::uuid, ${client.blockStatus.status}::status, ${LocalDateTime.now().toString}::timestamp, ${client.blockStatus.reason})"""
    block.update.run.transact(xa).attempt.flatMap{
      case Left(err) => IO.raiseError(err)
      case Right(_) => IO.unit
    }
  }

  def getClient(clientId: ClientId): IO[ClientInfo] = {
    for {
      client <- sql"SELECT client_id, block_status, reason FROM clients WHERE client_id = $clientId::uuid".query[Client].unique.transact(xa).attempt
      either <- sql"SELECT created_at FROM clients WHERE client_id = $clientId::uuid".query[String].unique.transact(xa).attempt
      res <- (client, either) match {
        case (Left(err), _) => IO.raiseError(err)
        case (Right(client), Right(time)) => IO(ClientInfo(client, LocalDateTime.parse(time.replace(" ", "T"))))
      }
    } yield res
  }

  def patchClientStatus(clientId: ClientId, blockStatus: BlockStatus): IO[Int] = {
    val patch = sql"UPDATE clients SET block_status = ${blockStatus.status}::status, reason = ${blockStatus.reason} WHERE client_id = $clientId::uuid"
    patch.update.run.transact(xa)
  }

  def deleteClient(clientId: ClientId): IO[Unit] = {
    val delete = sql"DELETE FROM clients WHERE client_id = $clientId::uuid"
    delete.update.run.transact(xa).attempt.flatMap {
      case Left(err) => IO.raiseError(err)
      case Right(_) => IO.unit
    }
  }

  def dropBD: IO[Int] = {
    val drop =
      sql"""
  DROP TABLE IF EXISTS clients;
  DROP TYPE status;
""".update.run

    drop.transact(xa)
  }


    def createBD: IO[Int] = {
    val create =
//      CREATE TYPE status AS ENUM ('grey', 'white', 'black');
      sql"""
  CREATE TABLE IF NOT EXISTS clients(
    client_id UUID,
    block_status status,
    reason text,
    created_at timestamp,
    UNIQUE(client_id)
);
""".update.run

    create.transact(xa)
  }

  //создание БД
  val run: IO[Unit] = {
//    dropBD.map(_ => ())
    createBD.map(_ => ())
  }
}
