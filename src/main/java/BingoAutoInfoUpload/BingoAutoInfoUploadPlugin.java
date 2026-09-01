package BingoAutoInfoUpload;

import Notifiers.LootNotifier;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.events.PlayerLootReceived;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Bingo Auto Info Upload"
)
public class BingoAutoInfoUploadPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private BingoAutoInfoUploadConfig config;

	@Inject
	private LootNotifier lootNotifier;

	@Override
	protected void startUp() throws Exception
	{
		log.debug("Example started! ");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.debug("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	BingoAutoInfoUploadConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BingoAutoInfoUploadConfig.class);
	}

	@Subscribe
	public void onNpcLootReceived(NpcLootReceived event)
	{
		lootNotifier.onNpcLootReceived(event);
	}
}
