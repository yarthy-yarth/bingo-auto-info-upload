package Networking;
import BingoAutoInfoUpload.BingoAutoInfoUploadConfig;
import Domain.*;
import com.google.gson.JsonObject;
import okhttp3.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import net.runelite.api.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SheetLogger {


    private final BingoAutoInfoUploadConfig config;

    private final Client client;

    @Inject
    public SheetLogger(BingoAutoInfoUploadConfig config, Client client) {
        this.config = config;
        this.client = client;
    }

    private static final Logger log = LoggerFactory.getLogger(SheetLogger.class);
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .build();
    private static final Gson gson = new Gson();

    private static final ArrayList<BaseDTO> eventQueue = new ArrayList<>();
    private static int ticksSinceLastMessage = 0;


    //called every game tick
    public void onTick(){
        if (ticksSinceLastMessage >= config.ticksPerMessage()){
            ticksSinceLastMessage = 0;
            JsonObject message = buildMessage();
            if (message != null){
                sendMessage(message);
            }
        }else{
            ticksSinceLastMessage++;
        }
    }

    //Notifiers send information to SheetLogger to be batched and sent to google sheet
    //

    public void sendMessage(JsonObject message){
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), gson.toJson(message));

        Request request = new Request.Builder()
                .url(config.webhook())
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.warn("Failed to log to sheet", e);
            }
            @Override
            public void onResponse(Call call, Response response) {
                response.close(); // important: always close the response body
                log.debug("sent to sheet");
            }
        });
    }

    ///Builds message using notifications in queue
    public JsonObject buildMessage(){
        if (eventQueue.isEmpty()){
            return null;
        }
        JsonObject message = new JsonObject();
        int count = 0;

        for(BaseDTO entry : eventQueue){
            Map.Entry<String, JsonObject> entryJsonWithKey = entry.toJsonWithKey();
            message.add(count+"_"+entryJsonWithKey.getKey(), entryJsonWithKey.getValue());

            count++;
        }
        //empty event queue for next batch
        eventQueue.clear();

        return message;
    }

    //enqueue xp gain event
    public void logEvent(BaseDTO event) {

        eventQueue.add(event);

    }
}