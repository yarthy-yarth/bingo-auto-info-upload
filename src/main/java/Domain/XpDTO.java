package Domain;

public class XpDTO extends BaseDTO {
    public String skillName;
    public int skillXpSinceLastUpdate;

    public XpDTO(String skillName, int skillXpSinceLastUpdate, PlayerMetaInfo playerMetaInfo)
    {
        super(playerMetaInfo);

        this.skillName = skillName;
        this.skillXpSinceLastUpdate = skillXpSinceLastUpdate;
    }
}
