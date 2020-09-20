package com.helloscala.akka.security.oauth.server.jwt
import java.security.PrivateKey
import java.time.Instant

import akka.actor.typed.ActorRef
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.scaladsl.{ ActorContext, Behaviors }
import akka.pattern.StatusReply
import akka.util.Timeout
import com.helloscala.akka.oauth.jwt.JWT
import com.helloscala.akka.security.exception.AkkaSecurityException
import com.helloscala.akka.security.oauth.jose.JoseHeader
import com.helloscala.akka.security.oauth.server.OAuth2Extension
import com.helloscala.akka.security.oauth.server.authentication.OAuth2ClientCredentialsAuthentication
import com.helloscala.akka.security.oauth.server.crypto.keys.{ KeyManager, ManagedKey }
import com.nimbusds.jose.crypto.{ MACSigner, RSASSASigner }
import com.nimbusds.jose.{ JWSHeader, JWSSigner }
import com.nimbusds.jwt.{ JWTClaimsSet, SignedJWT }
import javax.crypto.SecretKey

import scala.concurrent.duration._

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 18:28:55
 */
object JwtEncoder {
  trait Command
  val Key = ServiceKey[Command]("JwtEncoder")

  case class Encode(
      authentication: OAuth2ClientCredentialsAuthentication,
      joseHeader: JoseHeader,
      jwtClaim: JWTClaimsSet,
      replyTo: ActorRef[StatusReply[JWT]])
      extends Command

  case class EncodeWithManagedKey(
      managedKey: ManagedKey,
      joseHeader: JoseHeader,
      jwtClaim: JWTClaimsSet,
      replyTo: ActorRef[StatusReply[JWT]])
      extends Command
}

import com.helloscala.akka.security.oauth.server.jwt.JwtEncoder._
class DefaultJwtEncoder(context: ActorContext[Command]) {
  implicit private val system = context.system
  implicit private val ec = system.executionContext
  implicit private val timeout: Timeout = 5.seconds
  private val oauth2Extension = OAuth2Extension(context.system)

  def receive: Behaviors.Receive[Command] = Behaviors.receiveMessagePartial {
    case Encode(authentication, joseHeader, jwtClaim, replyTo) =>
      val keyId = authentication.registeredClient.keyId
      oauth2Extension.keyManager.ask[Option[ManagedKey]](ref => KeyManager.FindById(keyId, ref)).foreach {
        case Some(managedKey) => context.self ! EncodeWithManagedKey(managedKey, joseHeader, jwtClaim, replyTo)
        case None             => replyTo ! StatusReply.error(s"ManagedKey not found, key id is $keyId")
      }
      Behaviors.same

    case EncodeWithManagedKey(managedKey, joseHeader, claim, replyTo) =>
      try {
        val jwsSigner: JWSSigner = if (managedKey.isAsymmetric) {
          val privateKey = managedKey.getKey[PrivateKey]
          managedKey.getAlgorithm match {
            case "RSA" => new RSASSASigner(privateKey)
            case _     => throw new AkkaSecurityException(s"Unsupported key type '${managedKey.getAlgorithm}'")
          }
        } else {
          val secretKey = managedKey.getKey[SecretKey]
          new MACSigner(secretKey)
        }
        val jwsHeader = joseHeader.toJwsHeader(managedKey.id)
        val jwtClaim = new JWTClaimsSet.Builder(claim).jwtID(managedKey.id).build()
        val signedJWT = new SignedJWT(jwsHeader, jwtClaim)
        signedJWT.sign(jwsSigner)
        val tokenValue = signedJWT.serialize()
        val jwt = JWT(tokenValue, jwtClaim.getIssueTime.toInstant, jwtClaim.getExpirationTime.toInstant)
        replyTo ! StatusReply.success(jwt)
      } catch {
        case e: Throwable =>
          replyTo ! StatusReply.error(e)
      }
      Behaviors.same
  }
}
