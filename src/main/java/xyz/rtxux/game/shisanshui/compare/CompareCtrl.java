package xyz.rtxux.game.shisanshui.compare;

import org.springframework.stereotype.Component;
import xyz.rtxux.game.shisanshui.compare.simple.*;
import xyz.rtxux.game.shisanshui.compare.special.*;
import xyz.rtxux.game.shisanshui.logic.Card;
import xyz.rtxux.game.shisanshui.logic.CardGroup;
import xyz.rtxux.game.shisanshui.logic.CardHand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CompareCtrl {
	private List<CardSet> specialList = new ArrayList<>();
	private List<CardSet> simpleList = new ArrayList<>();
	
	public CompareCtrl() {
		init();
	}
	
	public void init() {
		//-------------------------特殊牌型-----------------------------
		//至尊清龙
		addSpecialState(ZhiZhunQingLong.class);
		//一条龙
		addSpecialState(YiTiaoLong.class);
		//十二皇族
		addSpecialState(ShiErHuangZu.class);
		//三同花顺
		addSpecialState(SanTongHuaShun.class);
		//三套炸弹（三分天下）
		addSpecialState(SanZhaDan.class);
		//全大
		addSpecialState(QuanDa.class);
		//全小
		addSpecialState(QuanXiao.class);
		//凑一色
		addSpecialState(CouYiSe.class);
		//四套三条
		addSpecialState(SiTaoSanTiao.class);
		//五对冲三
		addSpecialState(WuDuiChongSan.class);
		//六对半
		addSpecialState(LiuDuiBan.class);
		//三顺子
		addSpecialState(SanShunZi.class);
		//三同花
		addSpecialState(SanTongHua.class);
		
		//-------------------------普通牌型-----------------------------
		//五张相同
		addSimpleState(WuTong.class);
		//同花顺
		addSimpleState(TongHuaShun.class);
		//铁支
		addSimpleState(TieZhi.class);
		//葫芦
		addSimpleState(HuLu.class);
		//同花
		addSimpleState(TongHua.class);
		//顺子
		addSimpleState(ShunZi.class);
		//三条
		addSimpleState(SanTiao.class);
		//两对
		addSimpleState(LiangDui.class);
		//对子
		addSimpleState(DuiZi.class);
		//乌龙
		addSimpleState(WuLong.class);
	}
	
	public int calc(List<Card> cardList) {
		cardList = CardSet.sortCards(cardList);
		for (int i = 0; i < simpleList.size(); i++) {
			int weight = simpleList.get(i).getWeight(cardList);
			if (weight > 0) {
				return weight;
			}
		}
		return -1;
	}
	
	public int calcWithoutSort(List<Card> cardList) {
		for (int i = 0; i < simpleList.size(); i++) {
			int weight = simpleList.get(i).getWeight(cardList);
			if (weight > 0) {
				return weight;
			}
		}
		return -1;
	}
	
	public int calcSpecial(List<Card> cardList) {
		cardList = CardSet.sortCards(cardList);
		for (int i = 0; i < specialList.size(); i++) {
			int weight = specialList.get(i).getWeight(cardList);
			if (weight > 0) {
				return weight;
			}
		}
		return 0;
	}
	
	
	private void addSpecialState(Class<? extends CardSet> cls) {
		CardSet compare;
		try {
			compare = (CardSet) Class.forName(cls.getName()).newInstance();
			specialList.add(compare);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addSimpleState(Class<? extends CardSet> cls) {
		CardSet compare;
		try {
			compare = (CardSet) Class.forName(cls.getName()).newInstance();
			simpleList.add(compare);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<CardHand> getBestCardHand(List<Card> cardList) {
		boolean isIncludeTongHua = CardSet.checkIncludeTongHua(cardList);
		List<CardGroup> tempGroupList = new ArrayList<>();
		List<Integer> endWeightList = new ArrayList<>();
		int maxEndType = 0;
		List<CardHand> bestCardHandList = new ArrayList<>();
		long a = System.currentTimeMillis();
		//尾道组合
		for (int i = 0; i < cardList.size(); i++) {
			for (int j = i + 1; j < cardList.size(); j++) {
				for (int k = j + 1; k < cardList.size(); k++) {
					for (int m = k + 1; m < cardList.size(); m++) {
						for (int n = m + 1; n < cardList.size(); n++) {
							List<Card> endCardList = new ArrayList();
							endCardList.add(cardList.get(i));
							endCardList.add(cardList.get(j));
							endCardList.add(cardList.get(k));
							endCardList.add(cardList.get(m));
							endCardList.add(cardList.get(n));
							int weight = calcWithoutSort(endCardList);
							CardGroup endCardGroup = new CardGroup(endCardList, weight);
							//尾道不为乌龙
							if (PokerDefine.WU_LONG == endCardGroup.getType()) {
								continue;
							}
							//若牌型不含同花，则可以过滤权重相同，减少计算量
							//权重相同原因为同点数不同花色重复组合，若牌型无同花，可只留一种组合
							if (!isIncludeTongHua && endWeightList.contains(endCardGroup.getWeight())) {
								continue;
							}
							//如果牌型比当前最大牌型小太多，则过滤（减小运算，影响推荐准确）
							//if (endCardGroup.getType() < (maxEndType - 2)) {
							//    continue;
							//}
							tempGroupList.add(endCardGroup);
							//记录最大类型
							if (endCardGroup.getType() > maxEndType) {
								maxEndType = endCardGroup.getType();
							}
							endWeightList.add(endCardGroup.getWeight());
						}
					}
				}
			}
		}
		long b = System.currentTimeMillis();
		//System.out.println("耗时：" + (b - a) + "ms, 尾道组合：" + tempGroupList.size());
		// 取出牌型相差不超过3的牌组,减少计算量（但会小概率影响最优推荐）
		List<CardGroup> endGroupList = new ArrayList<>();
		for (int i = 0; i < tempGroupList.size(); i++) {
			if (tempGroupList.get(i).getType() >= (maxEndType - 3)) {
				endGroupList.add(tempGroupList.get(i));
			}
		}
		//中道及头道组合
		List<Integer> middleWeightList = new ArrayList<>();
		List<Integer> weightList = new ArrayList<>();
		for (CardGroup endCardGroup : endGroupList) {
			List<Card> tempCardList1 = new ArrayList<>();
			tempCardList1.addAll(cardList);
			for (Card endCard : endCardGroup.getCardList()) {
				tempCardList1.remove(endCard);
			}
			for (int i = 0; i < tempCardList1.size(); i++) {
				for (int j = i + 1; j < tempCardList1.size(); j++) {
					for (int k = j + 1; k < tempCardList1.size(); k++) {
						for (int m = k + 1; m < tempCardList1.size(); m++) {
							for (int n = m + 1; n < tempCardList1.size(); n++) {
								List<Card> middleCardList = new ArrayList();
								middleCardList.add(tempCardList1.get(i));
								middleCardList.add(tempCardList1.get(j));
								middleCardList.add(tempCardList1.get(k));
								middleCardList.add(tempCardList1.get(m));
								middleCardList.add(tempCardList1.get(n));
								int weight = calcWithoutSort(middleCardList);
								CardGroup middleCardGroup = new CardGroup(middleCardList, weight);
								//过滤权重相同的中道牌组
								if (!isIncludeTongHua && middleWeightList.contains(middleCardGroup.getWeight())) {
									continue;
								}
								List<Card> tempCardList2 = new ArrayList<>();
								tempCardList2.addAll(tempCardList1);
								if (endCardGroup.getWeight() > middleCardGroup.getWeight()) {
									for (Card middleCard : middleCardGroup.getCardList()) {
										tempCardList2.remove(middleCard);
									}
									int firstGroupWeight = calcWithoutSort(tempCardList2);
									CardGroup firstCardGroup = new CardGroup(tempCardList2, firstGroupWeight);
									if (middleCardGroup.getWeight() > firstGroupWeight) {
										CardHand cardHand = new CardHand();
										cardHand.setFirstGroup(firstCardGroup);
										cardHand.setMiddleGroup(middleCardGroup);
										cardHand.setEndGroup(endCardGroup);
										//过滤权重相同的牌组
										if (weightList.contains(cardHand.getWeight())) {
											continue;
										}
										bestCardHandList.add(cardHand);
										middleWeightList.add(middleCardGroup.getWeight());
										weightList.add(cardHand.getWeight());
									}
								}
							}
						}
					}
				}
			}
		}
		
		Map<String, CardHand> bestHandMap = new HashMap<>();
		for (int i = 0; i < bestCardHandList.size(); i++) {
			CardHand cardHand = bestCardHandList.get(i);
			CardHand c = bestHandMap.get(cardHand.getTypeStr());
			if (c == null || c.getWeight() < cardHand.getWeight()) {
				bestHandMap.put(cardHand.getTypeStr(), cardHand);
			}
		}
		bestCardHandList.clear();
		bestCardHandList.addAll(bestHandMap.values());
		List<CardHand> tempCardHanList = new ArrayList<>();
		tempCardHanList.addAll(bestCardHandList);
		for (int i = 0; i < tempCardHanList.size(); i++) {
			for (int j = i + 1; j < tempCardHanList.size(); j++) {
				CardHand cardHand1 = tempCardHanList.get(i);
				CardHand cardHand2 = tempCardHanList.get(j);
				if (cardHand1.isTypeAllLarge(cardHand2)) {
					bestCardHandList.remove(cardHand2);
				}
				if (cardHand2.isTypeAllLarge(cardHand1)) {
					bestCardHandList.remove(cardHand1);
				}
			}
		}
		return bestCardHandList;
	}
	
}
