package xyz.rtxux.game.shisanshui.compare.special;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.List;

public class CouYiSe extends CardSet {
	
	/**
	 * 十三张牌均为红色或者黑色
	 */
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
		int[] record = new int[4];
		for (int i = 0; i < cardList.size(); i++) {
			record[cardList.get(i).getSuit() - 1]++;
		}
		if (record[1] == 0 && record[2] == 0) {
			return PokerDefine.COU_YI_SE;
		} else if (record[0] == 0 && record[3] == 0) {
			return PokerDefine.COU_YI_SE;
		}
		return PokerDefine.NULL;
	}
}
