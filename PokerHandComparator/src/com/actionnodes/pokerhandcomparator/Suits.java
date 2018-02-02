/**
 * @author Arkady Koplyarov (arkad.k@gmail.com)
 */

package com.actionnodes.pokerhandcomparator;


/**
 * Suits
 */
public enum Suits {
	S(0x1),			// SPADES	0001
	H(0x2),			// HEARTS	0010
	D(0x4),			// DIAMONDS	0100
	C(0x8),			// CLUBS	1000
	UNKNOWN(0x0); 	// UNKNOWN	0000
	
	public static Suits checkValue(int i) {
		return
			i==0x1 ? Suits.S :
			i==0x2 ? Suits.H :
			i==0x4 ? Suits.D :
			i==0x8 ? Suits.C : 
			Suits.UNKNOWN;
	}
	
	private int value = 0x0;
	
	/**
	 * Constructor
	 * @param value
	 */
	private Suits(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
