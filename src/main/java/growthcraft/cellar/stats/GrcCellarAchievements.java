package growthcraft.cellar.stats;

import java.util.Map;
import java.util.EnumMap;

import growthcraft.core.common.AchievementPageGrowthcraft;
import growthcraft.cellar.GrowthCraftCellar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;

public class GrcCellarAchievements
{
	private final Map<CellarAchievement, Achievement> achievements =
			new EnumMap<>(CellarAchievement.class);

	public GrcCellarAchievements() {
	}

	public void unlock(CellarAchievement a, EntityPlayer player) {
		final Achievement achievement = achievements.get(a);
		if (achievement != null) {
			player.triggerAchievement(achievement);
		}
	}

	public void addStat(CellarAchievement a, EntityPlayer player, int n) {
		final Achievement achievement = achievements.get(a);
		if (achievement != null) {
			player.addStat(achievement, n);
		}
	}
}