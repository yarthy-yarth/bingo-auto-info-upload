package Domain;

public class BossKillTimeDTO extends BaseDTO {
    public int timeInTicks;
    public String sourceName;

    public BossKillTimeDTO(int timeInTicks, String sourceName, PlayerMetaInfo playerMetaInfo)
    {
        super(playerMetaInfo);

        this.timeInTicks = timeInTicks;
        this.sourceName = sourceName;
    }
}
