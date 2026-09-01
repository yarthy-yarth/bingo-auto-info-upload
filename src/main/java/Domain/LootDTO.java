package Domain;

public class LootDTO {

    public int quantity;
    public int itemId;
    public int itemPrice;
    public String itemName;
    public String sourceName;

    public LootDTO(int itemId, int quantity, String itemName, int itemPrice, String sourceName)
    {
        this.quantity = quantity;
        this.itemId = itemId;
        this.sourceName = sourceName;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString()
    {
        return "LootDTO {" +
                "quantity=" + quantity +
                ", itemId=" + itemId +
                ", itemPrice=" + itemPrice +
                ", itemName='" + itemName + '\'' +
                ", sourceName='" + sourceName + '\'' +
                '}';
    }
}
