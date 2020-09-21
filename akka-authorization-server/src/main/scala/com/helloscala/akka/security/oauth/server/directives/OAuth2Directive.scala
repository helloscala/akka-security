package com.helloscala.akka.security.oauth.server.directives

import akka.http.scaladsl.model.FormData
import akka.http.scaladsl.model.headers.Authorization
import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import com.helloscala.akka.security.exception.AkkaSecurityException
import com.helloscala.akka.security.oauth.GrantType
import com.helloscala.akka.security.oauth.constant.OAuth2ParameterNames
import com.helloscala.akka.security.oauth.server.authentication.OAuth2AccessTokenAuthentication

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 11:08:36
 */
trait OAuth2Directive {
  def extractOAuth2TokenAuthentication[L]: Directive1[OAuth2AccessTokenAuthentication] =
    headerValueByType(Authorization).flatMap { authorization =>
      entity(as[FormData]).map { formData =>
        val grantType = formData.fields
          .get(OAuth2ParameterNames.GRANT_TYPE)
          .flatMap(GrantType.valueOf)
          .getOrElse(
            throw new AkkaSecurityException(s"The OAuth2 parameter ${OAuth2ParameterNames.GRANT_TYPE} not found."))
        OAuth2AccessTokenAuthentication(
          grantType,
          authorization.credentials,
          formData.fields.get(OAuth2ParameterNames.SCOPE).map(_.split(' ').toSet).getOrElse(Set()),
          formData.fields.toMap)
      }
    }
}
