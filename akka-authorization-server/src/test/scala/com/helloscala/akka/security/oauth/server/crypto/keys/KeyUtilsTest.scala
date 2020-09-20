package com.helloscala.akka.security.oauth.server.crypto.keys

import java.util.UUID

import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-20 18:29:31
 */
class KeyUtilsTest extends AnyWordSpec with Matchers {

  "KeyUtilsTest" should {
    "RSA key pair" in {
      val jwk = new RSAKeyGenerator(2048)
        .keyUse(KeyUse.SIGNATURE) // indicate the intended use of the key
        .keyID(UUID.randomUUID().toString()) // give the key a unique ID
        .generate();
      println(jwk.toString)
    }

    "generateRsaKey" in {}

    "generateSecretKey" in {}

    "convert" in {}

    "generateKeys" in {}

    "generateEcKey" in {}
  }
}
