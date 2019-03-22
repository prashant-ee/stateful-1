package stateful.a_statefulaccount
import java.util.concurrent.atomic.AtomicReference

case class AccountData(balance: Int, actions: List[Action])

class StatefulBankAccount {
  val accountData = new AtomicReference[AccountData](AccountData(0, Nil))

  def deposit(amount: Int): Unit = {
    accountData.updateAndGet { accountData =>
      AccountData(accountData.balance + amount, Deposit(amount) :: accountData.actions)
    }
  }

  def withdraw(amount: Int): Unit = {
    accountData.updateAndGet { accountData =>
      AccountData(accountData.balance - amount, Withdrawal(amount) :: accountData.actions)
    }
  }

  def balance: Int = accountData.get().balance
}
