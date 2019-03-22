package stateful.b_externalservice.asynchronousblocking

import java.util.concurrent.atomic.AtomicReference

case class AccountData(balance: Int, actions: List[Action])

class StatefulBankAccount(externalService: ExternalService) {
  val accountData = new AtomicReference[AccountData](AccountData(0, Nil))

  def deposit(amount: Int): Unit = {
    val depositRecord = Deposit(amount)
    externalService.record(depositRecord)
    accountData.updateAndGet { accountData =>
      AccountData(accountData.balance + amount, depositRecord :: accountData.actions)
    }
  }

  def withdraw(amount: Int): Unit = {
    val withdrawalRecord = Withdrawal(amount)
    externalService.record(withdrawalRecord)
    accountData.updateAndGet { accountData =>
      AccountData(accountData.balance - amount, withdrawalRecord :: accountData.actions)
    }
  }

  def balance: Int = accountData.get().balance
}
