package Notifiers;

import BingoAutoInfoUpload.BingoAutoInfoUploadPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Skill;
import net.runelite.api.events.FakeXpDrop;
import net.runelite.api.events.StatChanged;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Enumeration;
import java.util.HashMap;

import static java.lang.Integer.valueOf;

@Slf4j
@Singleton
public class XpNotifier extends BaseNotifier {


    @Inject
    Client client;

    ///current xp amounts for the logged in player.
    HashMap<Skill, Integer> currentXpDictionary = new HashMap<Skill, Integer>();

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
            }
            currentXpDictionary.put(skill, xp);
            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", client.getLocalPlayer().getName() + " Gained: " + skill.toString() + ": " + xpGained, null);
        }

    }
}
