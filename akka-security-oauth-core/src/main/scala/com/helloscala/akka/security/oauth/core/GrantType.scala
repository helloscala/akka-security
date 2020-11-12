package com.helloscala.akka.security.oauth.core

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.helloscala.akka.security.oauth.jacksons.GrantTypeDeserializer
import com.helloscala.akka.security.oauth.jacksons.GrantTypeSerializer

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

  val values: Set[GrantType] = Set(AUTHORIZATION_CODE, CLIENT_CREDENTIALS, REFRESH_TOKEN, PASSWORD)

  def valueOf(text: String): Option[GrantType] = values.find(_.VALUE == text)
}
