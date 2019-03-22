package stateful.e_callbackcomposition

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}

object ProductCompositeQueryTest extends App {

  val productService = new ProductService()
  val compositeQuery = new CompositeQuery(productService)

  compositeQuery.onTotalPrice { totalPrice =>
    println(totalPrice)
  }
}
