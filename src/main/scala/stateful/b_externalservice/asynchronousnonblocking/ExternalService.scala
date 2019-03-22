package stateful.b_externalservice.asynchronousnonblocking

import java.util.concurrent.{ExecutorService, Executors, ScheduledExecutorService, TimeUnit}

/**
 * Client class to communicate with external system.
 */
class ExternalService {

  private val service: ScheduledExecutorService = Executors.newScheduledThreadPool(10)

  def record(action: Action): Unit = {
    val runnable: Runnable = { () =>
      // Thread.sleep(1000) <== Blocking call removed.
      println("Completed external service call")
    }
    service.schedule(runnable, 1, TimeUnit.SECONDS) // Asynchronous blocking
  }
}
