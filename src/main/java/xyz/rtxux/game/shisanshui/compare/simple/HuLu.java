package xyz.rtxux.game.shisanshui.compare.simple;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.ArrayList;
import java.util.List;

public class HuLu extends CardSet {
	int typeVal = PokerDefine.HU_LU << PLACE_6;
	
	/**
	 * 判断葫芦，三张点数存在队列最后（队列越后大小越大）
	 */
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() != CARD_COUNT_5) return PokerDefine.NULL;
		Card card1 = cardList.get(0);
		Card card2 = cardList.get(1);
		Card card3 = cardList.get(2);
		Card card4 = cardList.get(3);
		Card card5 = cardList.get(4);
		
		List<Card> list = new ArrayList<>();
		if (card1.getNumber() == card2.getNumber() && card1.getNumber() == card3.getNumber() && card4.getNumber() == card5.getNumber()) {
			list.add(card5);
			list.add(card1);
			return typeVal + getCardListWeight(list);
		}
		
		if (card1.getNumber() == card2.getNumber() && card5.getNumber() == card4.getNumber() && card5.getNumber() == card3.getNumber()) {
			list.add(card1);
			list.add(card5);
			return typeVal + getCardListWeight(list);
		}
		return PokerDefine.NULL;
	}
}
