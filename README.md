# Cart Service

This service manages shopping carts for the T2 Store.
The content of a user's shopping cart is simply a map of Strings to Integers.
Within the context of the T2 Store it contains for each user the products (identified by their id) and how many units of each the users wants to buy.


## Build and Run Manually

_**TODO: copy from Order was mangus checked it.**_

## HTTP Endpoints

(generated with spring-boot-starter-data-rest)

* ``/cart/{id}`` POST, GET or DELETE cart content identified by ``id``

## Usage

Confere e.g. spring's [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/) on how to talk to the generated endpoints in general.

An examplatory request to GET the cart content of the user "foo" : 
```
curl localhost:8080/cart/foo
```
Response : 
```
{
  "content" : {
    "607819f88949803a9bffd280" : 2,
    "asdf" : 2
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