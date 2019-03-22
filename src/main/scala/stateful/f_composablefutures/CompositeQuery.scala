package stateful.f_composablefutures
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Provide total price of 2 products
 */
class CompositeQuery(productService: ProductService) {

  def getTotalPrice(): Future[Int] = {
    val f1 = productService.getProductService(1)
    val f2 = productService.getProductService(2)
    f1.flatMap(p1 => f2.map(p2 => p1 + p2))
  }
}
