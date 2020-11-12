package com.helloscala.akka.security.oauth.core

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.helloscala.akka.security.oauth.jacksons.ResponseTypeDeserializer
import com.helloscala.akka.security.oauth.jacksons.ResponseTypeSerializer

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-23 12:40:13
 */
@JsonSerialize(using = classOf[ResponseTypeSerializer])
@JsonDeserialize(using = classOf[ResponseTypeDeserializer])
sealed abstract class ResponseType(val VALUE: String) {
  override def toString: String = VALUE
}

object ResponseType {
  case object CODE extends ResponseType("code")
  case object TOKEN extends ResponseType("token")

  val values: Set[ResponseType] = Set(CODE, TOKEN)

  def valueOf(text: String): Option[ResponseType] = values.find(_.VALUE == text)
}
