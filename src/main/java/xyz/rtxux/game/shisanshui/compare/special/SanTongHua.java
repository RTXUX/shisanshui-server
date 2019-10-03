package xyz.rtxux.game.shisanshui.compare.special;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.List;

public class SanTongHua extends CardSet {
	
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
		int[] record = new int[4];
		for (int i = 0; i < cardList.size(); i++) {
			record[cardList.get(i).getSuit() - 1]++;
		}
		int wuCount = 0;
		int sanCount = 0;
		for (int i = 0; i < record.length; i++) {
			if (record[i] == 5) {
				wuCount++;
			} else if (record[i] == 3) {
				sanCount++;
			}
		}
		if (wuCount == 2 && sanCount == 1) {
			return PokerDefine.SAN_TONG_HUA;
		}
		return PokerDefine.NULL;
	}
}
