package stateful.g_queuewithfuturebasedstatefulaccount

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}

class StatefulBankAccount(externalService: ExternalService) {
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor())

  var _balance = 0
  var _actions : List[Action] = Nil

  def deposit(amount: Int): Future[Unit] = {
    val deposit = Deposit(amount)
    externalService.record(deposit).map { _ =>
      _balance += amount
      deposit :: _actions
    }
  }

  def withdraw(amount: Int): Future[Unit] = {
    val withdrawal = Withdrawal(amount)
    externalService.record(withdrawal).map { _ =>
      _balance -= amount
      withdrawal :: _actions
    }
  }

  def balance: Future[Int] = Future.successful(_balance)
}
