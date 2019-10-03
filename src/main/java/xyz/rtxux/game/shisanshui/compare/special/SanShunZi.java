package xyz.rtxux.game.shisanshui.compare.special;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;
import xyz.rtxux.game.shisanshui.logic.Card;

import java.util.ArrayList;
import java.util.List;

public class SanShunZi extends CardSet {
	@Override
	public int getWeight(List<Card> cardList) {
		if (cardList.size() != CARD_COUNT_13) return PokerDefine.NULL;
		List<Integer> numList = new ArrayList<>();
		for (Card card : cardList) {
			numList.add(card.getNumber());
		}
		List<List<Integer>> sanList = getSanList(numList);
		boolean isSanShunZi = false;
		for (int i = 0; i < sanList.size(); i++) {
			List<Integer> nList = new ArrayList<>();
			nList.addAll(numList);
			//删除三顺牌，剩余十张牌
			for (Integer tmpNum : sanList.get(i)) {
				nList.remove(tmpNum);
			}
			if (isWuShun(nList)) {
				//打印日志 begin
                /*
                for (Integer num : sanList.get(i)) {
                    System.out.print(num + "-");
                }
                System.out.println();
                */
				//打印日志 end
				isSanShunZi = true;
				break;
			}
		}
		if (isSanShunZi) {
			return PokerDefine.SAN_SHUN_ZI;
		} else {
			return PokerDefine.NULL;
		}
	}
	
	/**
	 * 判断10张片是否是两个5张顺子
	 */
	private boolean isWuShun(List<Integer> nList) {
		List<List<Integer>> wuList = new ArrayList<>();
		for (int i = 0; i < nList.size(); i++) {
			Integer num1 = nList.get(i);
			Integer num2 = num1 + 1;
			Integer num3 = num1 + 2;
			Integer num4 = num1 + 3;
			Integer num5 = num1 + 4;
			if (num1 == 10) {
				num5 = 1;
			}
			if (nList.contains(num2) && nList.contains(num3) && nList.contains(num4) && nList.contains(num5)) {
				List<Integer> tmpList = new ArrayList<>();
				tmpList.add(num1);
				tmpList.add(num2);
				tmpList.add(num3);
				tmpList.add(num4);
				tmpList.add(num5);
				if (!wuList.contains(tmpList)) {
					wuList.add(tmpList);
				}
			}
		}
		for (int i = 0; i < wuList.size(); i++) {
			for (int j = 0; j < wuList.size(); j++) {
				List<Integer> tmpList = new ArrayList<>();
				tmpList.addAll(nList);
				for (Integer tmpNum : wuList.get(i)) {
					tmpList.remove(tmpNum);
				}
				for (Integer tmpNum : wuList.get(j)) {
					tmpList.remove(tmpNum);
				}
				if (tmpList.size() == 0) {
					//打印日志 begin
                    /*
                    for (Integer tmpNum : wuList.get(i)) {
                        System.out.print(tmpNum + "-");
                    }
                    System.out.print("|");
                    for (Integer tmpNum : wuList.get(j)) {
                        System.out.print(tmpNum + "-");
                    }
                    System.out.print("|");
                    */
					//打印日志 end
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 找出3张顺子的所有可能组合（组合不重复）
	 */
	private List<List<Integer>> getSanList(List<Integer> numList) {
		List<List<Integer>> sanList = new ArrayList<>();
		for (int i = 0; i < numList.size(); i++) {
			int num1 = numList.get(i);
			int num2 = num1 + 1;
			int num3 = num1 + 2;
			//3张顺子12，13，1
			if (num1 == 12) {
				num3 = 1;
			}
			if (numList.contains(num2) && numList.contains(num3)) {
				List<Integer> san = new ArrayList<>();
				san.add(num1);
				san.add(num2);
				san.add(num3);
				if (!sanList.contains(san)) {
					sanList.add(san);
				}
			}
		}
		return sanList;
	}
}