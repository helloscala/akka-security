package com.helloscala.akka.security.sample.server

import akka.actor.typed.ActorSystem
import akka.actor.typed.SpawnProtocol
import akka.http.scaladsl.Http
import com.helloscala.akka.security.oauth.server.OAuth2Route
import com.helloscala.akka.security.sample.server.slick.SlickCreator
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.StdIn

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-11-12 23:08:41
 */
object OAuth2ServerSlickApplication {
  private val logger = LoggerFactory.getLogger(OAuth2ServerSlickApplication.getClass)

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(SpawnProtocol(), "oauth-server")
    import system.executionContext
    Await.result(SlickCreator.init(system), 2.seconds)
    val route = new OAuth2Route(system).route
    val bindingFuture = Http().newServerAt("localhost", 9000).bind(route)

    logger.info(s"Server online at http://localhost:9000/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }
}
