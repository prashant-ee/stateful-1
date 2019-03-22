package stateful.f_composablefutures

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import scala.concurrent.{ExecutionContext, Future}

/**
 * Client class to communicate with product service.
 */
class ProductService {

  private val queue: ScheduledExecutorService = Executors.newScheduledThreadPool(10)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(queue)

  val m = Map(1 -> 100, 2 -> 200)

  def getProductService(productId: Int): Future[Int] = Future {
      m(productId)
  }
}


