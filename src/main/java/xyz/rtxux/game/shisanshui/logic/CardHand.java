package xyz.rtxux.game.shisanshui.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * 手牌：13张，分为：头道3张，中道5张，尾道5张
 */
public class CardHand {
	
	private CardGroup firstGroup;
	
	private CardGroup middleGroup;
	
	private CardGroup endGroup;
	
	public CardGroup getFirstGroup() {
		return firstGroup;
	}
	
	public void setFirstGroup(CardGroup firstGroup) {
		this.firstGroup = firstGroup;
	}
	
	public CardGroup getMiddleGroup() {
		return middleGroup;
	}
	
	public void setMiddleGroup(CardGroup middleGroup) {
		this.middleGroup = middleGroup;
	}
	
	public CardGroup getEndGroup() {
		return endGroup;
	}
	
	public void setEndGroup(CardGroup endGroup) {
		this.endGroup = endGroup;
	}
	
	public List<Card> getCardList() {
		List<Card> cardList = new ArrayList<>();
		cardList.addAll(firstGroup.getCardList());
		cardList.addAll(middleGroup.getCardList());
		cardList.addAll(endGroup.getCardList());
		return cardList;
	}
	
	public int getWeight() {
		return firstGroup.getWeight() + middleGroup.getWeight() + endGroup.getWeight();
	}
	
	public int getType() {
		return firstGroup.getType() + middleGroup.getType() + endGroup.getType();
	}
	
	public String getTypeStr() {
		return "" + firstGroup.getType() + middleGroup.getType() + endGroup.getType();
	}
	
	public boolean isTypeAllLarge(CardHand cardHand) {
		if (firstGroup.getType() >= cardHand.getFirstGroup().getType()
				    && middleGroup.getType() >= cardHand.getMiddleGroup().getType()
				    && endGroup.getType() >= cardHand.getEndGroup().getType()) {
			return true;
		}
		return false;
	}
}
