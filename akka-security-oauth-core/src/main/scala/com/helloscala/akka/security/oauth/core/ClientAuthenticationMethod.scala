package com.helloscala.akka.security.oauth.core

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-23 17:16:21
 */
sealed abstract class ClientAuthenticationMethod(val VALUE: String) {
  override def toString: String = VALUE
}

object ClientAuthenticationMethod {
  case object BASIC extends ClientAuthenticationMethod("basic")
  case object POST extends ClientAuthenticationMethod("post")
  case object NONE extends ClientAuthenticationMethod("none")

  val values: Set[ClientAuthenticationMethod] = Set(BASIC, POST, NONE)

  def valueOf(text: String): Option[ClientAuthenticationMethod] = values.find(_.VALUE == text)
}
