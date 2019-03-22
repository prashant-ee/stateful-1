package stateful.d_queuebasedstatemanagement

import java.util.concurrent.{ExecutorService, Executors}

case class AccountData(balance: Int, actions: List[Action])

class StatefulBankAccount(externalService: ExternalService) {

  var _balance = 0
  var _actions : List[Action] = Nil

  private val queue: ExecutorService = Executors.newSingleThreadExecutor()

  def deposit(amount: Int): Unit = {
    val depositRecord = Deposit(amount)
    externalService.record(depositRecord,
      () => {
        // Create a message/task and submit to the single threaded queue.
        val runnable : Runnable = () => {
          _balance += amount
          _actions = depositRecord :: _actions
        }
        queue.submit(runnable)
      }
    )
  }

  def withdraw(amount: Int): Unit = {
    val withdrawalRecord = Withdrawal(amount)
    externalService.record(withdrawalRecord,
      () => {
        // Create a message/task and submit to the single threaded queue.
        val runnable : Runnable = () => {
          _balance -= amount
          _actions = withdrawalRecord :: _actions
        }
        queue.submit(runnable)
      }
    )
  }

  def onBalance(callback : Int => Unit) : Unit = {
    val runnable: Runnable = () => callback(_balance)
    queue.submit(runnable)
  }
}
