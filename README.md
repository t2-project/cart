# Cart Service

This service manages the shopping carts for the T2-Project.
The content of a user's shopping cart is a map of Strings to Integers.
Within the context of the T2-Project it contains which products (identified by their id) and how many units there of a users wants to buy.

## Build and Run

Refer to the [Documentation](https://t2-documentation.readthedocs.io/en/latest/microservices/deploy.html) on how to build, run or deploy and use the T2-Project services.

## Application Properties

(`./src/main/resources/application.yml`)

| property | read from env var | description |
| -------- | ----------------- | ----------- |
| spring.data.mongodb.uri | MONGO_HOST | hostname of the mongo db |
| t2.cart.TTL | T2_CART_TTL | time to live of items in cart (in seconds) |
| t2.cart.taskRate | T2_CART_TASKRATE | rate at which the cart checks for items that exceeded their TTL (in milliseconds) |

setting either `t2.TTL` or `t2.taskrate` to a value less or equal to zero disables the collection of expired cart entries.
