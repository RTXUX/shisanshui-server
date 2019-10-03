package xyz.rtxux.game.shisanshui.compare.simple;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.List;

public class WuLong extends CardSet {
	int typeVal = PokerDefine.WU_LONG << PLACE_6;
	
	/**
	 * 判断头道（3张），中道和尾道（5张）
	 */
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() > CARD_COUNT_5) return PokerDefine.NULL;
		cardList = sortCardByEndA(cardList);
		if (cardList.size() == 3) {
			//判断头道三张时，将三张放于3,4,5位，提高权重
			int weight = getCardValue(cardList.get(0).getNumber()) << PLACE_3;
			weight += getCardValue(cardList.get(1).getNumber()) << PLACE_4;
			weight += getCardValue(cardList.get(2).getNumber()) << PLACE_5;
			return typeVal + weight;
		}
		return typeVal + getCardListWeight(cardList);
		
	}
}
