package Notifiers;

import Networking.SheetLogger;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Skill;
import net.runelite.api.events.FakeXpDrop;
import net.runelite.api.events.StatChanged;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;

@Slf4j
@Singleton
public class XpNotifier extends BaseNotifier {


    ///current xp amounts for the logged in player.
    HashMap<Skill, Integer> currentXpDictionary = new HashMap<Skill, Integer>();

    @Inject
    SheetLogger sheetLogger;

    // Handles Xp Received
    @Subscribe
    public void onFakeXpReceived(FakeXpDrop event)
    {
        Skill skill = event.getSkill();
        int xp = event.getXp();
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Exp Gained:" + skill.toString() + ": " + xp, null);
    }

    @Subscribe
    public void onXpReceived(StatChanged event)
    {
        Skill skill = event.getSkill();
        int xp = event.getXp();

        if (!currentXpDictionary.containsKey(skill)){
            currentXpDictionary.put(skill, xp);
            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", client.getLocalPlayer().getName() + " Initial Xp: " + skill.toString() + ": " + xp, null);
        }
        else{
            int xpGained = (xp - currentXpDictionary.get(skill));
            if (xpGained <0){
                log.error("can't gain negative xp");
                return;
            } else if (xpGained == 0) {
                log.debug("stats changed not experience");
                return;
            }
            //update current xp amount for logged in player
            currentXpDictionary.put(skill, xp);

            //log to in game chat
            String message = client.getLocalPlayer().getName() + " Gained: " + skill.toString() + ": " + xpGained;
            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", message, null);

            //send data to webhook
            sheetLogger.logXpGained(client.getLocalPlayer().getName(), skill.getName(), xpGained);
        }

    }
}
