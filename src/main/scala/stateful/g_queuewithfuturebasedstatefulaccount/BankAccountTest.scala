package stateful.g_queuewithfuturebasedstatefulaccount

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}

object BankAccountTest extends App {

  val externalService = new ExternalService()
  val bankAccount = new StatefulBankAccount(externalService)

  val service = Executors.newFixedThreadPool(100)

  implicit val ec: ExecutionContext = {
    ExecutionContext.fromExecutorService(service)
  }

  val finalFuture = Future.traverse((1 to 10000).toList) { x =>
    val f1 = bankAccount.deposit(10)
    val f2 = bankAccount.withdraw(10)

    f1.flatMap(_ => f2)
  }.flatMap(_ => bankAccount.balance)

  finalFuture.onComplete { b =>
    println(b)
    service.shutdown()
  }
}
