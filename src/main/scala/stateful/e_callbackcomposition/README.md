The callback can be used to get value from external service in a Asynchronous non-blocking way.

##### Product Service

Add a product that can fetch product price from an external product service. 

Write a composite query implementation to combine prices of 2 products and return the sum using callbacks.

 ```scala
  // Two onProductPrice calls happen one afte the other. 
  // issue with callback composition.  
  def onTotalPrice(f: Int => Unit): Unit = {
     productService.onProductPrice(1, { p1 =>
       productService.onProductPrice(2, { p2 =>
         f(p1 + p2)
       })
     })
  }
 ```