# Cart Service

This service manages the shopping carts for the T2 Store.
The content of a user's shopping cart is a map of Strings to Integers.
Within the context of the T2 Store it contains which products (identified by their id) and how many units there of a users wants to buy.


## Build and Run Manually

_**TODO: copy from Order was mangus checked it.**_

## HTTP Endpoints

(generated with spring-boot-starter-data-rest)

* ``/cart/{id}`` POST, GET or DELETE cart content identified by ``id``

## Usage

Confere e.g. spring's [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/) on how to talk to the generated endpoints in general.

An examplatory request to GET the cart content of the user "foo".
They have 2 units of productA and 1 unit of product B in their cart. 
```
curl localhost:8080/cart/foo
```
```
{
  "content" : {
    "productA" : 2,
    "productB" : 1
  },
  "creationDate" : "2021-05-12T16:20:55.211+00:00",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/cart/foo"
    },
    "cart" : {
      "href" : "http://localhost:8080/cart/foo"
    }
  }
}

```

## Application Properties
(./src/main/resources/application.yml)

property | read from env var | description |
-------- | ----------------- | ----------- |
spring.data.mongodb.uri | MONGO_HOST | uri of the mongo db
t2.cart.TTL		| T2_CART_TTL | time to live of items in cart (in seconds)
t2.cart.taskRate	| T2_CART_TASKRATE | rate at which the cart checks for items that exceeded their TTL (in milliseconds)

setting either t2.TTL or t2.taskrate to a value less or equal to zero disables the collection of expired cart entries.