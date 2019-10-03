package xyz.rtxux.game.shisanshui.compare.simple;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.List;

public class TongHuaShun extends CardSet {
	int typeVal = PokerDefine.TONG_HUA_SHUN << PLACE_6;
	
	/**
	 * 判断中道和尾道（5张）
	 */
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() != CARD_COUNT_5) return PokerDefine.NULL;
		if (checkSameSuit(cardList) && checkSequence(cardList)) {
			cardList = sortCardByEndA(cardList);
			return typeVal + getCardListWeight(cardList);
		}
		return PokerDefine.NULL;
	}
}
