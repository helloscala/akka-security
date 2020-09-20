package com.helloscala.akka.security.oauth.server

import akka.actor.typed.{ ActorRef, ActorSystem, Extension, ExtensionId }
import akka.util.Timeout
import com.helloscala.akka.security.authentication.AuthenticationProvider
import com.helloscala.akka.security.oauth.server.authentication.client.RegisteredClientRepository
import com.helloscala.akka.security.oauth.server.crypto.keys.KeyManager
import com.helloscala.akka.security.oauth.server.jwt.JwtEncoder
import com.helloscala.akka.security.util.AkkaUtils
import com.typesafe.config.Config

import scala.concurrent.duration._
import scala.reflect.ClassTag

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 17:20:58
 */
class OAuth2Extension()(implicit val system: ActorSystem[_]) extends Extension {
  private implicit val timeout: Timeout = 5.seconds
  val config: Config = system.settings.config.getConfig("akka.security.server")

  val registeredClientRepository: ActorRef[RegisteredClientRepository.Command] =
    AkkaUtils.receptionistFindOne(RegisteredClientRepository.Key)

  val jwtEncoder: ActorRef[JwtEncoder.Command] = AkkaUtils.receptionistFindOne(JwtEncoder.Key)

  val keyManager: ActorRef[KeyManager.Command] = AkkaUtils.receptionistFindOne(KeyManager.Key)

  val authorizationService: ActorRef[OAuth2AuthorizationService.Command] =
    AkkaUtils.receptionistFindOne(OAuth2AuthorizationService.Key)

  val clientCredentialsAuthenticationProvider: AuthenticationProvider =
    createInstanceFor[AuthenticationProvider]("authentication-provider.client-credentials")

  private def createInstanceFor[T: ClassTag](path: String) = {
    val fqcn = config.getString(path)
    system.dynamicAccess
      .createInstanceFor[T](fqcn, List(classOf[ActorSystem[_]] -> system))
      .orElse(system.dynamicAccess.createInstanceFor[T](fqcn, Nil))
      .getOrElse(throw new ExceptionInInitializerError(s"Initial $fqcn class error."))

  }
}

object OAuth2Extension extends ExtensionId[OAuth2Extension] {
  override def createExtension(system: ActorSystem[_]): OAuth2Extension = new OAuth2Extension()(system)

}
