package BingoAutoInfoUpload;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("BingoAutoInfoUploadConfig")
public interface BingoAutoInfoUploadConfig extends Config
{
	@ConfigItem(
			keyName = "xpNotifierEnabled",
			name = "Use Xp Notifier ",
			description = "Notifies webhook whenever player gains experience."
	)
	default boolean xpNotifierEnabled(){
		return true;
	}


	@ConfigItem(
			keyName = "BossKillTimeEnabled",
			name = "Boss Kill Time Enabled",
			description = "Notifies webhook whenever player kills a boss and sends the time killed in ticks."
	)
	default boolean bossKillTimeEnabled(){
		return true;
	}

	@ConfigItem(
			keyName = "LootLoggerEnabled",
			name = "Loot Logger Enabled",
			description = "Notifies webhook whenever player gains loot and sends it."
	)
	default boolean lootLoggerEnabled(){
		return true;
	}

	@ConfigItem(
			keyName = "webhook",
			name = "Web Hook",
			description = "webhook to send data to"
	)

	default String webhook()
	{
		return "";
	}
}
