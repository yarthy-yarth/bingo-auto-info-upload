package Networking;
import BingoAutoInfoUpload.BingoAutoInfoUploadConfig;
import com.formdev.flatlaf.json.Json;
import com.google.gson.JsonObject;
import okhttp3.*;
import com.google.gson.Gson;
import java.io.IOException;

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

    public void onTick(){

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
        JsonObject message = new JsonObject();
        /*payload.addProperty("player", player);
        payload.addProperty("event", skill);
        payload.addProperty("value", xpGained);*/

        return message;
    }

    //enqueue xp gain event
    public void logXpGained() {



    }
}