package xyz.rtxux.game.shisanshui.compare.simple;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * 判断是否为首道冲三或是尾道三条
 */
public class SanTiao extends CardSet {
	int typeVal = PokerDefine.SAN_TIAO << PLACE_6;
	
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() > CARD_COUNT_5) return PokerDefine.NULL;
		//存储剩余单张
		List<Card> danList = new ArrayList<>();
		int san = -1;
		for (int i = 0; i < cardList.size(); i++) {
			if (i + 2 < cardList.size()) {
				Card card1 = cardList.get(i);
				Card card2 = cardList.get(i + 1);
				Card card3 = cardList.get(i + 2);
				if (card1.getNumber() == card2.getNumber() && card2.getNumber() == card3.getNumber()) {
					san = card1.getNumber();
					break;
				}
			}
		}
		if (san == -1) {
			return PokerDefine.NULL;
		}
		for (int i = 0; i < cardList.size(); i++) {
			Card card = cardList.get(i);
			if (card.getNumber() != san) {
				danList.add(card);
			}
		}
		danList = sortCardByEndA(danList);
		if (cardList.size() == 3) {
			int weight = getCardValue(san) << PLACE_1;
			return typeVal + weight + getCardListWeight(danList);
		} else {
			int weight = getCardValue(san) << PLACE_3;
			return typeVal + weight + getCardListWeight(danList);
		}
		//将三张点数存在第三位
	}
}