package com.helloscala.akka.security.authentication

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 12:18:58
 */
trait Authentication {
  def isAuthenticated: Boolean
}
trait AuthenticationToken
