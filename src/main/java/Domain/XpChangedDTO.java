package Domain;

public class XpChangedDTO extends BaseDTO {
    public String skillName;
    public int skillXpSinceLastUpdate;

    public XpChangedDTO(String skillName, int skillXpSinceLastUpdate, PlayerMetaInfo playerMetaInfo)
    {
        super(playerMetaInfo);

        this.skillName = skillName;
        this.skillXpSinceLastUpdate = skillXpSinceLastUpdate;
    }
}
