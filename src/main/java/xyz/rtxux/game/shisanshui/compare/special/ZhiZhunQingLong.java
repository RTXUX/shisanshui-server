package xyz.rtxux.game.shisanshui.compare.special;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.List;

public class ZhiZhunQingLong extends CardSet {
	
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
		if (checkSequence(cardList) && checkSameSuit(cardList)) {
			return PokerDefine.ZHI_ZUN_QING_LONG;
		}
		return PokerDefine.NULL;
	}
}
