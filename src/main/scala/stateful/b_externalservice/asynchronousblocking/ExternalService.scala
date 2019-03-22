package stateful.b_externalservice.asynchronousblocking
import java.util.concurrent.{ExecutorService, Executors}

/**
 * Client class to communicate with external system.
 */
class ExternalService {

  private val service: ExecutorService = Executors.newFixedThreadPool(10)

  def record(action: Action): Unit = {
    val runnable: Runnable = { () =>
      Thread.sleep(1000) // Asynchronous blocking
      println("Completed external service call")
    }
    service.submit(runnable)
  }
}
