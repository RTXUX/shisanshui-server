package xyz.rtxux.game.shisanshui.logic;

import xyz.rtxux.game.shisanshui.compare.CardSet;
import xyz.rtxux.game.shisanshui.compare.PokerDefine;

public class CardConfig {
	
	//    三同花 +3分：若大于其它玩家，赢取每家3分
//    三顺子 +4分：若大于其它玩家，赢取每家4分
//    六对半 +4分：若大于其它玩家，赢取每家4分
//    五对三条 +5分：若大于其它玩家，赢取每家5分
//    四套三条 +6分：若大于其它玩家，赢取每家6分
//    凑一色 +10分：若大于其它玩家，赢取每家10分
//    全小 +10分：若大于其它玩家，赢取每家10分
//    全大 +10分：若大于其它玩家，赢取每家10分
//    三分天下 +20分：若大于其它玩家，赢取每家20分
//    三同花顺 +20分：若大于其它玩家，赢取每家20分
//    十二皇族 +24分：若大于其它玩家，赢取每家24分
//    一条龙 +36分：若大于其它玩家，赢取每家36分
//    清龙  +108分：若大于其它玩家，赢取每家108分
	//获取倍数积分 后期配置化
	public static int getSpecialMultiple(int type) {
		switch (type) {
			case PokerDefine.ZHI_ZUN_QING_LONG:
				return 52;
			case PokerDefine.YI_TIAO_LONG:
				return 26;
			case PokerDefine.SHI_ER_HUANG_ZU:
				return 24;
			case PokerDefine.SAN_TONG_HUA_SHUN:
				return 22;
			case PokerDefine.SAN_ZHA_DAN:
				return 20;
			case PokerDefine.QUAN_DA:
				return 15;
			case PokerDefine.QUAN_XIAO:
				return 12;
			case PokerDefine.COU_YI_SE:
				return 10;
			case PokerDefine.SHUANG_GUAI_CHONG_SAN:
				return 8;
			case PokerDefine.SI_TAO_SAN_TIAO:
				return 6;
			case PokerDefine.WU_DUI_CHONG_SAN:
				return 5;
			case PokerDefine.LIU_DUI_BAN:
				return 4;
			case PokerDefine.SAN_SHUN_ZI:
			case PokerDefine.SAN_TONG_HUA:
				return 3;
		}
		return 0;
	}
	
	//    -----------------------------------------比牌规则------------------------------------
//    冲三  +3分：头道为三条，且大于对手，赢家可获得1分外，再加2分，总计赢3分，输家则输3分。
//    中墩葫芦 +2分：中道为葫芦，且大于对手，赢家可获得1分外，再加1分，总计2分，输家则输2分。
//    铁支（中道）+7分：中道为铁支，且大于对手，赢家可获得1分外，再加6分，总计赢7分，输家则输7分。
//    铁支（尾道）+4分：尾道为铁支，且大于对手，赢家可获得1分外，再加3分，总计赢4分，输家则输4分。
//    同花顺（中道）+9分：中道为同花顺，且大于对手，赢家可获得1分外，再加8分，总计9分，输家则输9分。
//    同花顺（尾道）+5分：尾道为同花顺，且大于对手，赢家可获得1分外，再加4分，总计5分，输家则输5分。
//    五同（中道）+11分：中道为五同，且大于对手，赢家可获得1分外，再加10分，总计11分，输家则输11分。
//    五同（尾道）+6分：中道为五同，且大于对手，赢家可获得1分外，再加5分，总计6分，输家则输6分。
	//获取倍数积分 后期配置化
	public static int getMultiple(int result, int pos) {
		int type = CardSet.getType(result);
//		if (type == PokerDefine.SAN_TIAO && pos == 1) {
//			return 3;
//		}
		if (type == PokerDefine.HU_LU && pos == 2) {
			return 2;
		}
		if (type == PokerDefine.TIE_ZHI) {
			if (pos == 2) {
				return 8;
			}
			return 4;
		}
		if (type == PokerDefine.TONG_HUA_SHUN) {
			if (pos == 2) {
				return 10;
			}
			return 5;
		}
//		if (type == PokerDefine.WU_TONG) {
//			if (pos == 2) {
//				return 11;
//			}
//			return 6;
//		}
		return 1;
	}
}
