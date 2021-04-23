package cc.js.sora.fight.serivce;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Longs;

import cc.js.sora.fight.Action;
import cc.js.sora.fight.BarrackSkills;
import cc.js.sora.fight.Equip;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.Soldier;
import cc.js.sora.fight.db.ActionRepository;
import cc.js.sora.fight.db.HeroRepository;
import cc.js.sora.fight.db.SoldierRepository;
import cc.js.sora.fight.skill.SuperBuff;
import cc.js.sora.fight.skill.action.Anlian;
import cc.js.sora.fight.skill.action.Anlong;
import cc.js.sora.fight.skill.action.Benlei;
import cc.js.sora.fight.skill.action.Binglong;
import cc.js.sora.fight.skill.action.Bingqiang;
import cc.js.sora.fight.skill.action.Bujiechongji;
import cc.js.sora.fight.skill.action.DreamAction;
import cc.js.sora.fight.skill.action.Feicuimoshi;
import cc.js.sora.fight.skill.action.Feicuipofeng;
import cc.js.sora.fight.skill.action.Fengren;
import cc.js.sora.fight.skill.action.Heilingwan;
import cc.js.sora.fight.skill.action.Heiyao;
import cc.js.sora.fight.skill.action.Huoqiu;
import cc.js.sora.fight.skill.action.Jinglingzhixi;
import cc.js.sora.fight.skill.action.Juemingyiji;
import cc.js.sora.fight.skill.action.Lanxingzhan;
import cc.js.sora.fight.skill.action.Leiji;
import cc.js.sora.fight.skill.action.Liefengzhiji;
import cc.js.sora.fight.skill.action.Lingwan;
import cc.js.sora.fight.skill.action.Molizhendang;
import cc.js.sora.fight.skill.action.Qiangfengjuji;
import cc.js.sora.fight.skill.action.Shenfa;
import cc.js.sora.fight.skill.action.Shengguangzhiyong;
import cc.js.sora.fight.skill.action.Shengwangzhiji;
import cc.js.sora.fight.skill.action.Shengyan;
import cc.js.sora.fight.skill.action.Shiying;
import cc.js.sora.fight.skill.action.Weifengchongzhen;
import cc.js.sora.fight.skill.action.XunyingShaji;
import cc.js.sora.fight.skill.action.Yazhisheji;
import cc.js.sora.fight.skill.action.Yeshouzhenshe;
import cc.js.sora.fight.skill.action.Zuizhongshenpan;
import cc.js.sora.fight.skill.enhance.DashuEnhance;
import cc.js.sora.fight.skill.enhance.FuriousEnhance;
import cc.js.sora.fight.skill.enhance.GangtieEnhance;
import cc.js.sora.fight.skill.enhance.LieriEnhance;
import cc.js.sora.fight.skill.enhance.LiuxingEnhance;
import cc.js.sora.fight.skill.enhance.ManyueEnhance;
import cc.js.sora.fight.skill.enhance.MoshuEnhance;
import cc.js.sora.fight.skill.enhance.ShuijingEnhance;
import cc.js.sora.fight.skill.enhance.WindEnhance;
import cc.js.sora.fight.skill.equip.Cangbaizhizhang;
import cc.js.sora.fight.skill.equip.Canyue;
import cc.js.sora.fight.skill.equip.Chenhun;
import cc.js.sora.fight.skill.equip.Chenshi;
import cc.js.sora.fight.skill.equip.Daditou;
import cc.js.sora.fight.skill.equip.Erzhui;
import cc.js.sora.fight.skill.equip.Feiyingxingdou;
import cc.js.sora.fight.skill.equip.JingjiHuangguan;
import cc.js.sora.fight.skill.equip.Jingmian;
import cc.js.sora.fight.skill.equip.Jixianmogong;
import cc.js.sora.fight.skill.equip.Lage;
import cc.js.sora.fight.skill.equip.LastSuit;
import cc.js.sora.fight.skill.equip.Mimier;
import cc.js.sora.fight.skill.equip.Mingxiang;
import cc.js.sora.fight.skill.equip.Rongyao;
import cc.js.sora.fight.skill.equip.Shenpan;
import cc.js.sora.fight.skill.equip.Shenyi;
import cc.js.sora.fight.skill.equip.Shijieshu;
import cc.js.sora.fight.skill.equip.Shouhuzhe;
import cc.js.sora.fight.skill.equip.Shuijingfengci;
import cc.js.sora.fight.skill.equip.Tier;
import cc.js.sora.fight.skill.equip.Tulong;
import cc.js.sora.fight.skill.equip.Wuleer;
import cc.js.sora.fight.skill.equip.Xunzhang;
import cc.js.sora.fight.skill.equip.Yicai;
import cc.js.sora.fight.skill.equip.Yuezhihuiyi;
import cc.js.sora.fight.skill.equip.Yuguan;
import cc.js.sora.fight.skill.equip.Zhenshizijia;
import cc.js.sora.fight.skill.equip.Zuzhouzhiqiang;
import cc.js.sora.fight.skill.heart.AlbedoHuzhu;
import cc.js.sora.fight.skill.heart.AlustrielLangqi;
import cc.js.sora.fight.skill.heart.AlutemuratSP;
import cc.js.sora.fight.skill.heart.AutokratoIVHuangdi;
import cc.js.sora.fight.skill.heart.AutokratoIVHuangjia;
import cc.js.sora.fight.skill.heart.BrendaDoushen;
import cc.js.sora.fight.skill.heart.BrendaHuangjia;
import cc.js.sora.fight.skill.heart.DeedlitJingling;
import cc.js.sora.fight.skill.heart.EluciaHaiqi;
import cc.js.sora.fight.skill.heart.EluciaHuangjia;
import cc.js.sora.fight.skill.heart.EmiliaShengqi;
import cc.js.sora.fight.skill.heart.EmiliaShengqiang;
import cc.js.sora.fight.skill.heart.FlorentiaJunshi;
import cc.js.sora.fight.skill.heart.FlorentiaZaixiang;
import cc.js.sora.fight.skill.heart.FreyaSP;
import cc.js.sora.fight.skill.heart.HelenaHuangjia;
import cc.js.sora.fight.skill.heart.HelenaTuji;
import cc.js.sora.fight.skill.heart.Huoyanshen;
import cc.js.sora.fight.skill.heart.KayuraJingdi;
import cc.js.sora.fight.skill.heart.KayuraXingli;
import cc.js.sora.fight.skill.heart.KreugerTheWickedFashi;
import cc.js.sora.fight.skill.heart.LandiusDayuanshuai;
import cc.js.sora.fight.skill.heart.LandiusHuangqi;
import cc.js.sora.fight.skill.heart.LedynShengqi;
import cc.js.sora.fight.skill.heart.LedynWangzhe;
import cc.js.sora.fight.skill.heart.LordOfCrimsonXuejing;
import cc.js.sora.fight.skill.heart.LordOfCrimsonYixing;
import cc.js.sora.fight.skill.heart.LukeDazhujiao;
import cc.js.sora.fight.skill.heart.LukeYinshi;
import cc.js.sora.fight.skill.heart.LunaGongqi;
import cc.js.sora.fight.skill.heart.LunaShengtianma;
import cc.js.sora.fight.skill.heart.RachelMoneng;
import cc.js.sora.fight.skill.heart.RachelShenshi;
import cc.js.sora.fight.skill.heart.RianaXianzhi;
import cc.js.sora.fight.skill.heart.RianaYinshi;
import cc.js.sora.fight.skill.heart.RozencielLingguang;
import cc.js.sora.fight.skill.heart.RozencielShuijing;
import cc.js.sora.fight.skill.heart.SchelfanielWangnv;
import cc.js.sora.fight.skill.heart.SchelfanielWushi;
import cc.js.sora.fight.skill.heart.SherrySP;
import cc.js.sora.fight.skill.heart.TsubameYing;
import cc.js.sora.fight.skill.heart.TsubameYouxia;
import cc.js.sora.fight.skill.heart.VirashHaidou;
import cc.js.sora.fight.skill.heart.VirashJianshi;
import cc.js.sora.fight.skill.heart.YuliaShengjian;
import cc.js.sora.fight.skill.heart.YuusukeZhentan;
import cc.js.sora.fight.skill.passivity.BloodBattle;
import cc.js.sora.fight.skill.passivity.Xinyang;
import cc.js.sora.fight.skill.soldier.Airenmaoxianzhe;
import cc.js.sora.fight.skill.soldier.Anheiweidui;
import cc.js.sora.fight.skill.soldier.Anjingling;
import cc.js.sora.fight.skill.soldier.Anshazhe;
import cc.js.sora.fight.skill.soldier.Anyingbaifuzhang;
import cc.js.sora.fight.skill.soldier.Chaoxijingling;
import cc.js.sora.fight.skill.soldier.Dajingling;
import cc.js.sora.fight.skill.soldier.Diyuquan;
import cc.js.sora.fight.skill.soldier.Dujiaoshou;
import cc.js.sora.fight.skill.soldier.Fangzhenliebing;
import cc.js.sora.fight.skill.soldier.Feiwunvshi;
import cc.js.sora.fight.skill.soldier.Gangyiyongshi;
import cc.js.sora.fight.skill.soldier.Gangzonglangren;
import cc.js.sora.fight.skill.soldier.Gaodiyongshi;
import cc.js.sora.fight.skill.soldier.GriffinSkill;
import cc.js.sora.fight.skill.soldier.Guxi;
import cc.js.sora.fight.skill.soldier.Haiguai;
import cc.js.sora.fight.skill.soldier.Huangjiaqibing;
import cc.js.sora.fight.skill.soldier.Huonujujishou;
import cc.js.sora.fight.skill.soldier.Jiamiannvpu;
import cc.js.sora.fight.skill.soldier.Jinjilianjinshi;
import cc.js.sora.fight.skill.soldier.Jinweibubing;
import cc.js.sora.fight.skill.soldier.Jinweiqiangbing;
import cc.js.sora.fight.skill.soldier.Jinweiqibing;
import cc.js.sora.fight.skill.soldier.Jiwushen;
import cc.js.sora.fight.skill.soldier.Jixieqishi;
import cc.js.sora.fight.skill.soldier.Kuangrezhe;
import cc.js.sora.fight.skill.soldier.Kuangshouren;
import cc.js.sora.fight.skill.soldier.Kuangzhanshi;
import cc.js.sora.fight.skill.soldier.LobsterSkill;
import cc.js.sora.fight.skill.soldier.Longqi;
import cc.js.sora.fight.skill.soldier.Longyizhanshi;
import cc.js.sora.fight.skill.soldier.Manzuyongshi;
import cc.js.sora.fight.skill.soldier.Moxie;
import cc.js.sora.fight.skill.soldier.Nanwu;
import cc.js.sora.fight.skill.soldier.Qumoshi;
import cc.js.sora.fight.skill.soldier.Renjutongling;
import cc.js.sora.fight.skill.soldier.Senlinjisi;
import cc.js.sora.fight.skill.soldier.Shengdianqishi;
import cc.js.sora.fight.skill.soldier.Shengtianma;
import cc.js.sora.fight.skill.soldier.Shenguan;
import cc.js.sora.fight.skill.soldier.Shenguanqishi;
import cc.js.sora.fight.skill.soldier.Shenyanghaidao;
import cc.js.sora.fight.skill.soldier.Shirenjumo;
import cc.js.sora.fight.skill.soldier.Shixianggui;
import cc.js.sora.fight.skill.soldier.Shurenshouwei;
import cc.js.sora.fight.skill.soldier.Silingqishi;
import cc.js.sora.fight.skill.soldier.Sutigaizaozhe;
import cc.js.sora.fight.skill.soldier.Tiankongsheshou;
import cc.js.sora.fight.skill.soldier.Tianqinqinwei;
import cc.js.sora.fight.skill.soldier.Tianshi;
import cc.js.sora.fight.skill.soldier.Toushiche;
import cc.js.sora.fight.skill.soldier.Tujinuqibing;
import cc.js.sora.fight.skill.soldier.Wangnvqinwei;
import cc.js.sora.fight.skill.soldier.WizardSkill;
import cc.js.sora.fight.skill.soldier.Wumianzhe;
import cc.js.sora.fight.skill.soldier.Wunv;
import cc.js.sora.fight.skill.soldier.Wushi;
import cc.js.sora.fight.skill.soldier.Xixuebianfu;
import cc.js.sora.fight.skill.soldier.Xiyidaoke;
import cc.js.sora.fight.skill.soldier.Xizuizhe;
import cc.js.sora.fight.skill.soldier.Xuanfengyouqibing;
import cc.js.sora.fight.skill.soldier.Yanshi;
import cc.js.sora.fight.skill.soldier.Yaojinggongqi;
import cc.js.sora.fight.skill.soldier.Zhongjibaifuzhang;
import cc.js.sora.fight.skill.soldier.Zhongzhuangbubing;
import cc.js.sora.fight.skill.soldier.Zhongzhuangkulou;
import cc.js.sora.fight.skill.soldier.Zhongzhuangqiangbing;
import cc.js.sora.fight.skill.soldier.Zhongzhuangqibing;
import cc.js.sora.fight.skill.soldier.Zhumojingling;
import cc.js.sora.fight.skill.support.BernhardtSuper;
import cc.js.sora.fight.skill.support.Bihuzhijian;
import cc.js.sora.fight.skill.support.ElwinSuper;
import cc.js.sora.fight.skill.support.HildaSuper;
import cc.js.sora.fight.skill.support.LunaHalo;
import cc.js.sora.fight.skill.support.Shenji;
import cc.js.sora.fight.skill.support.XieshenShield;
import cc.js.sora.fight.skill.support.YuusukeSuper;
import cc.js.sora.fight.skill.support.ZillagodSuper;
import cc.js.sora.fight.skill.talent.AresTalent;
import cc.js.sora.fight.skill.talent.BernhardtTalent;
import cc.js.sora.fight.skill.talent.BozelTalent;
import cc.js.sora.fight.skill.talent.ElwinTalent;
import cc.js.sora.fight.skill.talent.HildaTalent;
import cc.js.sora.fight.skill.talent.HimikoTalent;
import cc.js.sora.fight.skill.talent.KayuraTalent;
import cc.js.sora.fight.skill.talent.LandiusTalent;
import cc.js.sora.fight.skill.talent.LedynTalent;
import cc.js.sora.fight.skill.talent.LicoriceTalent;
import cc.js.sora.fight.skill.talent.LightOfGenesisTalent;
import cc.js.sora.fight.skill.talent.ListellTalent;
import cc.js.sora.fight.skill.talent.MagusOfTheTreeTalent;
import cc.js.sora.fight.skill.talent.MarielTalent;
import cc.js.sora.fight.skill.talent.PatyleTalent;
import cc.js.sora.fight.skill.talent.ReanTalent;
import cc.js.sora.fight.skill.talent.RozaliaTalent;
import cc.js.sora.fight.skill.talent.SigmaTalent;
import cc.js.sora.fight.skill.talent.SissiWhiteTalent;
import cc.js.sora.fight.skill.talent.TowaTalent;
import cc.js.sora.fight.skill.talent.WernerTalent;
import cc.js.sora.fight.skill.talent.ZalrahdaTalent;
import cc.js.sora.fight.skill.talent.ZillagodTalent;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SkillService {

	@Autowired
	HeroRepository heroRepository;

	@Autowired
	SoldierRepository soldierRepository;

	@Autowired
	ActionRepository actionRepository;

	BarrackSkills barrackSkills = new BarrackSkills();

	List<Long> globalSkills = Lists.newArrayList(Skill.SuperBuff, Skill.Shenji, Skill.XieshenShield, Skill.Bihuzhijian, Skill.LunaHalo);

	List<Long> ehanceSkills = Lists.newArrayList(0L, Skill.WindEnhance, Skill.ManyueEnhance, Skill.MoshuEnhance, 0L,
			Skill.FuriousEnhance, Skill.LieriEnhance, Skill.LiuxingEnhance, 0L, Skill.ShuijingEnhance, 0L, Skill.DashuEnhance, 0L, Skill.GangtieEnhance);

	public Map<Long, Skill> skills = Maps.newHashMap();

	public SkillService() {
		init();
	}

	private void init() {
		log.info("init skills");
		skills.clear();

		// equip
		
		registerSkill(Skill.Zuihouzhifu, new LastSuit());
		registerSkill(Skill.Lage, new Lage());
		registerSkill(Skill.Xunzhang, new Xunzhang());
		registerSkill(Skill.Yuguan, new Yuguan());
		registerSkill(Skill.Erzhui, new Erzhui());
		registerSkill(Skill.Shenpan, new Shenpan());
		registerSkill(Skill.Tulong, new Tulong());
		registerSkill(Skill.Shenyi, new Shenyi());
		registerSkill(Skill.Yicai, new Yicai());
		registerSkill(Skill.Jixianmogong, new Jixianmogong());
		registerSkill(Skill.Tier, new Tier());
		registerSkill(Skill.Shijieshu, new Shijieshu());
		registerSkill(Skill.Shuijingfengci, new Shuijingfengci());
		registerSkill(Skill.Zhenshizijia, new Zhenshizijia());
		registerSkill(Skill.Chenhun, new Chenhun());
		registerSkill(Skill.Cangbaizhizhang, new Cangbaizhizhang());
		registerSkill(Skill.Mingxiang, new Mingxiang());
		registerSkill(1053L, new Daditou());
		registerSkill(1013L, new Jingmian());
		registerSkill(1064L, new Feiyingxingdou());
		registerSkill(1065L, new Rongyao());
		registerSkill(1066L, new JingjiHuangguan());
		registerSkill(1067L, new Canyue());
		registerSkill(1057L, new Chenshi());
		registerSkill(1071L, new Shouhuzhe());
		registerSkill(1023L, new Mimier());
		registerSkill(1074L, new Zuzhouzhiqiang());
		registerSkill(1075L, new Wuleer());
		registerSkill(1036L, new Yuezhihuiyi());
		
		registerSkill(Skill.WindEnhance, new WindEnhance());
		registerSkill(Skill.FuriousEnhance, new FuriousEnhance());
		registerSkill(Skill.ManyueEnhance, new ManyueEnhance());
		registerSkill(Skill.MoshuEnhance, new MoshuEnhance());
		registerSkill(Skill.ShuijingEnhance, new ShuijingEnhance());

		registerSkill(Skill.GangtieEnhance, new GangtieEnhance());
		registerSkill(Skill.LiuxingEnhance, new LiuxingEnhance());
		registerSkill(Skill.LieriEnhance, new LieriEnhance());
		registerSkill(Skill.DashuEnhance, new DashuEnhance());
		
		registerSkill(Skill.BloodBattle, new BloodBattle());
		registerSkill(Skill.Xinyang, new Xinyang());

		// hero
		registerSkill(Skill.PatyleTalent, new PatyleTalent());
		registerSkill(Skill.TowaTalent, new TowaTalent());

		registerSkill(Skill.MarielTalent, new MarielTalent());
		registerSkill(Skill.HildaTalent, new HildaTalent());
		registerSkill(Skill.WernerTalent, new WernerTalent());
		registerSkill(Skill.LightOfGenesisTalent, new LightOfGenesisTalent());
		
		registerSkill(58L, new LandiusHuangqi());
		registerSkill(10058L, new LandiusDayuanshuai());
		registerSkill(Skill.LedynTalent, new LedynTalent());
		registerSkill(Skill.HimikoTalent, new HimikoTalent());
		registerSkill(Skill.MagusOfTheTreeTalent, new MagusOfTheTreeTalent());
		registerSkill(Skill.ReanTalent, new ReanTalent());
		registerSkill(Skill.AresTalent, new AresTalent());
		registerSkill(Skill.RozaliaTalent, new RozaliaTalent());
		registerSkill(Skill.ElwinTalent, new ElwinTalent());
		registerSkill(Skill.ZalrahdaTalent, new ZalrahdaTalent());
		registerSkill(Skill.ZillagodTalent, new ZillagodTalent());
		registerSkill(Skill.BernhardtTalent, new BernhardtTalent());
		registerSkill(Skill.ListellTalent, new ListellTalent());
		registerSkill(93L, new KayuraXingli());
		registerSkill(10093L, new KayuraJingdi());
		registerSkill(Skill.BozelTalent, new BozelTalent());
		registerSkill(Skill.SissiWhiteTalent, new SissiWhiteTalent());
		registerSkill(Skill.LicoriceTalent, new LicoriceTalent());
		registerSkill(44L, new YuliaShengjian());
		registerSkill(52L, new SigmaTalent());
		registerSkill(94L, new Huoyanshen());
		registerSkill(3L, new KreugerTheWickedFashi());
		registerSkill(16L, new FlorentiaZaixiang());
		registerSkill(10016L, new FlorentiaJunshi());
		registerSkill(11L, new RozencielLingguang());
		registerSkill(10011L, new RozencielShuijing());
		registerSkill(17L, new TsubameYing());
		registerSkill(10017L, new TsubameYouxia());
		registerSkill(37L, new EmiliaShengqi());
		registerSkill(10037L, new EmiliaShengqiang());
		registerSkill(95L, new FreyaSP());
		registerSkill(96L, new LukeYinshi());
		registerSkill(10096L, new LukeDazhujiao());
		registerSkill(19L, new HelenaTuji());
		registerSkill(10019L, new HelenaHuangjia());
		registerSkill(97L, new AutokratoIVHuangdi());
		registerSkill(10097L, new AutokratoIVHuangjia());
		registerSkill(77L, new SherrySP());
		registerSkill(40L, new YuusukeZhentan());
		registerSkill(13L, new AlbedoHuzhu());
		registerSkill(30L, new DeedlitJingling());
		
		registerSkill(75L, new RianaYinshi());
		registerSkill(10075L, new RianaXianzhi());
		
		registerSkill(92L, new LordOfCrimsonXuejing());
		registerSkill(10092L, new LordOfCrimsonYixing());
		
		registerSkill(26L, new EluciaHaiqi());
		registerSkill(10026L, new EluciaHuangjia());
		
		registerSkill(68L, new LunaShengtianma());
		registerSkill(10068L, new LunaGongqi());
		registerSkill(66L, new AlutemuratSP());
		
		registerSkill(32L, new BrendaDoushen());
		registerSkill(10032L, new BrendaHuangjia());
		
		registerSkill(35L, new VirashHaidou());
		registerSkill(10035L, new VirashJianshi());
		
		registerSkill(46L, new AlustrielLangqi());
		
		registerSkill(64L, new SchelfanielWangnv());
		registerSkill(10064L, new SchelfanielWushi());
		
		registerSkill(59L, new RachelMoneng());
		registerSkill(10059L, new RachelShenshi());
		
		registerSkill(78L, new LedynWangzhe());
		registerSkill(10078L, new LedynShengqi());
		
		// soldier
		registerSkill(Skill.MonvSkill, new WizardSkill());
		registerSkill(Skill.HuangjiashijiuSkill, new GriffinSkill());
		registerSkill(Skill.LongxiajushouSkill, new LobsterSkill());
		registerSkill(Skill.Gangyiyongshi, new Gangyiyongshi());
		registerSkill(Skill.Gangzonglangren, new Gangzonglangren());
		registerSkill(Skill.Gaodiyongshi, new Gaodiyongshi());
		registerSkill(Skill.Dujiaoshou, new Dujiaoshou());
		registerSkill(Skill.Fangzhenliebing, new Fangzhenliebing());
		registerSkill(Skill.Huangjiaqibing, new Huangjiaqibing());
		registerSkill(Skill.Jiamiannvpu, new Jiamiannvpu());
		registerSkill(Skill.Jixieqishi, new Jixieqishi());
		registerSkill(Skill.Kuangrezhe, new Kuangrezhe());
		registerSkill(Skill.Longqi, new Longqi());
		registerSkill(Skill.Senlinjisi, new Senlinjisi());
		registerSkill(Skill.Shixianggui, new Shixianggui());
		registerSkill(Skill.Shurenshouwei, new Shurenshouwei());	
		registerSkill(Skill.Tianshi, new Tianshi());
		registerSkill(Skill.Wumianzhe, new Wumianzhe());
		registerSkill(Skill.Xiyidaoke, new Xiyidaoke());
		registerSkill(Skill.Xizuizhe, new Xizuizhe());
		registerSkill(Skill.Xuanfengyouqibing, new Xuanfengyouqibing());
		registerSkill(Skill.Wunv, new Wunv());
		registerSkill(Skill.Yanshi, new Yanshi());
		registerSkill(Skill.Tiankongsheshou, new Tiankongsheshou());
		registerSkill(Skill.Anjingling, new Anjingling());
		registerSkill(Skill.Feiwunvshi, new Feiwunvshi());
		
		registerSkill(2020L, new Yaojinggongqi());
		registerSkill(2034L, new Jinweibubing());
		registerSkill(2035L, new Jiwushen());
		registerSkill(2036L, new Jinweiqibing());
		registerSkill(2037L, new Shengtianma());
		registerSkill(2038L, new Longyizhanshi());
		registerSkill(2039L, new Qumoshi());
		registerSkill(2040L, new Shenguanqishi());
		registerSkill(2041L, new Anshazhe());
		registerSkill(2042L, new Tujinuqibing());
		registerSkill(2043L, new Huonujujishou());		
		registerSkill(2044L, new Dajingling());
		
		registerSkill(2045L, new Airenmaoxianzhe());
		registerSkill(2046L, new Toushiche());
		registerSkill(2047L, new Jinweiqiangbing());
		registerSkill(2048L, new Anyingbaifuzhang());
		registerSkill(2049L, new Zhongjibaifuzhang());
		registerSkill(2050L, new Zhongzhuangqiangbing());
		registerSkill(2052L, new Kuangzhanshi());
		registerSkill(2053L, new Sutigaizaozhe());
		registerSkill(2055L, new Shirenjumo());
		registerSkill(2056L, new Manzuyongshi());
		registerSkill(2057L, new Anheiweidui());
		registerSkill(2058L, new Kuangshouren());
		registerSkill(2059L, new Haiguai());
		registerSkill(2060L, new Shenyanghaidao());
		registerSkill(2061L, new Renjutongling());
		registerSkill(2062L, new Chaoxijingling());
		registerSkill(2063L, new Jinjilianjinshi());
		registerSkill(2064L, new Nanwu());	
		registerSkill(2065L, new Xixuebianfu());
		registerSkill(2066L, new Diyuquan());
		registerSkill(2067L, new Guxi());
		registerSkill(2068L, new Zhongzhuangqibing());
		registerSkill(2070L, new Tianqinqinwei());
		registerSkill(2072L, new Moxie());
		registerSkill(2074L, new Silingqishi());
		registerSkill(2075L, new Zhumojingling());
		registerSkill(2076L, new Shenguan());
		
		registerSkill(2051L, new Zhongzhuangbubing());
		registerSkill(2054L, new Wangnvqinwei());
		registerSkill(2071L, new Shengdianqishi());
		registerSkill(2073L, new Zhongzhuangkulou());
		
		registerSkill(Skill.Wushi, new Wushi());
		
		// action
		registerSkill(Skill.Shimeng, new DreamAction());
		registerSkill(Skill.Weifengchongzhen, new Weifengchongzhen());
		registerSkill(Skill.Juemingyiji, new Juemingyiji());
		registerSkill(Skill.Huoqiu, new Huoqiu());
		registerSkill(Skill.Anlian, new Anlian());
		registerSkill(Skill.Leiji, new Leiji());
		registerSkill(Skill.Lanxingzhan, new Lanxingzhan());
		registerSkill(Skill.Shengguangzhiyong, new Shengguangzhiyong());
		registerSkill(Skill.Shengyan, new Shengyan());
		registerSkill(Skill.Bingdong, new Bingqiang());
		registerSkill(Skill.Anlong, new Anlong());
		registerSkill(Skill.Shengwangzhiji, new Shengwangzhiji());
		registerSkill(Skill.Yeshouzhenshe, new Yeshouzhenshe());
		registerSkill(1629L, new Binglong());
		registerSkill(1625L, new Shiying());
		registerSkill(1626L, new Liefengzhiji());
		registerSkill(1627L, new Yazhisheji());
		registerSkill(1628L, new Qiangfengjuji());
		registerSkill(1630L, new XunyingShaji());
		registerSkill(1632L, new Bujiechongji());
		registerSkill(1633L, new Zuizhongshenpan());
		registerSkill(1637L, new Lingwan());
		registerSkill(1638L, new Heilingwan());
		registerSkill(1642L, new Jinglingzhixi());
		registerSkill(1643L, new Fengren());
		registerSkill(1646L, new Benlei());
		registerSkill(1648L, new Heiyao());
		
		registerSkill(1653L, new Feicuimoshi());
		registerSkill(1652L, new Feicuipofeng());
		registerSkill(1655L, new Shenfa());
		registerSkill(1658L, new Molizhendang());
	
		registerSkill(Skill.ZillagodSuper, new ZillagodSuper());
		registerSkill(Skill.ElwinSuper, new ElwinSuper());
		registerSkill(Skill.BernhardtSuper, new BernhardtSuper());
		registerSkill(Skill.HildaSuper, new HildaSuper());
		registerSkill(Skill.YuusukeSuper, new YuusukeSuper());

		registerSkill(Skill.SuperBuff, new SuperBuff());
		registerSkill(Skill.Shenji, new Shenji());
		registerSkill(Skill.XieshenShield, new XieshenShield());
		registerSkill(Skill.Bihuzhijian, new Bihuzhijian());
		registerSkill(Skill.LunaHalo, new LunaHalo());
		
		skills.putAll(barrackSkills.getAllBarrackSkills());
	}

	public Map<Long, Skill> getAllSkills() {
		if (skills.size() == 0) {
			init();
		}
		return skills;
	}

	public Skill getSkill(long id) {

		if (skills.size() == 0) {
			init();
		}
		return skills.get(id);

	}

	public void registerSkill(long id, Skill skill) {

		skills.put(id, skill);

	}

	private boolean checkSkillType(int skillType, int roleType) {
		if (roleType == 1) {
			return skillType == 1 || skillType == 3 || skillType == 4 || skillType == 9;
		} else if (roleType == 2) {
			return skillType == 2 || skillType == 3 || skillType == 5 || skillType == 9;
		} else if (roleType == 3) {
			return skillType == 1 || skillType == 8 || skillType == 6 || skillType == 9;
		} else if (roleType == 4) {
			return skillType == 2 || skillType == 8 || skillType == 7 || skillType == 9;
		}
		return false;
	}

	private void loadSkill(String skillList, List<Skill> result, int roleType) {
		if (!StringUtils.isEmpty(skillList)) {
			String[] d = StringUtils.split(skillList, ",");
			for (int i = 0; i < d.length; i++) {
				long skillId = Longs.tryParse(d[i].trim());
				if (this.skills.containsKey(skillId)) {
					if (this.getSkill(skillId) != null) {
						result.add(this.getSkill(skillId));
					}
				}
			}
		}
	}

	public List<Skill> getSkills(Hero hero, Soldier soldier, long actionId, int enhance, Map<String, Equip> equips,
			int roleType) {
		log.info("get skills for roleType:"+roleType);
		//List<Skill> result = new ArrayList<Skill>();
		List<Skill> result = Lists.newCopyOnWriteArrayList();
		if (hero != null) {
			loadSkill(hero.getSkills(), result, roleType);
		}

		if (equips != null) {
			equips.values().stream().forEach(e -> {

				loadSkill(e.getSkills(), result, roleType);

			});
		}

		if (enhance > 0) {
			long enhanceSkill = ehanceSkills.get(enhance);
			if (enhanceSkill > 0) {
				if (this.getSkill(enhanceSkill) != null) {
					result.add(this.getSkill(enhanceSkill));
				}
			}
		}

		if (soldier != null) {
			loadSkill(soldier.getSkills(), result, roleType);

			int soldierType = soldier.getType();
			if (barrackSkills.getSkills(soldierType) != null) {
				result.addAll(barrackSkills.getSkills(soldierType));
			}

		}

		if (actionId > 0) {
			Action action = actionRepository.getOne(actionId);
			loadSkill(action.getSkills(), result, roleType);

		}

		globalSkills.forEach(i -> {
			if (this.skills.containsKey(i)) {
				result.add(this.getSkill(i));
			}
		});

		result.forEach(s -> {
			checkChild(s, result);
		});

		return result.stream().filter(s->checkSkillType(s.getSkillType(), roleType)).collect(Collectors.toList());
	}
	
	private void checkChild(Skill skill, List<Skill> result)
	{
		//log.info("check child for skill:"+skill.getName());
		skill.childSkill().forEach(cs -> {
			result.add(cs);
			checkChild(cs, result);
		});

		
	}

}


