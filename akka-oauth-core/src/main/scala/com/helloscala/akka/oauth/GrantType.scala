package com.helloscala.akka.oauth

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.helloscala.akka.oauth.jacksons.GrantTypeDeserializer
import com.helloscala.akka.oauth.jacksons.GrantTypeSerializer

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 09:13:30
 */
@JsonSerialize(using = classOf[GrantTypeSerializer])
@JsonDeserialize(using = classOf[GrantTypeDeserializer])
sealed abstract class GrantType(val VALUE: String) {
  override def toString: String = VALUE
}

object GrantType {
  case object AUTHORIZATION_CODE extends GrantType("authorization_code")
  case object CLIENT_CREDENTIALS extends GrantType("client_credentials")
  case object REFRESH_TOKEN extends GrantType("refresh_token")
  case object PASSWORD extends GrantType("password")

  def valueOf(text: String): Option[GrantType] = Option(text match {
    case GrantType.AUTHORIZATION_CODE.VALUE => GrantType.AUTHORIZATION_CODE
    case GrantType.CLIENT_CREDENTIALS.VALUE => GrantType.CLIENT_CREDENTIALS
    case GrantType.REFRESH_TOKEN.VALUE      => GrantType.REFRESH_TOKEN
    case GrantType.PASSWORD.VALUE           => GrantType.PASSWORD
    case _                                  => null
  })
}
