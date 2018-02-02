/**
 * @author Arkady Koplyarov (arkad.k@gmail.com)
 */

package com.actionnodes.pokerhandcomparator;

/**
 * This class is just to indicate an idea of an alternative solution
 * to compare two 5-card poker hands
 * that must be faster and much simpler than the implemented PokerHand class.
 * 
 * But this solution requires first to calculate and fill out the array with 
 * all 7462 numerical identificators for all poker hand equivalence classes.
 */
public class PokerHandEquivClassIDsIdea
{
	//TODO: Fill out the HandEquivalenceClasseRankingIDs array with all 7462 
	//      numerical identificators for all poker hand equivalence classes, 
	//      to provide the alternative implementation.
	
	/**
	 * This array of 7462 long values represents the idea of an alternative implementation 
	 * that should work faster than the current implementation of the 
	 * PokerHand class.
	 * This is an array of 7462 numerical identificators of poker hand equivalence classes, 
	 * placed in ranking order. 
	 * Any single element of this array 
	 * represents and identifies a particular hand ranking equivalence class 
	 * and may be used as a key for poker hand comparison. 
	 * In a single element of the array, 
	 * every 4 bits (a single hexadecimal digit) represent a number of cards of a particular rank 
	 * (Ace,King,Queen,Jack,Ten,Nine,Eight,Seven,Six,Five,Four,Trey,Deuce), in the hand.
	 * Note, this functionality cannot be provided by use of java.util.EnumSet.
	 */
	public static final long HandEquivalenceClasseRankingIDs[] = 
	{
		0x0001111100000000L, // Rank:    1   Hand: A, K, Q, J, Tn, same Suit,  Category: SF (Royal Straight Flush)
		0x0000111110000000L, // Rank:    2   Hand: K, Q, J, Tn,N,  same Suit,  Category: SF (Straight Flush)
		0x0000011111000000L, // Rank:    3   Hand: Q, J, Tn,N, E,  same Suit,  Category: SF (Straight Flush)
		// ... ... ...
		0x0004100000000000L, // Rank:   11   Hand: A, A, A, A, K               Category: K4 (Four of a Kind)
		0x0004010000000000L, // Rank:   12   Hand: A, A, A, A, Q               Category: K4 (Four of a Kind)
		0x0004001000000000L, // Rank:   13   Hand: A, A, A, A, J               Category: K4 (Four of a Kind)
		// ... ... ...
		// ... TODO all 7462 numerical identificators ...
		// ... ... ...
		// This is the last hand ranking sets:
		0x0000000000110111L, // Rank: 7461   Hand: Sv,Sx,Fr,Tr,D               Category: HC (High Card)
		0x0000000000101111L  // Rank: 7462   Hand: Sv,Fv,Fr,Tr,D               Category: HC (High Card)
	};
	
	
	/** 
	 * This long-based "bit flags" member
	 * represents and identifies a particular hand ranking equivalence class.  
	 * Every 4 bits (a single hexadecimal digit) represent a number of cards of a particular rank
	 * (Ace,King,Queen,Jack,Ten,Nine,Eight,Seven,Six,Five,Four,Trey,Deuce), in the hand.
	 * Note, this functionality cannot be provided by use of java.util.EnumSet.
	 */
	private long _handCardRankingSum = 0x0000000000000000L;
	
	private Card _cards[] = null;
	
	/**
	 * A hand always consists of five cards.
	 */
	private static final int CARDS_IN_HAND = 5;
	
	
	/**
	 * Constructor
	 */
	public PokerHandEquivClassIDsIdea(Card card1, Card card2, Card card3, Card card4, Card card5)
	{
		_cards = new Card[CARDS_IN_HAND];
		_cards[0] = card1;
		_cards[1] = card2;
		_cards[2] = card3;
		_cards[3] = card4;
		_cards[4] = card5;
		
		// Get the hand card-ranking bits.
		for (int i=0; i<CARDS_IN_HAND; i++) {
			_handCardRankingSum += _cards[i]._rank.getValue();
		}
	}
	
	/**
	 * It compares this PokerHand with the specified PokerHand for order. 
	 * 
	 * @param h1 Poker hand #1.
	 * @param h2 Poker hand #2.
	 * @return A negative integer, zero, or a positive integer 
	 * as the 1st PokerHand is less than, equal to, or greater than the 2nd PokerHand.
	 */
	public static int compareHands(PokerHandEquivClassIDsIdea h1, PokerHandEquivClassIDsIdea h2)
	{
		int h1Rank = -1;
		int h2Rank = -1;
		for (int i=0; i<HandEquivalenceClasseRankingIDs.length; i++) {
			if (h1._handCardRankingSum == HandEquivalenceClasseRankingIDs[i]) {
				h1Rank = i;
			}
			if (h2._handCardRankingSum == HandEquivalenceClasseRankingIDs[i]) {
				h2Rank = i;
			}
		}
		
		return 
				h1Rank < h2Rank ? 1 
				: h1Rank == h2Rank ? 0 
				: -1;
	}
	
}






