
import akka.actor.{Actor, ActorLogging, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}


class PurchaseMaster extends Actor with ActorLogging {

  var router = {

    val routees = Vector.fill(5) {
      val purchaseActorRef = context.actorOf(Props[PurchaseActor])
      context watch purchaseActorRef
      ActorRefRoutee(purchaseActorRef)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  override def receive = {
    case client: Customer => log.info("Sending customer to worker PurchaseActor")
      router.route(client, sender())

    case Terminated(purchaseActorTerminate) => log.info("Terminating worker PurchaseActor")
      router = router.removeRoutee(purchaseActorTerminate)
      val purchaseActorRef = context.actorOf(Props[PurchaseActor])
      context watch purchaseActorRef
      router = router.addRoutee(purchaseActorRef)
  }

}


class PurchaseActor extends Actor with ActorLogging {

  override def receive: Receive = {

    case client:Customer => log.info("Sending Galaxy Limited Edition Phone to client")
      sender() ! LimitedEdMobile

  }
}

object PurchaseMaster {

  def props: Props = Props[PurchaseMaster]

}