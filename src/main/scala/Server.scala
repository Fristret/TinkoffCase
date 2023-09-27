import Routes._
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext

object Server extends IOApp{

  private val httpApp = for{
    _ <- Doobie.run
  } yield route.orNotFound

  override def run(args: List[String]): IO[ExitCode] = for {
    httpApp <- httpApp
    _ <- BlazeServerBuilder[IO](ExecutionContext.global)
      .bindHttp(port = 9001, host = "localhost")
      .withHttpApp(httpApp)
      .serve
      .compile
      .drain
  } yield ExitCode.Success
}
