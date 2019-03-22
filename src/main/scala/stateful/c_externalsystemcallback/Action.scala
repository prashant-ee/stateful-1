package stateful.c_externalsystemcallback

trait Action {

}

case class Deposit(amount: Int) extends Action
case class Withdrawal(amount: Int) extends Action
