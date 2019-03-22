package stateful.a_statefulaccount

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}

object BankAccountTest extends App {

  val bankAccount = new StatefulBankAccount()

  val service = Executors.newFixedThreadPool(100)

  implicit val ec: ExecutionContext = {
    ExecutionContext.fromExecutorService(service)
  }

  val finalFuture = Future.traverse((1 to 10000).toList) { x =>
    val f1 = Future {
      bankAccount.deposit(10)
    }

    val f2 = Future {
      bankAccount.withdraw(10)
    }

    f1.flatMap(_ => f2)
  }

  finalFuture.onComplete { _ =>
    println(bankAccount.balance)
    service.shutdown()
  }
}
