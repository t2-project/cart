# t2-cart project

this is the cart. 
it stores the items a user wants to buy.

## endpoints
(autogenerated with spring-boot-starter-data-rest)

* ```/cart/{id}``` POST, GET or DELETE item with for user with sessionId ```id```


## application properties
(./ src/main/resources/application.yml)

property | read from env var | description |
-------- | ----------------- | ----------- |
spring.data.mongodb.uri | MONGO_HOST | uri of the mongo db
t2.cart.TTL		| T2_CART_TTL | time to live of items in cart (in seconds)
t2.cart.taskRate	| T2_CART_TASKRATE | rate at which the cart checks for items that exceeded their TTL (in milliseconds)

setting either t2.TTL or t2.taskrate to a value less or equal to zero disables the collection of expired cart entries.
