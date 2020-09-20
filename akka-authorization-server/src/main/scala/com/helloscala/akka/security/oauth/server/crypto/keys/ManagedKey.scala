package com.helloscala.akka.security.oauth.server.crypto.keys

import java.security.{ Key, PrivateKey, PublicKey }
import java.time.Instant

import javax.crypto.SecretKey

import scala.reflect.ClassTag

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-20 13:53:27
 */
case class ManagedKey(
    id: String,
    key: Key,
    publicKey: Option[PublicKey],
    activatedOn: Instant,
    deactivatedOn: Option[Instant]) {
  def getAlgorithm: String = key.getAlgorithm
  def getKey[T <: Key: ClassTag]: T = key.asInstanceOf[T]
  def isActive: Boolean = deactivatedOn.isEmpty
  def isSymmetric: Boolean = classOf[SecretKey].isAssignableFrom(key.getClass)
  def isAsymmetric: Boolean = classOf[PrivateKey].isAssignableFrom(key.getClass)
}
