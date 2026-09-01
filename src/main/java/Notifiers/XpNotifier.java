package Notifiers;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Singleton;

@Slf4j
@Singleton
public class XpNotifier extends BaseNotifier {


    // Handles Xp Received
    @Subscribe
    public void onXpReceived()
    {

    }
}
