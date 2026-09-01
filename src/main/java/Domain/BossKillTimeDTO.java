package Domain;

public class BossKillTimeDTO {
    public int timeInTicks;
    public String sourceName;
    public String playerName;

    public BossKillTimeDTO(int timeInTicks, String sourceName, String playerName)
    {
        this.timeInTicks = timeInTicks;
        this.sourceName = sourceName;
        this.playerName = playerName;
    }

    @Override
    public String toString()
    {
        return "LootDTO {" +
                ", timeInTicks='" + timeInTicks + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", playerName='" + playerName + '\'' +
                '}';
    }
}
