### Challenge:

We need to add an external service call. This call needs to happen 
before account deposit and withdrawal.

For simplicity, the external service call can be simulated by just sleeping for 1 second.

### First Implementation :

#### Synchronous blocking call

This is where the call is synchronous means client is waiting for the response 
and its using blocking on the thread. 

#### Asynchronous blocking call 

This is where the call is asynchronous means client is not waiting for the response 
and but ExternalService class still used blocking IO calls. 

#### Asynchronous nonblocking call 

This is where the call is asynchronous means client is not waiting for the response 
and ExternalService class needs to use non blocking IO calls. 