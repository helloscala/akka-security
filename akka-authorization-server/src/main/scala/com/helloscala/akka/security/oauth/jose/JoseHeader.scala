package com.helloscala.akka.security.oauth.jose

import com.nimbusds.jose.Header
import com.nimbusds.jose.JWSHeader

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-20 13:46:52
 */
case class JoseHeader(header: Header) {
  def toJwsHeader(keyId: String): JWSHeader = {
    new JWSHeader.Builder(header.asInstanceOf[JWSHeader]).keyID(keyId).build()
  }
}
