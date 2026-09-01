package Notifiers;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.NPC;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.loottracker.LootReceived;

import javax.inject.Singleton;

@Slf4j
@Singleton
public class LootNotifier extends BaseNotifier {


    // Handles loot received from killing NPCs
    @Subscribe
    public void onNpcLootReceived(NpcLootReceived event) {
        NPC npc = event.getNpc();
        int id = npc.getId();
        for (ItemStack item : event.getItems())
        {
            log.debug("NPC loot item id = {} quantity = {} npcId = {}", item.getId(), item.getQuantity(), id);
        }
    }

    // Handles other loot received forms; CG, BA, Raids?
    @Subscribe
    public void onLootReceived(LootReceived event) {

    }
}
