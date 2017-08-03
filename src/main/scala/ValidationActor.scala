
import akka.actor.{Actor, ActorLogging, ActorRef, Props}

/**
  * Created by knoldus on 2/8/17.
  */
class ValidationActor(purchaseActorMaster: ActorRef) extends Actor with ActorLogging {

  var counter = 1000

  override def receive: Receive = {

    case customer: Customer => if(counter > 0)
      {
        log.info("Smartphone is available")
        counter -= 1
        log.info(s"Phone left in Storage is $counter")
        purchaseActorMaster.forward(customer)
      }
      else
      {
        sender() ! "Out of Stock!"
      }
  }

}

object ValidationActor {

  def props(purchaseActorMaster: ActorRef): Props = Props(classOf[ValidationActor], purchaseActorMaster)

}
