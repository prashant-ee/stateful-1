Instead of using AtomicReference based state management. 
Queue based message passing can be used, which will execute state changes in a single thread model.

The external service can also be treated as having a queue. 