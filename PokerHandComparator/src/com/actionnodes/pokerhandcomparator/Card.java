/**
 * @author Arkady Koplyarov (arkad.k@gmail.com)
 */

package com.actionnodes.pokerhandcomparator;

/**
 * Card
 */
public class Card
{
	Suits _suit = Suits.UNKNOWN;
	Ranks _rank = Ranks.UNKNOWN;
	
	/**
	 * Constructor
	 * 
	 * @param suit
	 * @param rank
	 */
	public Card(Suits suit, Ranks rank) {
		if (Deck.getInstance().isAvailable(suit, rank)) {
			_suit = suit;
			_rank = rank;
			Deck.getInstance().setAllocated(suit, rank);
		} else {
			throw new IllegalStateException("Card "+suit.name()+"-"+rank.name()+" is not available.");
		}
	}
}




