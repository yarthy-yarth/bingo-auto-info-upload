package Domain;

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
}
