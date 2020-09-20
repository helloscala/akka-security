package com.helloscala.akka.security.oauth.server.crypto.keys

import java.math.BigInteger
import java.security.interfaces.{ ECPublicKey, RSAPublicKey }
import java.security.spec.{ ECFieldFp, ECParameterSpec, ECPoint, EllipticCurve }
import java.security.{ KeyPair, KeyPairGenerator }
import java.time.Instant
import java.util.UUID

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk._
import javax.crypto.{ KeyGenerator, SecretKey }

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-20 14:06:18
 */
object KeyUtils {
  def convert(managedKey: ManagedKey): Option[JWK] = {
    Option(managedKey.publicKey match {
      case publicKey: RSAPublicKey =>
        new RSAKey.Builder(publicKey).keyUse(KeyUse.SIGNATURE).algorithm(JWSAlgorithm.RS256).keyID(managedKey.id).build
      case publicKey: ECPublicKey =>
        val curve = Curve.forECParameterSpec(publicKey.getParams)
        new ECKey.Builder(curve, publicKey)
          .keyUse(KeyUse.SIGNATURE)
          .algorithm(JWSAlgorithm.ES256)
          .keyID(managedKey.id)
          .build
      case _ => null
    })
  }

  def generateSecretKey(): SecretKey = {
    try KeyGenerator.getInstance("HmacSha256").generateKey
    catch {
      case ex: Exception =>
        throw new IllegalStateException(ex)
    }
  }

  def generateRsaKey(): KeyPair = {
    try {
      val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
      keyPairGenerator.initialize(2048)
      keyPairGenerator.generateKeyPair
    } catch {
      case ex: Exception =>
        throw new IllegalStateException(ex)
    }
  }

  def generateEcKey(): KeyPair = {
    val ellipticCurve = new EllipticCurve(
      new ECFieldFp(new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951")),
      new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853948"),
      new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291"))
    val ecPoint = new ECPoint(
      new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286"),
      new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109"))
    val ecParameterSpec = new ECParameterSpec(
      ellipticCurve,
      ecPoint,
      new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369"),
      1)
    try {
      val keyPairGenerator = KeyPairGenerator.getInstance("EC")
      keyPairGenerator.initialize(ecParameterSpec)
      keyPairGenerator.generateKeyPair
    } catch {
      case ex: Exception =>
        throw new IllegalStateException(ex)
    }
  }

  def generateKeys(): Map[String, ManagedKey] = {
    val rsaKeyPair = generateRsaKey()
    val rsaManagedKey =
      ManagedKey(UUID.randomUUID().toString, rsaKeyPair.getPrivate, Some(rsaKeyPair.getPublic), Instant.now(), None)
    val hmacKey: SecretKey = generateSecretKey()
    val secretManagedKey: ManagedKey = ManagedKey(UUID.randomUUID().toString, hmacKey, None, Instant.now(), None)
    Map(rsaManagedKey.id -> rsaManagedKey, secretManagedKey.id -> secretManagedKey)
  }
}
