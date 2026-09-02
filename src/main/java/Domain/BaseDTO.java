package Domain;

import java.time.OffsetDateTime;

public class BaseDTO {
    public OffsetDateTime when = OffsetDateTime.now();
    public PlayerMetaInfo playerMetaData;

    public BaseDTO(PlayerMetaInfo playerMetaData) {
        this.playerMetaData = playerMetaData;
    }
}
