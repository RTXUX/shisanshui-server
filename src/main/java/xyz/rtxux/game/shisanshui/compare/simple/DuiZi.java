package xyz.rtxux.game.shisanshui.compare.simple;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.ArrayList;
import java.util.List;

public class DuiZi extends CardSet {
	int typeVal = PokerDefine.DUI_ZI << PLACE_6;
	
	/**
	 * 判断头道（3张），中道和尾道（5张）
	 */
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() > CARD_COUNT_5) return PokerDefine.NULL;
		//存储剩余单张
		List<Card> danList = new ArrayList<>();
		int dui = -1;
		for (int i = 0; i < cardList.size() - 1; i++) {
			if (cardList.get(i + 1).getNumber() == cardList.get(i).getNumber()) {
				dui = cardList.get(i).getNumber();
				break;
			}
		}
		if (dui == -1) {
			return PokerDefine.NULL;
		}
		for (int i = 0; i < cardList.size(); i++) {
			Card card = cardList.get(i);
			if (card.getNumber() != dui) {
				danList.add(card);
			}
		}
		danList = sortCardByEndA(danList);
		//将对子点数存在第四位，其它单张按大到小顺序分别存在前几位
		int weight = getCardValue(dui) << PLACE_4;
		return typeVal + weight + getCardListWeight(danList);
	}
	
}
