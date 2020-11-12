package com.helloscala.akka.security.oauth.core.endpoint

import com.helloscala.akka.security.oauth.core.GrantType
import com.helloscala.akka.security.oauth.core.ResponseType

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-23 12:39:41
 */
case class OAuth2AuthorizationLogin(
    authorizationUri: String,
    grantType: GrantType,
    responseType: ResponseType,
    clientId: String,
    redirectUri: Option[String],
    scopes: Set[String],
    state: Option[String])
