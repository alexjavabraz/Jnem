# Nem Java Wallet / Integration Application
Java Maven

Run CreateAccount.java

You need to obtain your private key, and public key to transfer Faucet Xems

All account started by T is a TestNet account (Faucet Xem)

References
https://github.com/NEMPH

https://github.com/rosklyar

JDK 1.8

```
git clone https://github.com/NEMPH/nem.core.git
cd nem.core
mvn clean install -DskipTests=true

git clone https://github.com/NEMPH/nem-apps-lib.git
cd nem-apps-lib
mvn clean install -DskipTests=true
```

Create a jar file and run, in the project directory:

```
mvn clean install
java -jar ./target/nem-jar-with-dependencies.jar
```
By default the screen already have an private key for TestNet 

![alt text](https://github.com/alexjavabraz/jNem/blob/master/src/main/resources/screen1.png)

Put your testnet nem address in field 'To' 

![alt text](https://github.com/alexjavabraz/jNem/blob/master/src/main/resources/screen2.png)

