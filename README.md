# coinpaymentsnet-java-api
Java API implementation for the cryptocoin payment provider "coinpayments.net"

This is a simple API for the coinpayments.net platform which is an integrated payment gateway for cryptocurrencies such as Bitcoin and Litecoin. The actual API documentation can be looked up at https://www.coinpayments.net/apidoc

[Our implementation](./src/net/coinpayments/CoinPaymentsAPI.java) is a simple Java wrapper to make it useable from within your Java projects. We have tried to keep it as 
simple an clean as possible, but however there are some dependencies like slf4j, commons-codec and gson which must be resolved.
This can either be done via apache-ivy or by hand. 

Check the whole repository out and import it as eclipse project to have a look at the [JUnit testcases](./test/CpnetTests.java) in order to understand how it is intended to be used. 

If you still have no idea what it can be used for, then have a look at the corresponding [blog article which gives a short introduction](http://www.bytebang.at/Blog/Implement+cryptocoin+payments+with+Java)

Have fun with it, and if you like it then please consider to donate some Bitcoins to the adress 1Ja5cxfZcRd5ufXNE9g6vAAJTnQ4ToGaoD
