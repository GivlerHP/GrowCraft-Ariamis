package growthcraft.cellar.eventhandler;

import java.util.Random;

import growthcraft.cellar.GrowthCraftCellar;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.ServerChatEvent;

/**
 * Makes chat messages look progressively more slurred while the sender is
 * affected by Growthcraft's tipsy potion.
 */
public class EventHandlerTipsyChat
{
	private static final String VOWELS = "aeiouyAEIOUY"
		+ "\u0430\u0435\u0451\u0438\u043e\u0443\u044b\u044d\u044e\u044f"
		+ "\u0410\u0415\u0401\u0418\u041e\u0423\u042b\u042d\u042e\u042f";

	@SubscribeEvent
	public void onServerChat(ServerChatEvent event)
	{
		if (!GrowthCraftCellar.getConfig().tipsyChatEnabled
			|| !event.player.isPotionActive(GrowthCraftCellar.potionTipsy))
		{
			return;
		}

		final PotionEffect effect = event.player.getActivePotionEffect(GrowthCraftCellar.potionTipsy);
		final int level = Math.min(4, effect.getAmplifier() + 1);
		final String slurred = slur(event.message, level, event.player.getRNG());

		event.component = new ChatComponentTranslation(
			"chat.type.text", event.player.func_145748_c_(), slurred);
	}

	static String slur(String message, int level, Random random)
	{
		if (message == null || message.isEmpty() || level <= 0)
		{
			return message;
		}

		final int clampedLevel = Math.min(4, level);
		final float repeatChance = 0.06f + clampedLevel * 0.07f;
		final StringBuilder result = new StringBuilder(message.length() + clampedLevel * 4);
		int changed = 0;

		for (int i = 0; i < message.length(); i++)
		{
			final char character = message.charAt(i);
			result.append(character);

			if (isLetter(character) && random.nextFloat() < repeatChance)
			{
				// At low levels mostly vowels are stretched. At higher levels
				// consonants begin to stutter too.
				if (isVowel(character) || random.nextInt(5) < clampedLevel - 1)
				{
					final int repeats = clampedLevel >= 3 && random.nextBoolean() ? 2 : 1;
					for (int repeat = 0; repeat < repeats; repeat++)
					{
						result.append(Character.toLowerCase(character));
					}
					changed++;
				}
			}
		}

		// Even a short message should show a subtle effect instead of looking
		// completely sober because all random rolls happened to miss.
		if (changed == 0)
		{
			final int vowel = findRandomVowel(message, random);
			if (vowel >= 0)
			{
				result.insert(vowel + 1, Character.toLowerCase(message.charAt(vowel)));
			}
		}

		return result.toString();
	}

	private static int findRandomVowel(String message, Random random)
	{
		int count = 0;
		int selected = -1;
		for (int i = 0; i < message.length(); i++)
		{
			if (isVowel(message.charAt(i)))
			{
				count++;
				if (random.nextInt(count) == 0)
				{
					selected = i;
				}
			}
		}
		return selected;
	}

	private static boolean isLetter(char character)
	{
		return Character.isLetter(character);
	}

	private static boolean isVowel(char character)
	{
		return VOWELS.indexOf(character) >= 0;
	}
}
