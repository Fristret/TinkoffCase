import Base._
import Doobie._
import cats.effect.IO
import org.http4s.HttpRoutes
import Parsing._
import org.http4s.dsl.io._

object Routes {

  import org.http4s.circe.CirceEntityCodec._

  val route: HttpRoutes[IO] = {
    HttpRoutes.of[IO] {
      case req@POST -> Root / "clients" =>
        {
          for {
          client <- req.as[Client]
          resp <- blockClient(client) *> Ok(AnswerMessage("Клиент заблокирован"))
          } yield resp
        }.handleErrorWith(e => BadRequest(GeneralError(s"${e.getMessage}")))

      case GET -> Root / "clients" / clientId =>
        {
          for {
            client <- getClient(clientId)
            resp <- Ok(client)
          } yield resp
        }.handleErrorWith(e => BadRequest(GeneralError(s"${e.getMessage}")))

      case req@PATCH -> Root/"clients"/clientId =>
        {
          for{
            status <- req.as[BlockStatus]
            resp <- patchClientStatus(clientId, status) *> Ok(AnswerMessage("Статус блокировки изменён"))
          } yield resp
        }.handleErrorWith(e => BadRequest(GeneralError(s"${e.getMessage}")))

      case DELETE -> Root/ "clients" / clientId =>
        {
          for{
            resp <- deleteClient(clientId) *> Ok(AnswerMessage("Клиент удалён"))
          } yield resp
        }.handleErrorWith(e => BadRequest(GeneralError(s"${e.getMessage}")))
    }
  }
}
