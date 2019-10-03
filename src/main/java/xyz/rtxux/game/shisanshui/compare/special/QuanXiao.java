package xyz.rtxux.game.shisanshui.compare.special;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.List;

public class QuanXiao extends CardSet {
	
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
		for (int i = 0; i < cardList.size(); i++) {
			int num = cardList.get(i).getNumber();
			if (num > CARD_8 || num == CARD_A) {
				return PokerDefine.NULL;
			}
		}
		return PokerDefine.QUAN_XIAO;
	}
}