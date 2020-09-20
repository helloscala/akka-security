package com.helloscala.akka.security.oauth.server

import akka.actor.typed.receptionist.ServiceKey
import com.helloscala.akka.security.oauth.server.authentication.OAuth2AccessTokenAuthenticationToken

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 18:34:53
 */
object OAuth2AuthorizationService {
  trait Command
  trait Event extends Command
  val Key = ServiceKey[Command]("OAuth2AuthorizationService")

  case class Save(authenticationToken: OAuth2AccessTokenAuthenticationToken) extends Event
}
