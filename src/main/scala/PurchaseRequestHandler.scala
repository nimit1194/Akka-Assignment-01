
import akka.actor.{Actor, ActorLogging, ActorRef, Props}


class PurchaseRequestHandler(mobileRequestActor: ActorRef) extends Actor with ActorLogging{

  override def receive: Receive = {

    case customer: Customer => log.info("Customer information received in PurchaseRequestHandler." +
      "Forwarding to ValidationActor")
      mobileRequestActor.forward(customer)

    case _ => log.error("Error in customer information")


  }

}

object PurchaseRequestHandler {

  def props(validationActor: ActorRef): Props = Props(classOf[PurchaseRequestHandler], validationActor)

}
