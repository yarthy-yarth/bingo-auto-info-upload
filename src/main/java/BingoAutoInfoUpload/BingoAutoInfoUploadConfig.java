package BingoAutoInfoUpload;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("BingoAutoInfoUploadConfig")
public interface BingoAutoInfoUploadConfig extends Config
{
	@ConfigItem(
			keyName = "xpNotifier",
			name = "Xp Notifier",
			description = "Notifies webhook whenever player gains experience."
	)
	default boolean xpNotifier(){
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
