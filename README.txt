Two main design principles are considered in the coding task implementation:

1. Keep it as simple as possible.
2. Functional programming for concise representation.

Real production code had to be much more complicated for various reasons:

1. More rich API for items management - removing items, list items with prices for receipts etc.
   Basically, these additional API methods aren't hard to add, just some additional usual code.

2. Some management of measurement units and currencies may be required.
   In this implementation such things are just delegated to higher layers -
   this component just does its work for total cost checkout computations.

3. Error handling if price for some items aren't found in price list -
   this implementation just supposes that it shouldn' be possible.

4. Some cache of prices could be added - here such cache is delegated to price look-up mechanism.

Author:

Sergiy Shcherbyna

sergey.scherbina@gmail.com