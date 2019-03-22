package stateful.e_callbackcomposition

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

/**
 * Client class to communicate with product service.
 */
class ProductService {

  private val queue: ScheduledExecutorService = Executors.newScheduledThreadPool(10)

  val m = Map(1 -> 100, 2 -> 200)

  def onProductPrice(productId: Int, callback: Int => Unit): Unit = {
    val runnable: Runnable = { () =>
      callback(m(productId))
    }
    queue.schedule(runnable, 1, TimeUnit.SECONDS) // Asynchronous blocking
  }
}
