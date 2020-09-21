package com.helloscala.akka.security.oauth

import akka.http.scaladsl.model.AttributeKey

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 11:00:55
 */
case class OAuth2Authorization(
    principalName: String,
    accessToken: OAuth2AccessToken,
    attributes: Map[AttributeKey[_], _])
