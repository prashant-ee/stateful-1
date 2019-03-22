package stateful.b_externalservice.asynchronousblocking

trait Action {

}

case class Deposit(amount: Int) extends Action
case class Withdrawal(amount: Int) extends Action
