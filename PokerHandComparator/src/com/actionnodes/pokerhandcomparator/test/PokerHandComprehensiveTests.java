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
 * PokerHandComprehensiveTests
 * This test class compares pairs of poker hands in all possible combinations 
 * of poker hand categories from Straight Flush category to High Card category.
 */
public class PokerHandComprehensiveTests
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Deck.setDUPLICATE_CARDS_NOT_ALLOWED(false);
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
	
	
	private PokerHand hands[] = 
	{
		//   A  K  Q  J  Tn   Category SF   (Royal) Straight Flush
		new PokerHand(new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.Q), new Card(Suits.S, Ranks.J), new Card(Suits.S, Ranks.Tn)),
		//   Fv Fr Tr D  A    Category SF   Straight Flush
		new PokerHand(new Card(Suits.S, Ranks.Fv), new Card(Suits.S, Ranks.Fr), new Card(Suits.S, Ranks.Tr), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.A)),
		//
		//   A  A  A  A  K    Category K4   Four of a Kind
		new PokerHand(new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.K)),
		//   D  D  D  D  Tr   Category K4   Four of a Kind
		new PokerHand(new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.Tr)),
		// 
		//   A  A  A  K  K    Category FH   Full House	
		new PokerHand(new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.K)),
		//   D  D  D  Tr Tr   Category FH   Full House
		new PokerHand(new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.Tr), new Card(Suits.S, Ranks.Tr)),
		//
		//   A  K  Q  J  N    Category F    Flush
		new PokerHand(new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.Q), new Card(Suits.S, Ranks.J), new Card(Suits.S, Ranks.N)),
		//   Sv Fv Fr Tr D    Category F    Flush
		new PokerHand(new Card(Suits.S, Ranks.Sv), new Card(Suits.S, Ranks.Fv), new Card(Suits.S, Ranks.Fr), new Card(Suits.S, Ranks.Tr), new Card(Suits.S, Ranks.D)),
		//
		//   A  K  Q  J  Tn   Category S    Straight	
		new PokerHand(new Card(Suits.H, Ranks.A), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.Q), new Card(Suits.S, Ranks.J), new Card(Suits.S, Ranks.Tn)),
		//   Fv Fr Tr D  A    Category S    Straight
		new PokerHand(new Card(Suits.H, Ranks.Fv), new Card(Suits.S, Ranks.Fr), new Card(Suits.S, Ranks.Tr), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.A)),
		//
		//   A  A  A  K  Q    Category K3   Three of a Kind
		new PokerHand(new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.Q)),
		//   D  D  D  Fr Tr   Category K3   Three of a Kind
		new PokerHand(new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.Fr), new Card(Suits.S, Ranks.Tr)),
		//
		//   A  A  K  K  Q    Category P2   Two Pair	
		new PokerHand(new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.Q)),
		//   Tr Tr D  D  Fr   Category P2   Two Pair
		new PokerHand(new Card(Suits.S, Ranks.Tr), new Card(Suits.S, Ranks.Tr), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.Fr)),
		//
		//   A  A  K  Q  J    Category P1   One Pair	
		new PokerHand(new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.A), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.Q), new Card(Suits.S, Ranks.J)),
		//   D  D  Fv Fr Tr   Category P1   One Pair
		new PokerHand(new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.D), new Card(Suits.S, Ranks.Fv), new Card(Suits.S, Ranks.Fr), new Card(Suits.S, Ranks.Tr)),
		//
		//   A  K  Q  J  N    Category HC   High Card	
		new PokerHand(new Card(Suits.H, Ranks.A), new Card(Suits.S, Ranks.K), new Card(Suits.S, Ranks.Q), new Card(Suits.S, Ranks.J), new Card(Suits.S, Ranks.N)),
		//   Sv Fv Fr Tr D    Category HC   High Card	
		new PokerHand(new Card(Suits.H, Ranks.Sv), new Card(Suits.S, Ranks.Fv), new Card(Suits.S, Ranks.Fr), new Card(Suits.S, Ranks.Tr), new Card(Suits.S, Ranks.D))
	};
	
	
	/**
	 * This test method compares pairs of poker hands in all possible combinations 
	 * of poker hand categories from Straight Flush category to High Card category.
	 */
	@Test
	public void crossCategoryTest()
	{
		for (int i = 0; i < hands.length; i++)
		{
			for (int j = i; j < hands.length; j++)
			{
				PokerHand h1 = hands[i];
				PokerHand h2 = hands[j];
				if (j == i) {
					// "equal" test
					compareTwoHands(h1, h2, 0);					
				} else {
					// "greater" test
					compareTwoHands(h1, h2, 1);					
				}
			}
		}
	}
	public void compareTwoHands(PokerHand h1, PokerHand h2, int expected)
	{
		int result = PokerHand.compareHands(h1, h2);
		
		String actualRelation = result>0 ? " is greater than " : result<0 ? " is less than " : " equal to ";
		String expectedRelation = expected>0 ? " greater. " : expected<0 ? " less. " : " equal. ";
		if (expected != result) {
			fail("Comparison test failed: " + h1.toString() + actualRelation + h2.toString() + " while it's expected to be " + expectedRelation );
		} else {
			String message = "Passed: " + h1.toString() + actualRelation + h2.toString();
			System.out.println(message);
		}
	}

}

