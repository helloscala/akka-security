package com.helloscala.akka.security.oauth.jwt

import java.time.Instant

import com.helloscala.akka.security.oauth.core.TraitOAuth2Token

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 18:53:39
 */
case class Jwt(tokenValue: String, issuedAt: Instant, expiresAt: Instant) extends TraitOAuth2Token
