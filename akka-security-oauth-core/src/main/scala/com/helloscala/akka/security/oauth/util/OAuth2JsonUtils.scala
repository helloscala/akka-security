package com.helloscala.akka.security.oauth.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.helloscala.akka.security.oauth.jacksons.OAuth2JacksonModule
import com.helloscala.akka.security.util.JsonUtils

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 10:19:16
 */
object OAuth2JsonUtils {
  val objectMapper: ObjectMapper = JsonUtils.objectMapper.copy().registerModule(OAuth2JacksonModule)
}
