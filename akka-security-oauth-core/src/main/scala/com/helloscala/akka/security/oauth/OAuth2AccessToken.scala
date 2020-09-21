package com.helloscala.akka.security.oauth

import java.time.Instant

import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.HttpEntity
import com.helloscala.akka.security.oauth.core.TraitOAuth2Token
import com.helloscala.akka.security.oauth.util.OAuth2JsonUtils

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 09:13:02
 */
case class OAuth2AccessToken(
    tokenValue: String,
    issuedAt: Instant,
    expiresAt: Instant,
    tokenType: TokenType,
    scopes: Set[String])
    extends TraitOAuth2Token {

  def toJSONString(): String = OAuth2JsonUtils.objectMapper.writeValueAsString(this)

  def toHttpEntity: HttpEntity.Strict = HttpEntity(ContentTypes.`application/json`, toJSONString())
}
