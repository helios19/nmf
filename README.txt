provided:
The code is based around two stock price providers and these are the only classes you are expected to need to interact with:
1) LegacyPriceProvider - polling mechanism with non-thread safe behaviour. Returns price objects on the caller's thread
2) CallbackPriceProvider - callback mechanism on its own thread.
   - you should not need to amend any code in this package.

Solution:
   - Add your code here as answers to the below questions.
	
	
Add methods and classes as you see fit to demonstrate solutions to the following. It's not required, but if you feel that some 3rd party library is appropriate, then you may also use that.

1. Write a class which consumes and consolidates both price feeds and allows the data to be supplied (in a thread safe manner) to subscribers. For completeness it is expected you provide a subscriber implementation or client of your processor class to output the results. Output can be as simple as logging to standard output.

2. The feeds are not time synchronized, filter out any stale prices which come through either feed. I.e., you've seen a more recent price for the same stock on the other provider.

3. Provide a mechanism for clients of your class to receive sampled data at a specified time interval. For example allow subscribers to only see the latest price for a stock at X second intervals, where X is supplied by the client.

4. Calculate some technical measures of your choosing for each stock price. Which again, a client of your class can subscribe to and observe. For example, high and low prices over a period, moving averages, volatility over time and so on.
   If you are not familiar with any of these measures the definitions should be easily found via Google / Wikipedia or similar

5. You are encouraged to comment on any design or code considerations you encountered as you construct your solution. Concurrency, performance, garbage creation, code readability and extensibility are all reasonable.


*If you are not familiar with any of these measures the definitions should be easily found via Google / Wikipedia or similar. For reference 

- best bid = the highest price someone is offering to pay for this stock.
- bid volume = the number of shares which are sought at the bid price.
- best ask = the lowest price someone is offering to sell this stock for.
- ask volume = the number of shares which are offered at the sell price.
- last = the last transacted price for the stock.


Note: You are encouraged to comment on any design or code considerations you encountered as you construct your solution. Concurrency, performance, garbage creation, code readability and extensibility are all reasonable. It's not required, but if you feel that some 3rd party library is appropriate, then you may also use that.


Solution folder:
   - Add your code here as answers to the below questions.