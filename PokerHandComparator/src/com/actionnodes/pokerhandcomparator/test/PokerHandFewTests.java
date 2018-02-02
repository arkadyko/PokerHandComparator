/**
 * @author Arkady Koplyarov (arkad.k@gmail.com)
 */

package com.actionnodes.pokerhandcomparator.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.actionnodes.pokerhandcomparator.Card;
import com.actionnodes.pokerhandcomparator.Ranks;
import com.actionnodes.pokerhandcomparator.Suits;
import com.actionnodes.pokerhandcomparator.Deck;
import com.actionnodes.pokerhandcomparator.PokerHand;

/**
 * PokerHandFewTests
 * This test class compares only some combinations of pairs of poker hands.
 * For comprehensive testing see the PokerHandComprehensiveTests class.
 */
public class PokerHandFewTests
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Deck.setDUPLICATE_CARDS_NOT_ALLOWED(true);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Test
	public void cardNotAvailable() {
		if (Deck.isDUPLICATE_CARDS_NOT_ALLOWED()) {
			exception.expect(IllegalStateException.class);
			String msgPart = "is not available";
			exception.expectMessage( msgPart );
			
			Deck.getInstance().setFreedAll();
			new Card(Suits.S, Ranks.Tn);
			// This must throw IllegalStateException with a message containing "is not available".
			new Card(Suits.S, Ranks.Tn);
		} else {
			// passed
		}
	}	
	
	
	@Test
	public void straightFlush_vs_straightFlush() {
		Deck.getInstance().setFreedAll();
		PokerHand h1 = new PokerHand(
				new Card(Suits.S, Ranks.A), 
				new Card(Suits.S, Ranks.K), 
				new Card(Suits.S, Ranks.Q), 
				new Card(Suits.S, Ranks.J), 
				new Card(Suits.S, Ranks.Tn) );
		PokerHand h2 = new PokerHand(
				new Card(Suits.D, Ranks.K), 
				new Card(Suits.D, Ranks.Q), 
				new Card(Suits.D, Ranks.J), 
				new Card(Suits.D, Ranks.Tn), 
				new Card(Suits.D, Ranks.N) );
		
		int result = PokerHand.compareHands(h1, h2);
		if (result < 0) {
			fail("less");
		} else if (result > 0) {
			// greater, passed
		} else { 
			fail("equal");
		}
	}

	@Test
	public void straightFlush_vs_fourOfKind() {
		Deck.getInstance().setFreedAll();
		PokerHand h1 = new PokerHand(
				new Card(Suits.S, Ranks.Tn), 
				new Card(Suits.S, Ranks.N), 
				new Card(Suits.S, Ranks.E), 
				new Card(Suits.S, Ranks.Sv), 
				new Card(Suits.S, Ranks.Sx) );
		PokerHand h2 = new PokerHand(
				new Card(Suits.C,Ranks.A), 
				new Card(Suits.D,Ranks.A), 
				new Card(Suits.H,Ranks.A), 
				new Card(Suits.S,Ranks.A), 
				new Card(Suits.D,Ranks.Tr) );
		
		int result = PokerHand.compareHands(h1, h2);
		if (result < 0) {
			fail("less");
		} else if (result > 0) {
			// greater, passed
		} else { 
			fail("equal");
		}
	}

	@Test
	public void fourOfKind_vs_fourOfKind() {
		Deck.getInstance().setFreedAll();
		PokerHand h1 = new PokerHand(
				new Card(Suits.C,Ranks.A), 
				new Card(Suits.D,Ranks.A), 
				new Card(Suits.H,Ranks.A), 
				new Card(Suits.S,Ranks.A), 
				new Card(Suits.S,Ranks.Tr) );
		PokerHand h2 = new PokerHand(
				new Card(Suits.C,Ranks.D), 
				new Card(Suits.D,Ranks.D), 
				new Card(Suits.H,Ranks.D), 
				new Card(Suits.S,Ranks.D), 
				new Card(Suits.D,Ranks.Tr) );
		
		int result = PokerHand.compareHands(h1, h2);
		if (result < 0) {
			fail("less");
		} else if (result > 0) {
			// greater, passed
		} else { 
			fail("equal");
		}
	}

	@Test
	public void fullHouse_vs_fullHouse() {
		Deck.getInstance().setFreedAll();
		boolean duplicateNotAllowed = Deck.isDUPLICATE_CARDS_NOT_ALLOWED();
		Deck.setDUPLICATE_CARDS_NOT_ALLOWED(false);
		PokerHand h1 = new PokerHand(
				new Card(Suits.C,Ranks.A), 
				new Card(Suits.D,Ranks.A), 
				new Card(Suits.H,Ranks.A), 
				new Card(Suits.H,Ranks.D), 
				new Card(Suits.S,Ranks.D) );
		PokerHand h2 = new PokerHand(
				new Card(Suits.C,Ranks.A), 
				new Card(Suits.D,Ranks.A), 
				new Card(Suits.H,Ranks.A), 
				new Card(Suits.S,Ranks.Q), 
				new Card(Suits.D,Ranks.Q) );
		
		int result = PokerHand.compareHands(h1, h2);
		if (result < 0) {
			// less, passed
		} else if (result > 0) {
			fail("greater");
		} else { 
			fail("equal");
		}
		Deck.setDUPLICATE_CARDS_NOT_ALLOWED(duplicateNotAllowed);
	}

	@Test
	public void flush_vs_Flush() {
		Deck.getInstance().setFreedAll();
		PokerHand h1 = new PokerHand(
				new Card(Suits.C,Ranks.A), 
				new Card(Suits.C,Ranks.K), 
				new Card(Suits.C,Ranks.Tn), 
				new Card(Suits.C,Ranks.N), 
				new Card(Suits.C,Ranks.E) );
		PokerHand h2 = new PokerHand(
				new Card(Suits.D,Ranks.A), 
				new Card(Suits.D,Ranks.K), 
				new Card(Suits.D,Ranks.Q), 
				new Card(Suits.D,Ranks.Sv), 
				new Card(Suits.D,Ranks.Sx) );
		
		int result = PokerHand.compareHands(h1, h2);
		if (result < 0) {
			// less, passed
		} else if (result > 0) {
			fail("greater");
		} else { 
			fail("equal");
		}
	}

	@Test
	public void highCard_vs_HighCard() {
		Deck.getInstance().setFreedAll();
		PokerHand h1 = new PokerHand(
				new Card(Suits.C,Ranks.A), 
				new Card(Suits.C,Ranks.K), 
				new Card(Suits.C,Ranks.Tn), 
				new Card(Suits.H,Ranks.N), 
				new Card(Suits.C,Ranks.Sv) );
		PokerHand h2 = new PokerHand(
				new Card(Suits.D,Ranks.A), 
				new Card(Suits.D,Ranks.K), 
				new Card(Suits.S,Ranks.Tn), 
				new Card(Suits.S,Ranks.N), 
				new Card(Suits.C,Ranks.E) );
		
		int result = PokerHand.compareHands(h1, h2);
		if (result < 0) {
			// less, passed
		} else if (result > 0) {
			fail("greater");
		} else { 
			fail("equal");
		}
	}

	@Test
	public void threeOfKind_vs_ThreeOfKind() {
		Deck.getInstance().setFreedAll();
		boolean duplicateNotAllowed = Deck.isDUPLICATE_CARDS_NOT_ALLOWED();
		Deck.setDUPLICATE_CARDS_NOT_ALLOWED(false);
		PokerHand h1 = new PokerHand(
				new Card(Suits.C,Ranks.Tr), 
				new Card(Suits.D,Ranks.Tr), 
				new Card(Suits.S,Ranks.Tr), 
				new Card(Suits.H,Ranks.N), 
				new Card(Suits.C,Ranks.Sv) );
		PokerHand h2 = new PokerHand(
				new Card(Suits.C,Ranks.Tr), 
				new Card(Suits.D,Ranks.Tr), 
				new Card(Suits.S,Ranks.Tr), 
				new Card(Suits.S,Ranks.N), 
				new Card(Suits.C,Ranks.E) );
		
		int result = PokerHand.compareHands(h1, h2);
		if (result < 0) {
			// less, passed
		} else if (result > 0) {
			fail("greater");
		} else { 
			fail("equal");
		}
		Deck.setDUPLICATE_CARDS_NOT_ALLOWED(duplicateNotAllowed);
	}
	
	@Test
	public void twoPair_vs_TwoPair() {
		Deck.getInstance().setFreedAll();
		//boolean duplicateNotAllowed = Deck.isDUPLICATE_CARDS_NOT_ALLOWED();
		//Deck.setDUPLICATE_CARDS_NOT_ALLOWED(false);
		PokerHand h1 = new PokerHand(
				new Card(Suits.S,Ranks.Tr), 
				new Card(Suits.H,Ranks.Tr), 
				new Card(Suits.C,Ranks.N), 
				new Card(Suits.D,Ranks.N), 
				new Card(Suits.C,Ranks.Sv) );
		PokerHand h2 = new PokerHand(
				new Card(Suits.C,Ranks.Tr), 
				new Card(Suits.D,Ranks.Tr), 
				new Card(Suits.S,Ranks.N), 
				new Card(Suits.H,Ranks.N), 
				new Card(Suits.C,Ranks.E) );
		
		int result = PokerHand.compareHands(h1, h2);
		if (result < 0) {
			// less, passed
		} else if (result > 0) {
			fail("greater");
		} else { 
			fail("equal");
		}
		//Deck.setDUPLICATE_CARDS_NOT_ALLOWED(duplicateNotAllowed);
	}

	@Test
	public void onePair_vs_OnePair() {
		Deck.getInstance().setFreedAll();
		//boolean duplicateNotAllowed = Deck.isDUPLICATE_CARDS_NOT_ALLOWED();
		//Deck.setDUPLICATE_CARDS_NOT_ALLOWED(false);
		PokerHand h1 = new PokerHand(
				new Card(Suits.S,Ranks.Tr), 
				new Card(Suits.H,Ranks.Tr), 
				new Card(Suits.C,Ranks.A), 
				new Card(Suits.D,Ranks.K), 
				new Card(Suits.C,Ranks.Sv) );
		PokerHand h2 = new PokerHand(
				new Card(Suits.C,Ranks.Tr), 
				new Card(Suits.D,Ranks.Tr), 
				new Card(Suits.S,Ranks.A), 
				new Card(Suits.H,Ranks.K), 
				new Card(Suits.C,Ranks.E) );
		
		int result = PokerHand.compareHands(h1, h2);
		if (result < 0) {
			// less, passed
		} else if (result > 0) {
			fail("greater");
		} else { 
			fail("equal");
		}
		//Deck.setDUPLICATE_CARDS_NOT_ALLOWED(duplicateNotAllowed);
	}
	
	@Test
	public void threeOfKind_vs_TwoPair() {
		Deck.getInstance().setFreedAll();
		boolean duplicateNotAllowed = Deck.isDUPLICATE_CARDS_NOT_ALLOWED();
		Deck.setDUPLICATE_CARDS_NOT_ALLOWED(false);
		
		//   A  A  A  K  Q    Category K3   Three of a Kind
		PokerHand h1 = new PokerHand(new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.Q));
		//
		//   A  A  K  K  Q    Category P2   Two Pair	
		PokerHand h2 = new PokerHand(new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.Q));
		
		int result = PokerHand.compareHands(h1, h2);
		if (result < 0) {
			fail("less");
		} else if (result > 0) {
			// greater, passed
		} else { 
			fail("equal");
		}
		Deck.setDUPLICATE_CARDS_NOT_ALLOWED(duplicateNotAllowed);
	}
	
	

}



