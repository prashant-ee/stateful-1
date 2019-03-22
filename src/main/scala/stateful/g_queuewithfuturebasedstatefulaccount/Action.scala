package stateful.g_queuewithfuturebasedstatefulaccount

trait Action {

}

case class Deposit(amount: Int) extends Action
case class Withdrawal(amount: Int) extends Action
