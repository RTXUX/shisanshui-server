package xyz.rtxux.game.shisanshui.compare.simple;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * 判断连队，对子依小大到分别存于第2，3位，单张存于第一位
 */
public class LiangDui extends CardSet {
	
	int typeVal = PokerDefine.LIANG_DUI << PLACE_6;
	
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() != CARD_COUNT_5) return PokerDefine.NULL;
		List<Card> duiList = new ArrayList<>();
		for (int i = 0; i < cardList.size() - 1; i++) {
			if (cardList.get(i + 1).getNumber() == cardList.get(i).getNumber()) {
				duiList.add(cardList.get(i));
			}
		}
		if (duiList.size() < 2) {
			return PokerDefine.NULL;
		}
		Card danCard = null;//单张牌
		for (int i = 0; i < cardList.size(); i++) {
			int num = cardList.get(i).getNumber();
			if (num != duiList.get(0).getNumber() && num != duiList.get(1).getNumber()) {
				danCard = cardList.get(i);
				break;
			}
		}
		if (danCard == null) {
			return PokerDefine.NULL;
		}
		duiList = sortCardByEndA(duiList);
		if (duiList.get(0).getNumber() == duiList.get(1).getNumber() - 1) duiList.add(2, duiList.get(1));
		duiList.add(0, danCard);
		return typeVal + getCardListWeight(duiList);
	}
}
