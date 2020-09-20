package com.helloscala.akka.security.authentication

import scala.concurrent.Future

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 12:14:02
 */
trait AuthenticationProvider {
  def authenticate(authentication: Authentication): Future[AuthenticationToken]
}
