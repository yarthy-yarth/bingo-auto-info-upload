package Domain;

import com.google.gson.JsonObject;

import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Map;

public class XpDTO extends BaseDTO {
    public String skillName;
    public int skillXpSinceLastUpdate;

    public XpDTO(String skillName, int skillXpSinceLastUpdate, PlayerMetaInfo playerMetaInfo)
    {
        super(playerMetaInfo);

        this.skillName = skillName;
        this.skillXpSinceLastUpdate = skillXpSinceLastUpdate;
    }

    @Override
    public JsonObject toJson(){
        JsonObject xpMessage = new JsonObject();
        xpMessage.addProperty("time", this.when.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        xpMessage.addProperty("player", this.playerMetaData.playerName);
        xpMessage.addProperty("skill", this.skillName);
        xpMessage.addProperty("xpGained", this.skillXpSinceLastUpdate);
        return xpMessage;
    }

    @Override
    public Map.Entry<String, JsonObject> toJsonWithKey(){
        return new AbstractMap.SimpleEntry<>("XPGained", this.toJson());
    }
}
