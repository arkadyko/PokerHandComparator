# PokerHandComparator
A solution to compare two 5-card poker hands.

THE SOLUTION

The solution is implemented as the PokerHand class.

AN ALTERNATIVE SOLUTION

Besides the working solution, a draft implementation of an alternative solution is also provided (the PokerHandEquivClassIDsIdea class) that must be faster and much simpler than the implemented PokerHand class. But this alternative solution requires first to fill out the array with all 7462 numerical identificators for all poker hand equivalence classes, calculating them manually or by executing my primary solution, the PokerHand class. So, it would be either uninteresting manual work or dependent on the primary working solution results. However, the alternative solution eventually must be faster, simpler and more reliable, than the primary working solution.

THE TESTS

There are test classes in the test folder, to test the solution. 
The provided tests are consistent (they generate identical results every time I run them).
The provided tests produce some basic machine-readable output that would allow the tests to be part of a continuous integration build system.
The test class, PokerHandComprehensiveTests, provides more or less comprehensive testing of poker hand pairs comparison in all possible combinations of hand categories (though not all combinations of pairs of hands), from Straight Flush category to High Card category.
Another test class, PokerHandFewTests, has been written and used on initial stage of the solution development as sample testing and is not quite comprehensive.
