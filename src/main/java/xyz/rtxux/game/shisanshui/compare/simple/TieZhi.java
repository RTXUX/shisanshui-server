package xyz.rtxux.game.shisanshui.compare.simple;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.List;

public class TieZhi extends CardSet {
	int typeVal = PokerDefine.TIE_ZHI << PLACE_6;
	
	/**
	 * 因为最多坚持 7人，同一点数最多7张，
	 * 所以铁支只需比较铁支点数大小
	 * (不存在铁支点数一样大，比较剩余单张情况)
	 */
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() != CARD_COUNT_5) return PokerDefine.NULL;
		//判断四个一样
		if (checkSameNumberCount(cardList, 4)) {
			//找到四个一样的值
			int weight = 0;
			if (cardList.get(1).getNumber() == cardList.get(2).getNumber()) {
				weight = cardList.get(1).getNumber();
			} else {
				weight = cardList.get(2).getNumber();
			}
			return typeVal + (getCardValue(weight) << PLACE_1);
		}
		return PokerDefine.NULL;
	}
}
