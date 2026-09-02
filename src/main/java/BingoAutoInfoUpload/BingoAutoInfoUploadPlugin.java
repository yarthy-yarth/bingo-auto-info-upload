package BingoAutoInfoUpload;

import Networking.SheetLogger;
import Notifiers.BossKillTimeNotifier;
import Notifiers.LootNotifier;
import Notifiers.XpNotifier;
import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.events.PlayerLootReceived;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import okhttp3.OkHttpClient;

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

	@Inject
	private XpNotifier xpNotifier;

	@Inject
	private BossKillTimeNotifier bossKillTimeNotifier;

	@Inject
	private SheetLogger sheetLogger;

	@Override
	protected void startUp() throws Exception
	{

	}

	@Override
	protected void shutDown() throws Exception
	{

	}

	@Subscribe
	public void onGameTick(GameTick event){
		sheetLogger.onTick();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{

	}

	@Provides
	BingoAutoInfoUploadConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BingoAutoInfoUploadConfig.class);
	}

	@Subscribe
	public void onNpcLootReceived(NpcLootReceived event)
	{
		if (config.lootLoggerEnabled())
		{
			lootNotifier.onNpcLootReceived(event);
		}
	}

	@Subscribe
	public void onFakeXpDrop(FakeXpDrop event)
	{
		if (config.xpNotifierEnabled())
		{
			xpNotifier.onFakeXpReceived(event);
		}
	}

	@Subscribe
	public void onStatChanged(StatChanged event) {
		if (config.xpNotifierEnabled())
		{
			xpNotifier.onXpReceived(event);
		}
	}

	@Subscribe
	public void onNpcSpawned(NpcSpawned event) {
		if (config.bossKillTimeEnabled())
		{
			bossKillTimeNotifier.onNpcSpawned(event);
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event) {
		if (config.bossKillTimeEnabled())
		{
			bossKillTimeNotifier.onBossKilled(event);
		}
	}
}
