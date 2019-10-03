package xyz.rtxux.game.shisanshui.logic;

public enum PokerSuitEnum {
	
	SPADE(4), HEART(3), DIAMOND(2), CLUB(1);
	
	private int value;
	
	PokerSuitEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
