package stateful.h_possibleactorimplementation
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.{ExecutorService, Executors, TimeUnit}

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.Success

class AccountActor {

  val queueRef: AtomicReference[List[Runnable]] = new AtomicReference[List[Runnable]](Nil)

  var _balance = 0
  var _actions : List[Action] = Nil

  // start execution of the queue messages
  while(true) {
    val runnableOpt: Option[Runnable] = queueRef.getAndUpdate(queue => queue.init).lastOption
    runnableOpt.map(runnable => AccountActor.service.submit(runnable).get(10, TimeUnit.SECONDS))
  }

  def deposit(amount: Int): Future[Unit] = {
    val p: Promise[Unit] = Promise()

    val runnable: Runnable = () => {
      _balance += amount
      Deposit(amount) :: _actions
      p.complete(Success(()))
    }
    queueRef.updateAndGet(queue => runnable :: queue)
    p.future
  }

  def withdraw(amount: Int) : Future[Unit] = {
    val p: Promise[Unit] = Promise()

    val runnable: Runnable = () => {
      _balance -= amount
      Withdrawal(amount) :: _actions
      p.complete(Success(()))
    }
    queueRef.updateAndGet(queue => runnable :: queue)
    p.future
  }

  def balance: Future[Int] = Future.successful(_balance)
}

object AccountActor {

  private val service: ExecutorService = Executors.newFixedThreadPool(100)

//  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(service)
}
