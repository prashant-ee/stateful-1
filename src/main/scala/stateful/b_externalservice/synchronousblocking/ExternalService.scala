package stateful.b_externalservice.synchronousblocking

/**
 * Client class to communicate with external system.
 */
class ExternalService {

  def record(action: Action) : Unit = {
    Thread.sleep(1000) // Synchronous blocking
  }
}
