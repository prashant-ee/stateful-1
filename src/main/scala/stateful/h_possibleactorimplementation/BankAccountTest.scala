package stateful.h_possibleactorimplementation

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}
import scala.tools.nsc.io.File

object BankAccountTest extends App {

  val bankAccounts: List[AccountActor] = List.fill(1000000)(new AccountActor())

  val service = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors())

  implicit val ec: ExecutionContext = {
    ExecutionContext.fromExecutorService(service)
  }

  def processActor(index: Int, bankAccount: AccountActor): Unit = {
    val finalFuture = Future.traverse((1 to 10).toList) { x =>
      println(s"transaction : $x")
      val f1 = bankAccount.deposit(10)
      val f2 = bankAccount.withdraw(10)

      f1.flatMap(_ => f2)
    }
    .flatMap(_ => bankAccount.balance)

    finalFuture.onComplete { b =>
      println(f"$index%06d) Result : $b")
      File("output").createFile().appendAll(f"$index%06d) Result : $b\n")
    }
  }

  bankAccounts.zipWithIndex.foreach{ x => processActor(x._2, x._1) }
}
