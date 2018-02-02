/**
 * @author Arkady Koplyarov (arkad.k@gmail.com)
 */

package com.actionnodes.pokerhandcomparator;

import java.util.HashMap;

import com.actionnodes.pokerhandcomparator.Ranks;
import com.actionnodes.pokerhandcomparator.Suits;


/**
 * Deck
 */
public class Deck
{
	///// Singleton implementation. /////
	private Deck() {
	}
	private static Deck instance = null;
	public static Deck getInstance() {
		if (instance == null) {
			instance = new Deck();
		}
		return instance;
	}
	/////
	
	/**
	 * This flag controls if it is possible for two or more players to obtain the same hand
	 * like in community-card games (such as Texas Hold 'em) or games with wildcards or multiple decks.
	 * We set its default value to not allow duplicate hands.
	 */
	private static boolean DUPLICATE_CARDS_NOT_ALLOWED = true;
	public static boolean isDUPLICATE_CARDS_NOT_ALLOWED() {
		return DUPLICATE_CARDS_NOT_ALLOWED;
	}
	public static void setDUPLICATE_CARDS_NOT_ALLOWED(boolean notAllowed) {
		DUPLICATE_CARDS_NOT_ALLOWED = notAllowed;
	}
	
	
	// Data representation.
	private HashMap<String,Card> _cardsUnavailable = new HashMap<String,Card>();
	
	
	// Method implementation.
	public Card setAllocated(Card card) {
		return _cardsUnavailable.put(card._suit.name()+card._rank.name(), card);
	}
	public Card setAllocated(Suits suit, Ranks rank) {
		return _cardsUnavailable.put(suit.name()+rank.name(), null);
	}
	public Card setFreed(Card card) {
		return _cardsUnavailable.remove(card._suit.name()+card._rank.name());
	}
	public void setFreedAll() {
		_cardsUnavailable.clear();
	}
	public boolean isAvailable(Card card) {
		return isAvailable(card._suit, card._rank);
	}
	public boolean isAvailable(Suits suit, Ranks rank) {
		if (DUPLICATE_CARDS_NOT_ALLOWED) {
			if (_cardsUnavailable.containsKey(suit.name()+rank.name())) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}
