package stateful.h_possibleactorimplementation
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import scala.concurrent.{Future, Promise}
import scala.util.Success

class AccountActor {

  trait Actionable {
    def doAction(): Unit
  }

  val queueRef: AtomicReference[List[Actionable]] = new AtomicReference[List[Actionable]](Nil)

  var _balance = 0
  var _actions : List[Action] = Nil

  import AccountActor._

  def queueExecution: Runnable = () => {
    // start execution of the queue messages
    val maybeActionable: Option[Actionable] = queueRef.getAndUpdate(queue => if(queue.nonEmpty) queue.init else queue).lastOption
    maybeActionable.foreach(actionable => actionable.doAction())
  }

  service.scheduleWithFixedDelay(queueExecution, 1, 1, TimeUnit.MICROSECONDS)

  def deposit(amount: Int): Future[Unit] = {
    val p: Promise[Unit] = Promise()

    val actionable: Actionable = () => {
      _balance += amount
      Deposit(amount) :: _actions
      p.complete(Success(()))
    }
    queueRef.updateAndGet(queue => actionable :: queue)
    p.future
  }

  def withdraw(amount: Int) : Future[Unit] = {
    val p: Promise[Unit] = Promise()

    val actionable: Actionable = () => {
      _balance -= amount
      Withdrawal(amount) :: _actions
      p.complete(Success(()))
    }
    queueRef.updateAndGet(queue => actionable :: queue)
    p.future
  }

  def balance: Future[Int] = {
    val p: Promise[Int] = Promise()
    val actionable: Actionable = () => {
      p.complete(Success(_balance))
    }
    queueRef.updateAndGet(queue => actionable :: queue)
    p.future
  }
}

object AccountActor {
  private val service: ScheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime.availableProcessors())
}
