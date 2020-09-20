package com.helloscala.akka.security.sample.server

import akka.actor.typed.ActorSystem
import akka.actor.typed.SpawnProtocol
import akka.http.scaladsl.Http
import com.helloscala.akka.security.oauth.server.DefaultOAuth2AuthorizationServer
import com.helloscala.akka.security.oauth.server.OAuth2Route
import org.slf4j.LoggerFactory

import scala.io.StdIn

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-20 23:22:55
 */
object ServerApplication {
  private val logger = LoggerFactory.getLogger(ServerApplication.getClass)

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(SpawnProtocol(), "oauth-server")
    import system.executionContext
    new DefaultOAuth2AuthorizationServer(system).init()
    val bindingFuture = Http().newServerAt("localhost", 9999).bind(new OAuth2Route(system).route)

    logger.info(s"Server online at http://localhost:9999/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
