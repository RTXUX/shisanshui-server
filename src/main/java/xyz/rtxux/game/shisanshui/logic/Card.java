package xyz.rtxux.game.shisanshui.logic;

/**
 * 单张牌：点数，花色
 */
public class Card {
	private int number;
	private int suit;
	
	public Card() {
	}
	
	public Card(int number, int suit) {
		this.number = number;
		this.suit = suit;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getSuit() {
		return suit;
	}
	
	public void setSuit(int suit) {
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		return "{" +
				       "number=" + number +
				       ", suit=" + suit +
				       '}';
	}
}
