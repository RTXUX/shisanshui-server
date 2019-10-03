package xyz.rtxux.game.shisanshui.logic;

import xyz.rtxux.game.shisanshui.compare.CardSet;

import java.util.List;

/**
 * 牌组，3张或5张，和于存储一组牌相关信息
 * 手牌由三个牌组组成
 */
public class CardGroup {
	
	private List<Card> cardList;
	
	private int weight;
	
	public CardGroup(List<Card> cardList, int weight) {
		this.cardList = cardList;
		this.weight = weight;
	}
	
	public List<Card> getCardList() {
		return cardList;
	}
	
	public void setCardList(List<Card> cardList) {
		this.cardList = cardList;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getType() {
		return CardSet.getType(weight);
	}
	
	@Override
	public String toString() {
		return "CardGroup{" +
				       ", weight=" + weight +
				       "cardList=" + cardList +
				       '}';
	}
}
