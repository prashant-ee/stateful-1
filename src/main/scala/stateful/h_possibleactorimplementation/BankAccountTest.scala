package stateful.h_possibleactorimplementation

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}

object BankAccountTest extends App {

  val bankAccounts: List[AccountActor] = List.fill(300)(new AccountActor())

  val service = Executors.newFixedThreadPool(100)

  implicit val ec: ExecutionContext = {
    ExecutionContext.fromExecutorService(service)
  }

  def processActor(bankAccount: AccountActor) = {
    val finalFuture = Future.traverse((1 to 10).toList) { x =>
      val f1 = bankAccount.deposit(10)
      val f2 = bankAccount.withdraw(10)

      f1.flatMap(_ => f2)
    }
    .flatMap(_ => bankAccount.balance)

    finalFuture.onComplete { b =>
      println(b)
    }
  }

  bankAccounts.foreach(processActor)
}
