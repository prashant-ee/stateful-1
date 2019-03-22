package stateful.g_queuewithfuturebasedstatefulaccount

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.Success

/**
 * Client class to communicate with external system.
 */
class ExternalService {

  private val service: ScheduledExecutorService = Executors.newScheduledThreadPool(10)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(service)

  def record(action: Action): Future[Unit] = {
    val p: Promise[Unit] = Promise()

    val runnable: Runnable = { () =>
      println("Completed external service call")
      p.complete(Success(()))
    }
    service.schedule(runnable, 1, TimeUnit.SECONDS) // Asynchronous nonblocking
    p.future
  }
}
