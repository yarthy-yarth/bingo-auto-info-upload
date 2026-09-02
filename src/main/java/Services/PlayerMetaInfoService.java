package Services;

import Domain.PlayerMetaInfo;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.loottracker.LootTrackerPlugin;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PlayerMetaInfoService {

    @Inject
    private Client client;

    @Inject
    private ItemManager itemManager;

    @Inject
    LootTrackerPlugin lootTrackerPlugin;

    public PlayerMetaInfoService() { }

    public PlayerMetaInfo getPlayerMetaInfo() {
        String name = client.getLocalPlayer().getName();
        int[] playerArmorIds = client.getLocalPlayer().getPlayerComposition().getEquipmentIds();
        int playerEquipmentCost = Arrays.stream(playerArmorIds)
                .map(id -> itemManager.getItemPrice(id))
                .sum();

        return new PlayerMetaInfo(name, playerEquipmentCost);
    }
}
