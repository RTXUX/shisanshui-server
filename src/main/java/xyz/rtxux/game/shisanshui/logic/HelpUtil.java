package xyz.rtxux.game.shisanshui.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class HelpUtil {
	public static final List<String> numberList = new ArrayList<String>(Arrays.asList(
			null, "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"
	));
	
	public static final ConcurrentHashMap<String, Card> cache = new ConcurrentHashMap<String, Card>();
	public static String getSuitStr(int suit) {
		if (PokerSuitEnum.SPADE.getValue() == suit) {
			return "$";
		} else if (PokerSuitEnum.HEART.getValue() == suit) {
			return "&";
		} else if (PokerSuitEnum.DIAMOND.getValue() == suit) {
			return "#";
		} else {
			return "*";
		}
	}
	
	public static int getSuit(String suit) {
		if ("$".equals(suit)) {
			return PokerSuitEnum.SPADE.getValue();
		} else if ("&".equals(suit)) {
			return PokerSuitEnum.HEART.getValue();
		} else if ("#".equals(suit)) {
			return PokerSuitEnum.DIAMOND.getValue();
		} else {
			return PokerSuitEnum.CLUB.getValue();
		}
	}
	
	public static Card convertStringToSingleCard(String str) {
		Card cached = cache.get(str);
		if (cached != null) return cached;
		int suit = getSuit("" + str.charAt(0));
		int num = numberList.indexOf(str.substring(1));
		cached = new Card(num, suit);
		cache.put(cached.toString(), cached);
		return cached;
	}
	
	public static List<Card> converCardArrayToCardList(String[] cardArray) {
		List<Card> cardList = new ArrayList<>();
		for (int i = 0; i < cardArray.length; i++) {
			String cardStr = cardArray[i];
			int num = Integer.parseInt(cardStr.substring(1));
			int suit = getSuit("" + cardStr.charAt(0));
			Card card = new Card(num, suit);
			cardList.add(card);
		}
		return cardList;
	}
	
	public static String printCardList(List<Card> cardList) {
		String cardString = "";
		for (Card c : cardList) {
			cardString += ", \"" + HelpUtil.getSuitStr(c.getSuit()) + c.getNumber() + "\"";
		}
		return cardString.replaceFirst(", ", "");
	}
	
	public static void initCache() {
		for (PokerSuitEnum pokerSuit : PokerSuitEnum.values()) {
			for (int i = 1; i < 14; i++) {
				Card card = new Card(i, pokerSuit.getValue());
				cache.put(card.toString(), card);
			}
		}
	}
	
	public static List<Card> getRandomCardList() {
//		ArrayList<Card> allCards = new ArrayList<>();
//		for (PokerSuitEnum pokerSuit : PokerSuitEnum.values()) {
//			for (int i = 1; i < 14; i++) {
//				allCards.add(new Card(i, pokerSuit.getValue()));
//			}
//		}
		ArrayList<Card> availableCards = new ArrayList(cache.values());
		List<Card> cardList = new ArrayList<>();
		Random random = new Random();
		for (int i = 1; i < 14; i++) {
			int index = random.nextInt(availableCards.size());
			Card card = availableCards.get(index);
			availableCards.remove(index);
			cardList.add(card);
		}
		return cardList;
	}
	
	public static void main(String[] args) {
		String testStr = "♥12";
		System.out.println(HelpUtil.getSuit("" + testStr.charAt(0)));
		System.out.println(testStr.substring(1));
		
	}
}
