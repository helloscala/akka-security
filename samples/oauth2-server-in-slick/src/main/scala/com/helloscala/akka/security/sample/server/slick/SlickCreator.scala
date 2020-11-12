package com.helloscala.akka.security.sample.server.slick

import akka.actor.typed.ActorSystem
import akka.actor.typed.SpawnProtocol

import scala.concurrent.Future

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-11-12 23:10:33
 */
object SlickCreator {
  def init(system: ActorSystem[SpawnProtocol.Command]): Future[Unit] = {
    ???
  }
}
