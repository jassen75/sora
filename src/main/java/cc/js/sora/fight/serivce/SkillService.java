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
import cc.js.sora.fight.skill.action.DreamAction;
import cc.js.sora.fight.skill.action.Huoqiu;
import cc.js.sora.fight.skill.action.Juemingyiji;
import cc.js.sora.fight.skill.action.Lanxingzhan;
import cc.js.sora.fight.skill.action.Leiji;
import cc.js.sora.fight.skill.action.Shengguangzhiyong;
import cc.js.sora.fight.skill.action.Weifengchongzhen;
import cc.js.sora.fight.skill.equip.Cangbaizhizhang;
import cc.js.sora.fight.skill.equip.Chenhun;
import cc.js.sora.fight.skill.equip.Erzhui;
import cc.js.sora.fight.skill.equip.FuriousEnhance;
import cc.js.sora.fight.skill.equip.Jixianmogong;
import cc.js.sora.fight.skill.equip.Lage;
import cc.js.sora.fight.skill.equip.LastSuit;
import cc.js.sora.fight.skill.equip.ManyueEnhance;
import cc.js.sora.fight.skill.equip.MoshuEnhance;
import cc.js.sora.fight.skill.equip.Shenpan;
import cc.js.sora.fight.skill.equip.Shenyi;
import cc.js.sora.fight.skill.equip.Shijieshu;
import cc.js.sora.fight.skill.equip.Shuijingfengci;
import cc.js.sora.fight.skill.equip.Tier;
import cc.js.sora.fight.skill.equip.Tulong;
import cc.js.sora.fight.skill.equip.WindEnhance;
import cc.js.sora.fight.skill.equip.Xunzhang;
import cc.js.sora.fight.skill.equip.Yicai;
import cc.js.sora.fight.skill.equip.Yuguan;
import cc.js.sora.fight.skill.equip.Zhenshizijia;
import cc.js.sora.fight.skill.passivity.BloodBattle;
import cc.js.sora.fight.skill.passivity.Xinyang;
import cc.js.sora.fight.skill.soldier.Anjingling;
import cc.js.sora.fight.skill.soldier.Dujiaoshou;
import cc.js.sora.fight.skill.soldier.Fangzhenliebing;
import cc.js.sora.fight.skill.soldier.Feiwunvshi;
import cc.js.sora.fight.skill.soldier.Gangyiyongshi;
import cc.js.sora.fight.skill.soldier.Gangzonglangren;
import cc.js.sora.fight.skill.soldier.Gaodiyongshi;
import cc.js.sora.fight.skill.soldier.GriffinSkill;
import cc.js.sora.fight.skill.soldier.Huangjiaqibing;
import cc.js.sora.fight.skill.soldier.Jiamiannvpu;
import cc.js.sora.fight.skill.soldier.Jixieqishi;
import cc.js.sora.fight.skill.soldier.Kuangrezhe;
import cc.js.sora.fight.skill.soldier.LobsterSkill;
import cc.js.sora.fight.skill.soldier.Longqi;
import cc.js.sora.fight.skill.soldier.Senlinjisi;
import cc.js.sora.fight.skill.soldier.Shixianggui;
import cc.js.sora.fight.skill.soldier.Shurenshouwei;
import cc.js.sora.fight.skill.soldier.Tiankongsheshou;
import cc.js.sora.fight.skill.soldier.Tianshi;
import cc.js.sora.fight.skill.soldier.WizardSkill;
import cc.js.sora.fight.skill.soldier.Wumianzhe;
import cc.js.sora.fight.skill.soldier.Wunv;
import cc.js.sora.fight.skill.soldier.Xiyidaoke;
import cc.js.sora.fight.skill.soldier.Xizuizhe;
import cc.js.sora.fight.skill.soldier.Xuanfengyouqibing;
import cc.js.sora.fight.skill.soldier.Yanshi;
import cc.js.sora.fight.skill.support.BernhardtSuper;
import cc.js.sora.fight.skill.support.ElwinSuper;
import cc.js.sora.fight.skill.support.Shenji;
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
import cc.js.sora.fight.skill.talent.LightOfGenesisTalent;
import cc.js.sora.fight.skill.talent.ListellTalent;
import cc.js.sora.fight.skill.talent.MagusOfTheTreeTalent;
import cc.js.sora.fight.skill.talent.MarielTalent;
import cc.js.sora.fight.skill.talent.PatyleTalent;
import cc.js.sora.fight.skill.talent.ReanTalent;
import cc.js.sora.fight.skill.talent.RozaliaTalent;
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

	List<Long> globalSkills = Lists.newArrayList(Skill.SuperBuff, Skill.Shenji);

	List<Long> ehanceSkills = Lists.newArrayList(0L, Skill.WindEnhance, Skill.ManyueEnhance, Skill.MoshuEnhance, 0L,
			Skill.FuriousEnhance, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L);

	public Map<Long, Skill> skills = Maps.newHashMap();

	public SkillService() {
		init();
	}

	private void init() {
		log.info("init skills");
		skills.clear();

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
		
		registerSkill(Skill.WindEnhance, new WindEnhance());
		registerSkill(Skill.FuriousEnhance, new FuriousEnhance());
		registerSkill(Skill.ManyueEnhance, new ManyueEnhance());
		registerSkill(Skill.MoshuEnhance, new MoshuEnhance());

		registerSkill(Skill.BloodBattle, new BloodBattle());
		registerSkill(Skill.Xinyang, new Xinyang());

		registerSkill(Skill.PatyleTalent, new PatyleTalent());
		registerSkill(Skill.TowaTalent, new TowaTalent());

		registerSkill(Skill.MarielTalent, new MarielTalent());
		registerSkill(Skill.HildaTalent, new HildaTalent());
		registerSkill(Skill.WernerTalent, new WernerTalent());
		registerSkill(Skill.LightOfGenesisTalent, new LightOfGenesisTalent());
		registerSkill(Skill.LandiusTalent, new LandiusTalent());
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
		registerSkill(Skill.KayuraTalent, new KayuraTalent());
		registerSkill(Skill.BozelTalent, new BozelTalent());
		
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
		
		registerSkill(Skill.Shimeng, new DreamAction());
		registerSkill(Skill.Weifengchongzhen, new Weifengchongzhen());
		registerSkill(Skill.Juemingyiji, new Juemingyiji());
		registerSkill(Skill.Huoqiu, new Huoqiu());
		registerSkill(Skill.Anlian, new Anlian());
		registerSkill(Skill.Leiji, new Leiji());
		registerSkill(Skill.Lanxingzhan, new Lanxingzhan());
		registerSkill(Skill.Shengguangzhiyong, new Shengguangzhiyong());
		
		registerSkill(Skill.ZillagodSuper, new ZillagodSuper());
		registerSkill(Skill.ElwinSuper, new ElwinSuper());
		registerSkill(Skill.BernhardtSuper, new BernhardtSuper());

		registerSkill(Skill.SuperBuff, new SuperBuff());
		registerSkill(Skill.Shenji, new Shenji());
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


