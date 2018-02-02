/**
 * @author Arkady Koplyarov (arkad.k@gmail.com)
 */

package com.actionnodes.pokerhandcomparator;


/**
 * Ranks
 */
public enum Ranks
{
	D (0x0000000000000001L),		// Deuce
	Tr(0x0000000000000010L),		// Trey
	Fr(0x0000000000000100L),		// Four
	Fv(0x0000000000001000L),		// Five
	Sx(0x0000000000010000L),		// Six
	Sv(0x0000000000100000L),		// Seven
	E (0x0000000001000000L),		// Eight
	N (0x0000000010000000L),		// Nine
	Tn(0x0000000100000000L),		// Ten
	J (0x0000001000000000L),		// Jack
	Q (0x0000010000000000L),		// Queen
	K (0x0000100000000000L),		// King
	A (0x0001000000000000L),		// Ace
	UNKNOWN(0x0000000000000000L);	// UNKNOWN, it doesn't count to Ranks.enumSize
		
	// Have this at the end of the enum list, so by the time it's evaluated, 
	// all the values have been initialized. (The -1 is not to count Ranks.UNKNOWN enum element.) 
	public static final int enumSize = Ranks.values().length-1;
	
	public static Ranks getElemValueByIdx(int idx) {
		switch (idx) {
		case 0:  return Ranks.D;
		case 1:  return Ranks.Tr;
		case 2:  return Ranks.Fr;
		case 3:  return Ranks.Fv;
		case 4:  return Ranks.Sx;
		case 5:  return Ranks.Sv;
		case 6:  return Ranks.E;
		case 7:  return Ranks.N;
		case 8:  return Ranks.Tn;
		case 9:  return Ranks.J;
		case 10: return Ranks.Q;
		case 11: return Ranks.K;
		case 12: return Ranks.A;
		default:
			throw new IllegalArgumentException(" in Card.Ranks.getElemValueByIdx() ");
		}
	}
		
	private long value = 0x0000000000000000L;
	
	/**
	 * Constructor
	 * @param value
	 */
	private Ranks(long value) {
		this.value = value;
	}
	
	public long getValue() {
		return value;
	}
}

