
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration.DurationInt
import scala.concurrent.ExecutionContext.Implicits.global


object SamsungStore extends App {

  val customer = Customer("NimitJain", "Saharanpur", 1936974591235645L, 9717857151L)

  val actorSystem = ActorSystem("SamsungMobileMart")

  val purchaseActorMaster = actorSystem.actorOf(PurchaseMaster.props)

  val validationActor = actorSystem.actorOf(ValidationActor.props(purchaseActorMaster))

  val purchaseRequestHandler = actorSystem.actorOf(PurchaseRequestHandler.props(validationActor))

  implicit val timeout = Timeout(100 seconds)

  val result = purchaseRequestHandler ? customer

  result.foreach(println(_))
}
