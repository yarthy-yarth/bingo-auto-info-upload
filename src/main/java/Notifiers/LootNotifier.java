package Notifiers;

import BingoAutoInfoUpload.BingoAutoInfoUploadConfig;
import Domain.LootDTO;
import Domain.PlayerMetaInfo;
import Services.PlayerMetaInfoService;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.loottracker.LootReceived;
import net.runelite.http.api.loottracker.LootRecordType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Slf4j
@Singleton
public class LootNotifier extends BaseNotifier {

    // These might be used later
    private static final String BARROWS = "Barrows";
    private static final String CHAMBERS_OF_XERIC = "Chambers of Xeric";
    private static final String THEATRE_OF_BLOOD = "Theatre of Blood";
    private static final String TOMBS_OF_AMASCUT = "Tombs of Amascut";

    @Inject
    private Client client;

    @Inject
    private BingoAutoInfoUploadConfig config;

    @Inject
    private ItemManager itemManager;

    @Inject
    private PlayerMetaInfoService playerMetaInfoService;

    // Handles loot received from killing NPCs
    @Subscribe
    public void onNpcLootReceived(NpcLootReceived event) {
        String sourceName = event.getNpc() != null && event.getNpc().getName() != null
                ? event.getNpc().getName()
                : "unknown_npc";

        uploadLoot(event.getItems(), sourceName);
    }

    // Handles other loot received forms; CG, Araxxor, Raids, etc.
    @Subscribe
    public void onLootReceived(LootReceived event) {

        Collection<ItemStack> items = event.getItems();
        String eventName = event.getName();

        if (event.getType() == LootRecordType.EVENT) {
            // Will need to extra work to determine if hard mode/challenge mode
            uploadLoot(event.getItems(), eventName);
        }
    }

    public void uploadLoot(Collection<ItemStack> items, String sourceName) {
        PlayerMetaInfo playerMetaInfo = playerMetaInfoService.getPlayerMetaInfo();

        for (ItemStack item : items)
        {
            int quantity = item.getQuantity();
            int itemId = item.getId();
            int itemPrice = itemManager.getItemPrice(itemId);
            String itemName = itemManager.getItemComposition(itemId).getName();

            LootDTO dto = new LootDTO(itemId, quantity, itemName, itemPrice, sourceName, playerMetaInfo);
            client.addChatMessage(ChatMessageType.GAMEMESSAGE,
                    "",
                    playerMetaInfo.playerName + " received " + itemName + " from " + sourceName + ".",
                    null);
        }
    }
}
