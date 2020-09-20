package com.helloscala.akka.oauth

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.helloscala.akka.oauth.jacksons.TokenTypeDeserializer
import com.helloscala.akka.oauth.jacksons.TokenTypeSerializer

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 09:24:53
 */
@JsonSerialize(using = classOf[TokenTypeSerializer])
@JsonDeserialize(using = classOf[TokenTypeDeserializer])
sealed abstract class TokenType(val VALUE: String) {
  override def toString: String = VALUE
}

object TokenType {
  case object BEARER extends TokenType("Bearer")
}
