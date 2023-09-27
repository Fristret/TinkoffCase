import java.time.LocalDateTime
import java.util.UUID

object Base {
  type ClientId = String
  private type Reason = String
  private type Timestamp = LocalDateTime

  final case class AnswerMessage(message: String) extends AnyVal
  final case class GeneralError(error: String) extends AnyVal

  final case class BlockStatus(status: String, reason: Reason)
  final case class Client(clientId: ClientId, blockStatus: BlockStatus)

  final case class ClientInfo(client: Client, createdAt: Timestamp)
}
