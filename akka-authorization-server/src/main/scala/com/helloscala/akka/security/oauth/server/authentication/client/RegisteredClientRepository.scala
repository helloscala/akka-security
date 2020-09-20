package com.helloscala.akka.security.oauth.server.authentication.client

import akka.actor.typed.receptionist.{ Receptionist, ServiceKey }
import akka.actor.typed.scaladsl.{ ActorContext, Behaviors }
import akka.actor.typed.{ ActorRef, Behavior }

import scala.collection.mutable

/**
 * @author Yang Jing <a href="mailto:yang.xunjing@qq.com">yangbajing</a>
 * @date 2020-09-19 17:53:41
 */
object RegisteredClientRepository {
  trait Command
  val Key = ServiceKey[Command]("RegisteredClientRepository")

  case class FindById(id: String, replyTo: ActorRef[Option[RegisteredClient]]) extends Command
  case class FindByClientId(clientId: String, replyTo: ActorRef[Option[RegisteredClient]]) extends Command

  def initMemoryRegisteredClientRepository(): Behavior[Command] = Behaviors.setup { context =>
    context.system.receptionist ! Receptionist.Register(Key, context.self)
    new InMemoryRegisteredClientRepository(context).receive()
  }
}

import com.helloscala.akka.security.oauth.server.authentication.client.RegisteredClientRepository._
class InMemoryRegisteredClientRepository(context: ActorContext[Command]) {
  private val clientIdRegisteredClientMap = mutable.Map[String, RegisteredClient]()
  private val idRegisteredClientMap = mutable.Map[String, RegisteredClient]()

  def receive(): Behavior[Command] =
    Behaviors.receiveMessagePartial {
      case FindById(id, replyTo) =>
        replyTo ! idRegisteredClientMap.get(id)
        Behaviors.same

      case FindByClientId(clientId, replyTo) =>
        replyTo ! clientIdRegisteredClientMap.get(clientId)
        Behaviors.same
    }
}
