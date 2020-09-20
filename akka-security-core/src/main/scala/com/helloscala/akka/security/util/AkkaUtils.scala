package com.helloscala.akka.security.util

import akka.actor.typed.receptionist.{ Receptionist, ServiceKey }
import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ ActorRef, ActorSystem }
import akka.util.Timeout
import com.helloscala.akka.security.exception.AkkaSecurityException

import scala.concurrent.Await

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 16:15:26
 */
object AkkaUtils {
  def receptionistFindSet[T](
      serviceKey: ServiceKey[T])(implicit system: ActorSystem[_], timeout: Timeout): Set[ActorRef[T]] = {
    implicit val ec = system.executionContext
    val f = system.receptionist.ask[Receptionist.Listing](Receptionist.Find(serviceKey)).map { listing =>
      if (listing.isForKey(serviceKey)) listing.serviceInstances(serviceKey) else Set[ActorRef[T]]()
    }
    Await.result(f, timeout.duration)
  }

  def receptionistFindOne[T](
      serviceKey: ServiceKey[T])(implicit system: ActorSystem[_], timeout: Timeout): ActorRef[T] = {
    receptionistFindSet(serviceKey).headOption.getOrElse(throw new AkkaSecurityException(s"$serviceKey not found!"))
  }
}
