package stateful.c_externalsystemcallback

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

/**
 * Client class to communicate with external system.
 */
class ExternalService {

  private val service: ScheduledExecutorService = Executors.newScheduledThreadPool(10)

  def record(action: Action, callback: Runnable): Unit = {
    val runnable: Runnable = { () =>
      println("Completed external service call")
      callback.run()
    }
    service.schedule(runnable, 1, TimeUnit.SECONDS) // Asynchronous blocking
  }
}
