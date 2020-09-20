package com.helloscala.akka.oauth.jacksons

import com.fasterxml.jackson.core.{ JsonGenerator, JsonParseException, JsonParser }
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.databind.{ DeserializationContext, SerializerProvider }
import com.helloscala.akka.oauth.{ GrantType, TokenType }

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 09:57:46
 */
trait OAuth2JacksonModule extends OAuth2AccessTokenModule

object OAuth2JacksonModule extends OAuth2JacksonModule

class TokenTypeSerializer extends StdSerializer[TokenType](classOf[TokenType]) {
  override def serialize(value: TokenType, gen: JsonGenerator, provider: SerializerProvider): Unit =
    gen.writeString(value.VALUE)
}

class TokenTypeDeserializer extends StdDeserializer[TokenType](classOf[TokenType]) {
  override def deserialize(p: JsonParser, ctxt: DeserializationContext): TokenType = p.getText match {
    case TokenType.BEARER.VALUE => TokenType.BEARER
    case _                      => throw new JsonParseException(p, s"Invalid token type, currently only supported ${TokenType.BEARER}")
  }
}

class GrantTypeSerializer(vc: Class[GrantType]) extends StdSerializer[GrantType](vc) {
  override def serialize(value: GrantType, gen: JsonGenerator, provider: SerializerProvider): Unit =
    gen.writeString(value.VALUE)
}

class GrantTypeDeserializer(vc: Class[GrantType]) extends StdDeserializer[GrantType](vc) {
  override def deserialize(p: JsonParser, ctxt: DeserializationContext): GrantType =
    GrantType.valueOf(p.getText).getOrElse(throw new JsonParseException(p, "Invalid authorization grant type."))
}
