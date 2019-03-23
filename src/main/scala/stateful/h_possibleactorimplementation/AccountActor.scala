package stateful.h_possibleactorimplementation
import java.util.concurrent.{ExecutorService, Executors, Future, TimeUnit}
import java.util.concurrent.atomic.AtomicReference

class AccountActor {

  val queueRef: AtomicReference[List[Runnable]] = new AtomicReference[List[Runnable]](Nil)

  var _balance = 0
  var _actions : List[Action] = Nil

  // start execution of the queue messages
  while(true) {
    val runnable: Runnable = queueRef.getAndUpdate(queue => queue.init).last
    AccountActor.service.submit(runnable).get(10, TimeUnit.SECONDS)
  }

  def deposit(amount: Int): Unit = {
    val runnable: Runnable = () => {
      _balance += amount
      Deposit(amount) :: _actions
    }
    queueRef.updateAndGet(queue => runnable :: queue)
  }

  def withdraw(amount: Int) : Unit = {
    val runnable: Runnable = () => {
      _balance -= amount
      Withdrawal(amount) :: _actions
    }
    queueRef.updateAndGet(queue => runnable :: queue)
  }

  def balance = ???
}

object AccountActor {
  private val service: ExecutorService = Executors.newFixedThreadPool(100)
}
