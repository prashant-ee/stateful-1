package stateful.e_callbackcomposition

/**
 * Provide total price of 2 products
 */
class CompositeQuery(productService: ProductService) {

  // FIXME - Here calls for product 1 and product 2 price happens one after the other.
  // Its a issue with composing of the call backs.
  def onTotalPrice(f: Int => Unit): Unit = {
     productService.onProductPrice(1, { p1 =>
       productService.onProductPrice(2, { p2 =>
         f(p1 + p2)
       })
     })
  }
}
