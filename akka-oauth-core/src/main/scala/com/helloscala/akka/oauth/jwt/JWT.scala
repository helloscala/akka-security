package com.helloscala.akka.oauth.jwt

import java.time.Instant

import com.helloscala.akka.oauth.core.TraitOAuth2Token

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 18:53:39
 */
case class JWT(tokenValue: String, issuedAt: Instant, expiresAt: Instant) extends TraitOAuth2Token
