package Domain;

import com.google.gson.JsonObject;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Map;

public class BaseDTO {
    public OffsetDateTime when = OffsetDateTime.now();
    public PlayerMetaInfo playerMetaData;

    public BaseDTO(PlayerMetaInfo playerMetaData) {
        this.playerMetaData = playerMetaData;
    }

    public JsonObject toJson(){
        JsonObject baseMessage = new JsonObject();
        baseMessage.addProperty("time", this.when.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        baseMessage.addProperty("player", this.playerMetaData.playerName);
        return baseMessage;
    }

    public Map.Entry<String, JsonObject> toJsonWithKey(){
        return new AbstractMap.SimpleEntry<>("Base", this.toJson());
    }
}
