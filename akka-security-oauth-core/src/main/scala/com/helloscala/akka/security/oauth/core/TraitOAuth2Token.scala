package com.helloscala.akka.security.oauth.core

import java.time.Instant

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 19:06:59
 */
trait TraitOAuth2Token {
  def tokenValue: String
  def issuedAt: Instant
  def expiresAt: Instant
}
