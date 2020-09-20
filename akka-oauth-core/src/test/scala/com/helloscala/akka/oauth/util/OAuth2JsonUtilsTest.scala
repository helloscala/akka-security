package com.helloscala.akka.oauth.util

import java.time.Instant

import com.helloscala.akka.oauth.OAuth2AccessToken
import com.helloscala.akka.oauth.TokenType
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 10:26:41
 */
class OAuth2JsonUtilsTest extends AnyWordSpec with Matchers {
  "OAuth2AccessToken" should {
    "ser" in {
      val accessToken = OAuth2AccessToken(
        "tokenValue",
        Instant.ofEpochSecond(1600482495L),
        Instant.ofEpochSecond(1601087295L),
        TokenType.BEARER,
        Set("api.access"))
      val text = """{"access_token":"tokenValue","scope":"api.access","token_type":"Bearer","expires_in":"604800"}"""
      OAuth2JsonUtils.objectMapper.writeValueAsString(accessToken) shouldBe text
    }
  }
}
