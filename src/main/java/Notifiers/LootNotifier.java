package Notifiers;

import Domain.LootDTO;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.NPC;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.loottracker.LootReceived;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class LootNotifier extends BaseNotifier {

    @Inject
    private ItemManager itemManager;

    // Handles loot received from killing NPCs
    public void onNpcLootReceived(NpcLootReceived event) {
        String npcName = event.getNpc().getName();

        for (ItemStack item : event.getItems())
        {
            int quantity = item.getQuantity();
            int itemId = item.getId();
            int itemPrice = itemManager.getItemPrice(itemId);
            String itemName = itemManager.getItemComposition(itemId).getName();


            LootDTO dto = new LootDTO(itemId, quantity, itemName, itemPrice, npcName);

            // Store and send later
            log.debug("NPC loot DTO: {}", dto);
        }
    }

    // Handles other loot received forms; CG, BA, Raids?
    public void onLootReceived(LootReceived event) {

    }
}
