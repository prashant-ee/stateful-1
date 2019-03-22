package stateful.f_composablefutures
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ProductCompositeQueryTest extends App {

  val productService = new ProductService()
  val compositeQuery = new CompositeQuery(productService)

  private val f: Future[Int] = compositeQuery.getTotalPrice()

  f.onComplete(println)
}
