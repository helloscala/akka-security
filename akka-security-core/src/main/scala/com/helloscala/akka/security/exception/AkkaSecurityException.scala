package com.helloscala.akka.security.exception

import scala.util.control.NoStackTrace

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 11:30:26
 */
class AkkaSecurityException(val message: String, val cause: Throwable)
    extends RuntimeException(message, cause: Throwable) {
  def this(message: String) { this(message, null) }
  def this() { this("", null) }

  override def fillInStackTrace(): Throwable =
    if (NoStackTrace.noSuppression) super.fillInStackTrace()
    else this

}
