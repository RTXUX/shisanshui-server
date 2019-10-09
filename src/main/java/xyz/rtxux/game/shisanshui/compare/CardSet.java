package xyz.rtxux.game.shisanshui.compare;


import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.List;

public abstract class CardSet {
	//牌大小由6组 4位二进字存储，
	public final static int BIT_4 = 4;
	//第6组存储牌型
	public final static int PLACE_6 = BIT_4 * 5;
	//前面5组存储数据，用于在同种牌型下，判断牌大小
	public final static int PLACE_5 = BIT_4 * 4;
	public final static int PLACE_4 = BIT_4 * 3;
	public final static int PLACE_3 = BIT_4 * 2;
	public final static int PLACE_2 = BIT_4 * 1;
	public final static int PLACE_1 = BIT_4 * 0;
	
	public final static int CARD_A = 1;
	public final static int CARD_8 = 8;
	public final static int CARD_10 = 10;
	public final static int CARD_MAX = 14;
	public final static int CARD_COUNT_3 = 3;
	public final static int CARD_COUNT_5 = 5;
	public final static int CARD_COUNT_13 = 13;
	
	/**
	 * 根据cardlist存储并计算大小，cardlist越后面数据存储位数越高，即越大
	 * 牌A值自动转化为14
	 */
	public static int getCardListWeight(List<Card> cardList) {
		if (cardList.size() > CARD_COUNT_5) return PokerDefine.NULL;
		int weight = 0;
		for (int i = cardList.size() - 1; i >= 0; i--) {
			weight += getCardValue(cardList.get(i).getNumber()) << (BIT_4 * i);
		}
		return weight;
	}
	
	/**
	 * 若是A，则值由1转为14
	 */
	public static int getCardValue(int num) {
//		if (num == CARD_A) {
//			num = CARD_MAX;
//		}
		return num;
	}
	
	/**
	 * 从小到大排序，1(A)作为最小值，放到最前面
	 */
	public static List<Card> sortCards(List<Card> cardList) {
		for (int i = 0; i < cardList.size() - 1; i++) {
			for (int j = i + 1; j < cardList.size(); j++) {
				Card card1 = cardList.get(i);
				Card card2 = cardList.get(j);
				if (card1.getNumber() > card2.getNumber()) {
					cardList.set(i, card2);
					cardList.set(j, card1);
				}
			}
		}
		return cardList;
	}
	
	/**
	 * 从小到大排序，1(A)作为最大值，放到最尾部
	 */
	public static List<Card> sortCardByEndA(List<Card> cardList) {
		for (int i = 0; i < cardList.size() - 1; i++) {
			for (int j = i + 1; j < cardList.size(); j++) {
				Card card1 = cardList.get(i);
				Card card2 = cardList.get(j);
				if (getCardValue(card1.getNumber()) > getCardValue(card2.getNumber())) {
					cardList.set(i, card2);
					cardList.set(j, card1);
				}
			}
		}
		return cardList;
	}
	
	/**
	 * 判断是否为相同花色
	 */
	public static boolean checkSameSuit(List<Card> cardList) {
		int cardSuit = cardList.get(0).getSuit();
		for (int i = 1; i < cardList.size(); i++) {
			int tempCardSuit = cardList.get(i).getSuit();
			if (cardSuit != tempCardSuit) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断前需先调用排序方法 sortCard
	 * 判断是否有超过sameNumCount张相同点数牌
	 */
	public static boolean checkSameNumberCount(List<Card> cardList, int sameNumCount) {
		int count = 1;
		for (int i = 0; i < cardList.size() - 1; i++) {
			int num1 = cardList.get(i).getNumber();
			int num2 = cardList.get(i + 1).getNumber();
			if (num1 == num2) {
				count++;
				if (count >= sameNumCount) {
					return true;
				}
			} else {
				count = 1;
			}
		}
		return false;
	}
	
	/**
	 * 判断前需先调用排序方法 sortCard
	 * 判断当前是否为顺子
	 */
	public static boolean checkSequence(List<Card> cardList) {
		for (int i = 0; i < cardList.size() - 1; i++) {
			int num1 = cardList.get(i).getNumber();
			int num2 = cardList.get(i + 1).getNumber();
			if ((num2 - num1) != 1) {
				if (i == 0 && num1 == CARD_A && num2 == CARD_10) {
					continue;
				}
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkIncludeTongHua(List<Card> cardList) {
		int[] record = new int[4];
		for (int i = 0; i < cardList.size(); i++) {
			record[cardList.get(i).getSuit() - 1]++;
		}
		for (int i = 0; i < record.length; i++) {
			if (record[i] == 5) {
				return true;
			}
		}
		return false;
	}
	
	public static int getType(int result) {
		return result >> PLACE_6;
	}
	
	public abstract int getWeight(List<Card> cardList);
}
