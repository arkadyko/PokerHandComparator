/**
 * @author Arkady Koplyarov (arkad.k@gmail.com)
 */

package com.actionnodes.pokerhandcomparator;

import java.util.Comparator;

import com.actionnodes.pokerhandcomparator.Ranks;
import com.actionnodes.pokerhandcomparator.Suits;


/**
 * PokerHand
 */
public class PokerHand
	implements Comparator<PokerHand>, Comparable<PokerHand> 
{
	/** 
	 * This long-based "bit flags" member
	 * represents and identifies a particular hand ranking equivalence class.  
	 * Every 4 bits (a single hexadecimal digit) represent a number of cards of a particular rank
	 * (Ace,King,Queen,Jack,Ten,Nine,Eight,Seven,Six,Five,Four,Trey,Deuce), in the hand.
	 * Note, this functionality cannot be provided by use of java.util.EnumSet.
	 */
	private long _handCardRankingSum = 0x0000000000000000L;
	
	private Card _cards[] = null;
	private Suits _handSuit = Suits.UNKNOWN;
	private Categories _category = Categories.UNKNOWN;
	private Ranks _cardRanks[];
	
	/**
	 * A hand always consists of five cards.
	 */
	private static final int CARDS_IN_HAND = 5;
	
	/** 
	 * The hand comparator is combined by the key items which make the hand ranking order:
	 * 1) byte #8: the hand category value, _category, which is placed into 
	 *             the highest position and is followed by 
	 * 2) byte #7: a category-dependent value like _cardRank, which is followed by
	 * 2) byte #6: a category-dependent value like _cardRank2, which is followed by
	 * 3) bytes #5 to #0: one or more kicker values.
	 */
	private long _comparator = 0x0000000000000000L;
	
	
	/**
	 * Constructor
	 */
	public PokerHand(Card card1, Card card2, Card card3, Card card4, Card card5)
	{
		_cards = new Card[CARDS_IN_HAND];
		_cards[0] = card1;
		_cards[1] = card2;
		_cards[2] = card3;
		_cards[3] = card4;
		_cards[4] = card5;
		
		_cardRanks = new Ranks[CARDS_IN_HAND];
		resetCardRanks();
		
		calcHandRankingCategory();
	}
	
	/**
	 * This method initialises this PokerHand instance by running the set of methods 
	 * checkForXXXXXX() and calculating its key members: 
	 *   _handCardRankingSum
	 *   _handSuit
	 *   _category
	 *   _comparator
	 * to make comparison of two 5-card poker hands a trivial task.
	 */
	private void calcHandRankingCategory() {
		
		// Get the hand card-ranking bits.
		for (int i=0; i<CARDS_IN_HAND; i++) {
			_handCardRankingSum += _cards[i]._rank.getValue();
		}
		
		// Get the hand common suit (if all 5 card are of the same suit).
		int handSuit = 0xF;
		for (int i=0; i<CARDS_IN_HAND; i++) {
			handSuit = handSuit & _cards[i]._suit.getValue();
		}
		_handSuit = Suits.checkValue(handSuit);
		
		// Calculate the hand ranking category and other ranking items.
		if (_category == Categories.UNKNOWN) checkForStraightFlushOrStraight();
		if (_category == Categories.UNKNOWN) checkForFlushOrHighCard();
		if (_category == Categories.UNKNOWN) checkForFourOfKind();
		if (_category == Categories.UNKNOWN) checkForFullHouse();
		if (_category == Categories.UNKNOWN) checkForThreeOfKind();
		if (_category == Categories.UNKNOWN) checkForTwoPair();
		if (_category == Categories.UNKNOWN) checkForOnePair();
		
		
		// Eventually, combine the hand comparator value from the calculated key items, 
		// which make the hand ranking order:
		_comparator = 0x0000000000000000L;
		_comparator |= ((long)_category.ordinal())<<(8*7); // byte #8
		_comparator |= ((long)_cardRanks[0].ordinal())<<(8*6); // byte #7 , default is Ranks.UNKNOWN ?!
		_comparator |= ((long)_cardRanks[1].ordinal())<<(8*5); // byte #6 , default is Ranks.UNKNOWN ?!
		_comparator |= ((long)_cardRanks[2].ordinal())<<(8*4); // byte #5 , default is Ranks.UNKNOWN ?!
		_comparator |= ((long)_cardRanks[3].ordinal())<<(8*3); // byte #4 , default is Ranks.UNKNOWN ?!
		_comparator |= ((long)_cardRanks[4].ordinal())<<(8*2); // byte #3 , default is Ranks.UNKNOWN ?!
	}
	
	
	/**
	 */
	private void resetCardRanks() {
		for (int i=0; i<_cardRanks.length; i++) {_cardRanks[i] = Ranks.UNKNOWN;}
	}
	
	
	/**
	 * It looks for a Straight Flush or a Straight
	 * (e.g. A,K,Q,J,Tn  which is represented by _handCardRankingSum as 0x0001111100000000L)
	 */
	private void checkForStraightFlushOrStraight()
	{
		resetCardRanks();
		// We start looking from the lowest rank, to see a number of cards of the rank (a hexadecimal digit).
		long handCardRankingSum = _handCardRankingSum;
		for (int i=0; i < Ranks.enumSize-(CARDS_IN_HAND-1); i++) {
			// Looking for 5 cards of 5 sequential ranks.
			// Starting search from Six-High Straight Flush (6 5 4 3 2), finishing at the Royal Straight Flush (A K Q J T)
			if ((handCardRankingSum & 0xFFFFFL) == 0x11111L) {
				// It is a Straight Flush or Straight.
				_category = _handSuit==Suits.UNKNOWN ? Categories.S : Categories.SF;
				// Set a Ranks enum value indicating the highest card rank in the detected Straight Flush.
				// Note, CARDS_IN_HAND is the number of cards constituting a Straight Flush.
				_cardRanks[0] = Ranks.getElemValueByIdx((CARDS_IN_HAND-1) + i);
				return;
			}
			// To see a number of cards of the next higher rank, we go to the next higher hexadecimal digit.
			handCardRankingSum = handCardRankingSum >>> 4;
		}
		// A special case is a Five-High Straight Flush (5 4 3 2 A)
		if ((_handCardRankingSum & 0x000F00000000FFFFL) == 0x0001000000001111L) {
			// It is a Straight Flush or Straight.
			_category = _handSuit==Suits.UNKNOWN ? Categories.S : Categories.SF;
			// Set a Ranks enum value indicating the highest card rank in the detected Straight Flush. 
			_cardRanks[0] = Ranks.Fv;
			return;
		}
		
		// It is NOT a Straight Flush and NOT a Straight.
		return;
	}
	/**
	 * It looks for a Flush or a High Card,
	 * The 5 cards to be all of different ranks, but not in sequence.
	 * (e.g. A,J,Tn,E,Sv  which is represented by _handCardRankingSum as 0x0001001101100000L)
	 */
	private void checkForFlushOrHighCard()
	{
		resetCardRanks();
		// Check if it's a Flush or a High Card.
		// Get cards' ranks.
		// We start looking from the lowest rank, to see a number of cards of the rank (a hexadecimal digit).
		long handCardRankingSum1 = _handCardRankingSum;
		boolean ranksNotInSequence = false;
		int prevRankIdx = -1;
		for (int i=0,j=CARDS_IN_HAND-1; i < Ranks.enumSize; i++) {
			// Looking for 5 cards to be all of different ranks, but not in sequence. 
			// And if all five cards are of the same suit then it's a Flush, else it's a HighCard.
			if ((handCardRankingSum1 & 0xFL) == 0x1L) {
				// Set a Ranks enum value indicating the card rank in the detected Flush. 
				_cardRanks[j--] = Ranks.getElemValueByIdx(i);
				// Check that the cards' ranks are not in sequence.
				if (prevRankIdx != (-1)) {
					if (prevRankIdx + 1 != i) ranksNotInSequence = true;
				}
				prevRankIdx = i;
			}
			// To see a number of cards of the next higher rank, we go to the next higher hexadecimal digit.
			handCardRankingSum1 = handCardRankingSum1 >>> 4;
		}
		boolean flushOrHighCard = ranksNotInSequence;
		// (Double)check if all 5 cards are of different ranks (i.e. we have 5 different ranks in the hand). 
		for (int i=0; i<_cardRanks.length; i++) {
			if (_cardRanks[i] == Ranks.UNKNOWN) {
				// No, 2 or more cards in the hand are of the same rank.
				flushOrHighCard = false;
			}
		}
		if (flushOrHighCard) {
			// It's a Flush or a High Card, 
			// because we have 5 different ranks in the hand, each card of a different rank.
			// Check if all 5 cards are of the same suit or of mixed suits.
			_category = _handSuit==Suits.UNKNOWN ? Categories.HC : Categories.F;
			
			return;
		} else {
			// It is NOT a Flush and NOT a HighCard.
			return;
		}
	}
	
	/**
	 * It looks for a Four of a Kind, 
	 * (e.g. A,A,A,A,J  which is represented by _handCardRankingSum as 0x0004001000000000L)
	 */
	private void checkForFourOfKind() 
	{
		resetCardRanks();
		// We start looking from the lowest rank, to see a number of cards of the rank (a hexadecimal digit).
		long handCardRankingSum = _handCardRankingSum;
		for (int i=0; i < Ranks.enumSize; i++) {
			// Looking for 4 cards of same rank.
			if ((handCardRankingSum & 0xFL) == 0x4L) {
				// It is a Four of a Kind.
				_category = Categories.K4;
				// Set a Ranks enum value indicating the card rank in the detected Four of a Kind. 
				_cardRanks[0] = Ranks.getElemValueByIdx(i);
				return;
			}
			// To see a number of cards of the next higher rank, we go to the next higher hexadecimal digit.
			handCardRankingSum = handCardRankingSum >>> 4;
		}
		
		// It is NOT a Four of a Kind.
		return;
	}
	
	/**
	 * It looks for a Full House, 
	 * (e.g. A,A,A,J,J  which is represented by _handCardRankingSum as 0x0003002000000000L)
	 */
	private void checkForFullHouse()
	{
		resetCardRanks();
		// We start looking for the Tree from the lowest rank, to see a number of cards of the rank (a hexadecimal digit).
		long handCardRankingSum = _handCardRankingSum;
		for (int i=0; i < Ranks.enumSize; i++) {
			// Looking for 3 cards of same rank.
			if ((handCardRankingSum & 0xFL) == 0x3L) {
				// It may be a Full House.
				// Set a Ranks enum value indicating the card rank in the detected Full House. 
				_cardRanks[0] = Ranks.getElemValueByIdx(i);
				break;
			}
			// To see a number of cards of the next higher rank, we go to the next higher hexadecimal digit.
			handCardRankingSum = handCardRankingSum >>> 4;
		}
		// 3 cards of same rank have been found at the previous step
		// Now we look for 2 cards of another rank.
		if (_cardRanks[0] != Ranks.UNKNOWN) {
			long handCardRankingSum2 = _handCardRankingSum;
			// We start looking for the Two from the lowest rank, to see a number of cards of the rank (a hexadecimal digit).
			for (int i=0; i < Ranks.enumSize; i++) {
				// Looking for 2 cards of same rank.
				if ((handCardRankingSum2 & 0xFL) == 0x2L) {
					// It is a Full House.
					_category = Categories.FH;
					// Set a Ranks enum value indicating the card rank #2 in the detected Full House. 
					_cardRanks[1] = Ranks.getElemValueByIdx(i);
					return;
				}
				// To see a number of cards of the next higher rank, we go to the next higher hexadecimal digit.
				handCardRankingSum2 = handCardRankingSum2 >>> 4;
			}
		}
		
		// It is NOT a Full House.
		return;
	}
	
	/**
	 * It looks for a Three of a Kind, 
	 * (e.g. A,A,A,J,Tn  which is represented by _handCardRankingSum as 0x0003001100000000L)
	 */
	private void checkForThreeOfKind()
	{
		resetCardRanks();
		// We start looking for the Tree from the lowest rank, to see a number of cards of the rank (a hexadecimal digit).
		long handCardRankingSum = _handCardRankingSum;
		for (int i=0; i < Ranks.enumSize; i++) {
			// Looking for 3 cards of same rank.
			if ((handCardRankingSum & 0xFL) == 0x3L) {
				// It may be a Three of a Kind.
				// Set a Ranks enum values indicating the card rank in the detected Three of a Kind. 
				_cardRanks[0] = Ranks.getElemValueByIdx(i);
				break;
			}
			// To see a number of cards of the next higher rank, we go to the next higher hexadecimal digit.
			handCardRankingSum = handCardRankingSum >>> 4;
		}
		if (_cardRanks[0] != Ranks.UNKNOWN) {
			// 3 cards of same rank have been found at the previous step.
			// Now we look for 2 cards of other 2 different ranks.
			// Get cards' ranks.
			// We start looking from the lowest rank, to see a number of cards of the rank (a hexadecimal digit).
			long handCardRankingSum1 = _handCardRankingSum;
			Ranks card1Rank = Ranks.UNKNOWN;
			Ranks card2Rank = Ranks.UNKNOWN;
			for (int i=0; i < Ranks.enumSize; i++) {
				// Looking for the 2 cards to be of 2 different ranks. 
				if ((handCardRankingSum1 & 0xFL) == 0x1L) {
					// Get Ranks enum values indicating the card ranks in the detected Three of a Kind. 
					if (card1Rank==Ranks.UNKNOWN) {
						card1Rank = Ranks.getElemValueByIdx(i);
					} else {
						if (card2Rank==Ranks.UNKNOWN) {
							card2Rank = Ranks.getElemValueByIdx(i);
							break;
						} else ; // Impossible.
					}
				}
				// To see a number of cards of the next higher rank, we go to the next higher hexadecimal digit.
				handCardRankingSum1 = handCardRankingSum1 >>> 4;
			}
			
			// Check if it's a Three of a Kind.
			if (card1Rank != Ranks.UNKNOWN && card2Rank != Ranks.UNKNOWN) {
				// It's a Three of a Kind.
				_category = Categories.K3;
			} else {
				// It's NOT a Three of a Kind.
				return;
			}
			// Set Ranks enum values indicating the card ranks of the 2 cards.
			if (card1Rank.compareTo(card2Rank) > 0) {
				_cardRanks[1] = card1Rank;
				_cardRanks[2] = card2Rank;
			} else if (card1Rank.compareTo(card2Rank) < 0) {
				_cardRanks[1] = card2Rank;
				_cardRanks[2] = card1Rank;
			} else ; // Impossible, as in this case it's a Four of a Kind.
		}
		
		// It is NOT a Three of a Kind.
		return;
	}
	
	/**
	 * It looks for a Two Pair, 
	 * (e.g. A,A,J,J,Tn  which is represented by _handCardRankingSum as 0x0002002100000000L)
	 */
	private void checkForTwoPair()
	{
		resetCardRanks();
		// We start looking for both Pairs from the lowest rank, to see a number of cards of the rank (a hexadecimal digit).
		long handCardRankingSum1 = _handCardRankingSum;
		Ranks cardPair1Rank = Ranks.UNKNOWN;
		Ranks cardPair2Rank = Ranks.UNKNOWN;
		for (int i=0; i < Ranks.enumSize; i++) {
			// Looking for 2 cards of same rank.
			if ((handCardRankingSum1 & 0xFL) == 0x2L) {
				// It may be a Two Pair.
				// Get Ranks enum values indicating the card ranks in the detected Two Pair. 
				if (cardPair1Rank==Ranks.UNKNOWN) {
					cardPair1Rank = Ranks.getElemValueByIdx(i);
				} else {
					if (cardPair2Rank==Ranks.UNKNOWN) {
						cardPair2Rank = Ranks.getElemValueByIdx(i);
						break;
					} else ; // Impossible.
				}
			}
			// To see a number of cards of the next higher rank, we go to the next higher hexadecimal digit.
			handCardRankingSum1 = handCardRankingSum1 >>> 4;
		}
		// Check if it's a Two Pair.
		if (cardPair1Rank != Ranks.UNKNOWN && cardPair2Rank != Ranks.UNKNOWN) {
			// It's a Two Pair.
			_category = Categories.P2;
		} else {
			// It's NOT a Two Pair.
			return;
		}
		// Set Ranks enum values indicating the card ranks of the 2 Pairs.
		if (cardPair1Rank.compareTo(cardPair2Rank) > 0) {
			_cardRanks[0] = cardPair1Rank;
			_cardRanks[1] = cardPair2Rank;
		} else if (cardPair1Rank.compareTo(cardPair2Rank) < 0) {
			_cardRanks[0] = cardPair2Rank;
			_cardRanks[1] = cardPair1Rank;
		} else ; // Impossible, as in this case it's a Four of a Kind.
		
		// 2 Pairs of cards of 2 ranks have been found at the previous step.
		// Now we look for 1 card of a different rank.
		// We start looking from the lowest rank, to see a number of cards of the rank (a hexadecimal digit).
		long handCardRankingSum2 = _handCardRankingSum;
		for (int i=0; i < Ranks.enumSize; i++) {
			// Looking for 1 card to be of a different rank. 
			if ((handCardRankingSum2 & 0xFL) == 0x1L) {
				// Set a Ranks enum value indicating the card rank. 
				_cardRanks[2] = Ranks.getElemValueByIdx(i);
				break;
			}
			// To see a number of cards of the next higher rank, we go to the next higher hexadecimal digit.
			handCardRankingSum2 = handCardRankingSum2 >>> 4;
		}
	}
	
	/**
	 * It looks for a One Pair, 
	 * (e.g. A,A,J,Tn,N  which is represented by _handCardRankingSum as 0x0002001110000000L)
	 */
	private void checkForOnePair() {
		resetCardRanks();
		// We start looking for the Two from the lowest rank, to see a number of cards of the rank (a hexadecimal digit).
		long handCardRankingSum = _handCardRankingSum;
		for (int i=0; i < Ranks.enumSize; i++) {
			// Looking for 2 cards of same rank.
			if ((handCardRankingSum & 0xFL) == 0x2L) {
				// It may be a OnePair.
				// Set a Ranks enum values indicating the card rank in the detected OnePair. 
				_cardRanks[0] = Ranks.getElemValueByIdx(i);
				break;
			}
			// To see a number of cards of the next higher rank, we go to the next higher hexadecimal digit.
			handCardRankingSum = handCardRankingSum >>> 4;
		}
		if (_cardRanks[0] != Ranks.UNKNOWN) {
			// 2 cards of same rank have been found at the previous step.
			// Now we look for 3 cards of other 3 different ranks.
			// Get cards' ranks.
			// We start looking from the lowest rank, to see a number of cards of the rank (a hexadecimal digit).
			long handCardRankingSum1 = _handCardRankingSum;
			for (int i=0; i < Ranks.enumSize; i++) {
				// Looking for the 3 cards to be of 3 different ranks. 
				if ((handCardRankingSum1 & 0xFL) == 0x1L) {
					// Get Ranks enum values indicating the card ranks in the detected OnePair. 
					// Remember, we are looping from the lowest rank to the highest rank.
					if (_cardRanks[3]==Ranks.UNKNOWN) {
						_cardRanks[3] = Ranks.getElemValueByIdx(i);
					} else {
						if (_cardRanks[2]==Ranks.UNKNOWN) {
							_cardRanks[2] = Ranks.getElemValueByIdx(i);
						} else {
							if (_cardRanks[1]==Ranks.UNKNOWN) {
								_cardRanks[1] = Ranks.getElemValueByIdx(i);
								break;
							} else ; // Must be impossible for OnePair.
						}
					}
				}
				// To see a number of cards of the next higher rank, we go to the next higher hexadecimal digit.
				handCardRankingSum1 = handCardRankingSum1 >>> 4;
			}
			// Check if it's a OnePair.
			if (_cardRanks[1] != Ranks.UNKNOWN && _cardRanks[2] != Ranks.UNKNOWN && _cardRanks[3] != Ranks.UNKNOWN) {
				// It's a OnePair.
				_category = Categories.P1;
			} else {
				// It's NOT a OnePair.
				return;
			}
		}
		
		// It is NOT a OnePair.
		return;
	}
	
	
	
	
	/**
	 * Compares this PokerHand with the specified PokerHand for order. 
	 * 
	 * @param h1 Poker hand #1.
	 * @param h2 Poker hand #2.
	 * @return A negative integer, zero, or a positive integer 
	 * as the 1st PokerHand is less than, equal to, or greater than the 2nd PokerHand.
	 */
	public static int compareHands(PokerHand h1, PokerHand h2) {
		return 
			h1._comparator < h2._comparator ? (-1) 
				: h1._comparator == h2._comparator ? 0 
				: 1;
	}
	
	
	/** @see java.util.Comparator<PokerHand> */
	@Override
	public int compare(PokerHand h1, PokerHand h2) {
		return compareHands(h1, h2);
	}
	
	/** @see java.lang.Comparable<PokerHand> */
	@Override
	public int compareTo(PokerHand h) {
		return compareHands(this, h);
	}
	
	
	/** @see java.lang.Object.toString() */
	public String toString() {
		String string = "";
		for (int i=0; i<CARDS_IN_HAND; i++) {
			string = string + " "+_cards[i]._suit.name() +"-"+ _cards[i]._rank.name()+" "; 
		}
		return string;
	}
}

