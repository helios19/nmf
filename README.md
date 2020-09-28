Setup Instructions
--

**Fat-Jar**

In order to make this program easily portable and runnable, the decision has been made to build a fat-jar (encapsulating
the few needed dependencies). By doing so, this application can be executed easily using the methods described below.

To build this application, simply run the following gradle command in a terminal, at the root of the source folder to generate
the runnable fat jar:

```
./gradlew clean build
```

| Note that this application has been implemented with a JDK 12. In case of any compilation or runtime issue make sure to use a compatible local environment. |
| --- |


Then, to run the application, simply type:

```
java -jar <SOURCE_FOLDER>/build/libs/nmf-pricer-1.0.jar
```

Alternatively, you could also use the 'run' gradle task to execute the application (skipping the jar packaging altogether):

```
./gradlew run
```

After the application has started, the standard output should display the following log events corresponding to the stock prices registered by the consumer and processed by the StockProcessor from the Main class:

```
Sep. 28, 2020 8:01:23 PM com.nmf.assessment.q1.solution.consumer.impl.StockConsumerExample receive
INFO: Events received : [StockEventResult(time=2020-09-28T10:01:23.777Z, symbol=IBM, price=124.64, bids=[[124.56, 2571.0]], asks=[[124.58, 4832.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.761Z, symbol=XOM, price=40.88, bids=[[40.77, 4821.0]], asks=[[40.79, 6652.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.761Z, symbol=JNJ, price=152.06, bids=[[151.99, 380.0]], asks=[[152.01, 2799.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.761Z, symbol=NKE, price=111.51, bids=[[111.43, 5674.0]], asks=[[111.45, 4123.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.760Z, symbol=DIS, price=129.79, bids=[[129.71, 1150.0]], asks=[[129.73, 7645.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:23.850691Z, symbol=IBM, price=124.64, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:23.850761Z, symbol=XOM, price=40.88, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:23.850810Z, symbol=JNJ, price=152.06, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:23.850862Z, symbol=NKE, price=111.51, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:23.850910Z, symbol=DIS, price=129.79, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000))]
Sep. 28, 2020 8:01:25 PM com.nmf.assessment.q1.solution.consumer.impl.StockConsumerExample receive
INFO: Events received : [StockEventResult(time=2020-09-28T10:01:22.767Z, symbol=IBM, price=124.64, bids=[[124.6, 1806.0]], asks=[[124.62, 7173.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.767Z, symbol=XOM, price=40.88, bids=[[40.95, 593.0]], asks=[[40.97, 4802.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.767Z, symbol=JNJ, price=152.06, bids=[[152.06, 3696.0]], asks=[[152.08, 644.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.767Z, symbol=NKE, price=111.51, bids=[[111.44, 7836.0]], asks=[[111.46, 2034.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.767Z, symbol=DIS, price=129.79, bids=[[129.82, 3308.0]], asks=[[129.84, 1001.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:25.002321Z, symbol=IBM, price=124.64, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:25.002368Z, symbol=XOM, price=40.88, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:25.002407Z, symbol=JNJ, price=152.06, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:25.002443Z, symbol=NKE, price=111.51, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:25.002480Z, symbol=DIS, price=129.79, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000))]
Sep. 28, 2020 8:01:26 PM com.nmf.assessment.q1.solution.consumer.impl.StockConsumerExample receive
INFO: Events received : [StockEventResult(time=2020-09-28T10:01:22.777Z, symbol=IBM, price=124.64, bids=[[124.63, 9271.0]], asks=[[124.65, 8947.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.777Z, symbol=XOM, price=40.88, bids=[[40.81, 521.0]], asks=[[40.83, 1992.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.777Z, symbol=JNJ, price=152.06, bids=[[152.14, 2526.0]], asks=[[152.16, 6068.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.777Z, symbol=NKE, price=111.51, bids=[[111.59, 6822.0]], asks=[[111.61, 3922.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:22.777Z, symbol=DIS, price=129.79, bids=[[129.69, 3899.0]], asks=[[129.71, 6599.0]], strategy=StockEventStrategy(stockEventType=LATEST_PRICES, periodSeconds=10000)), StockEventResult(time=2020-09-28T10:01:26.005590Z, symbol=IBM, price=124.64, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:26.005665Z, symbol=XOM, price=40.88, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:26.005691Z, symbol=JNJ, price=152.06, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:26.005712Z, symbol=NKE, price=111.51, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000)), StockEventResult(time=2020-09-28T10:01:26.005736Z, symbol=DIS, price=129.79, bids=null, asks=null, strategy=StockEventStrategy(stockEventType=HIGHEST_PRICES, periodSeconds=20000))]

```


Design decisions and other considerations
--

This application has been designed with the following considerations in mind:
- Concurrency
- Memory efficiency
- Speed and throughput
- GC 

Indeed, in order to address the above constraints several design decisions have been taken such as:

- Custom implementation of a Circular Buffer to hold the values received from the Price Providers. This approach provides
several benefits among, decoupling entirely consumers from Price Providers so that the latter are not directly impacted by
any issue that could arise on the Providers side. The prices received are stored in a lean fashion and actually
stored in a concurrent hashmap holding a de-correlated and specialized Circular Buffer per stock.
- A set of CAS lock implementations (StampedLock) has been used so that the related Stock Queues (backed by a concurrent hashmap holding 
several Circular Buffer per stock) can be used concurrently and thread-safely manipulated by several readers while allowing only one writer at a time
when adding stock price to this structure.
- ForkJoinPool and related tasks so that this application can benefit from the stealing-work algorithm and increase its overall performance.
Note that each event strategy a consumer can register to (i.e latest, highest or lowest prices) is implemented in its separated
RecursiveTask class and executed by the ForkJoinPool class.
- A set of utils classes such as Tuples (instead of StockPrice objects) has also been used along with some other recycling mechanisms to re-use 
already created StockPrice objects in order to minimize the JVM memory footprint and GC cycles.

This application has also been implemented in a way to be as readable, concise, testable and maintainable as possible. Hence, the following
coding principles and paradigms have been followed:

- SOLID
- YAGNI
- DRY
- TDD/BDD

Finally, note that the following additional third-party libraries have been used to apply some of the above coding practices:

- Lombok (to reduce Java boilerplate code)
- Gradle (application scaffolding, build and execution using groovy scripting language)
- JUnit 5 (latest methods available on the Jupiter package)
- BDD test style (given/when/then)
- Checkstyle / PMD / Findbugs (code quality)
