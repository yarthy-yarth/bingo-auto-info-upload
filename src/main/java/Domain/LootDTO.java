package Domain;
import com.google.gson.JsonObject;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Map;

public class LootDTO extends BaseDTO {

    public int quantity;
    public int itemId;
    public int itemPrice;
    public String itemName;
    public String sourceName;

    public LootDTO(int itemId, int quantity, String itemName, int itemPrice, String sourceName, PlayerMetaInfo playerMetaInfo)
    {
        super(playerMetaInfo);

        this.quantity = quantity;
        this.itemId = itemId;
        this.sourceName = sourceName;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
    @Override
    public JsonObject toJson(){
        JsonObject lootMessage = new JsonObject();
        lootMessage.addProperty("time", this.when.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        lootMessage.addProperty("player", this.playerMetaData.playerName);
        lootMessage.addProperty("itemName", this.itemName);
        lootMessage.addProperty("itemPrice", this.itemPrice);
        lootMessage.addProperty("itemQuantity", this.quantity);
        lootMessage.addProperty("ItemSource", this.sourceName);
        return lootMessage;
    }

    @Override
    public Map.Entry<String, JsonObject> toJsonWithKey(){
        return new AbstractMap.SimpleEntry<>("Loot", this.toJson());
    }
}
