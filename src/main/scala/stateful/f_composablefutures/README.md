Futures to solve callback issues. 

##### Product Service

Add a product that can fetch product price from an external product service. 

Write a composite query implementation to combine prices of 2 products and return the sum using futures.

 ```scala
  // f1 and f2 executed in parallel.  
  def getTotalPrice(): Future[Int] = {
    val f1 = productService.getProductService(1)
    val f2 = productService.getProductService(2)
    f1.flatMap(p1 => f2.map(p2 => p1 + p2)) 
  }
 ```