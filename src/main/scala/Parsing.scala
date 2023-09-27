import Base._
import io.circe._
import io.circe.generic.semiauto._

object Parsing {
  implicit val clientDecoder: Decoder[Client] = deriveDecoder
  implicit val clientEncoder: Encoder[Client] = deriveEncoder

  implicit val answerMessageEncoder: Encoder[AnswerMessage] = deriveEncoder

  implicit val generalErrorEncoder: Encoder[GeneralError] = deriveEncoder

  implicit val blockStatusEncoder: Encoder[BlockStatus] = deriveEncoder
  implicit val blockStatusDecoder: Decoder[BlockStatus] = deriveDecoder

  implicit val deriveClientInfoEncoder: Encoder[ClientInfo] = deriveEncoder
  implicit val deriveClientInfoDecoder: Decoder[ClientInfo] = deriveDecoder
}
