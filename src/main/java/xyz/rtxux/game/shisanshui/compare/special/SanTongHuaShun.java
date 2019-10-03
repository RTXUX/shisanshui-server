package xyz.rtxux.game.shisanshui.compare.special;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.ArrayList;
import java.util.List;

public class SanTongHuaShun extends CardSet {
	
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
		List<List<Card>> suitCardList = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			suitCardList.add(new ArrayList<>());
		}
		for (int i = 0; i < cardList.size(); i++) {
			Card tmpCard = cardList.get(i);
			suitCardList.get(tmpCard.getSuit() - 1).add(tmpCard);
		}
		int wuCount = 0;
		int sanCount = 0;
		//判断是否为3同花
		for (int i = 0; i < suitCardList.size(); i++) {
			if (suitCardList.get(i).size() == 5) {
				wuCount++;
			} else if (suitCardList.get(i).size() == 3) {
				sanCount++;
			}
		}
		if (wuCount == 2 && sanCount == 1) {
			//判断是否是顺子
			for (int i = 0; i < suitCardList.size(); i++) {
				if (suitCardList.get(i).size() > 0) {
					if (!checkSequence(suitCardList.get(i))) {
						return PokerDefine.NULL;
					}
				}
			}
			return PokerDefine.SAN_TONG_HUA_SHUN;
		}
		return PokerDefine.NULL;
	}
}
