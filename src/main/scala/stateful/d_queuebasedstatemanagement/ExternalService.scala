package stateful.d_queuebasedstatemanagement

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

/**
 * Client class to communicate with external system.
 */
class ExternalService {

  private val queue: ScheduledExecutorService = Executors.newScheduledThreadPool(10)

  def record(action: Action, callback: Runnable): Unit = {
    val runnable: Runnable = { () =>
      println("Completed external service call")
      callback.run()
    }
    queue.schedule(runnable, 1, TimeUnit.SECONDS) // Asynchronous blocking
  }
}
