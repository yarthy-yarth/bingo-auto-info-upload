package Notifiers;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.gameval.NpcID;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class BossKillTimeNotifier extends BaseNotifier {

    private static final String FIGHT_DURATION_REGEX = "Fight duration:\\s*(?:(\\d+):)?(\\d+):(\\d{2})\\.(\\d{2})";
    private static final String COMPLETION_TIME_REGEX = "completion time:\\s*(?:(\\d+):)?(\\d+):(\\d{2})\\.(\\d{2})";
    private static final String DURATION_REGEX = "Duration:\\s+(\\d+):(\\d{2}):(\\d{2})\\.(\\d{2})";
    private static final String SUBDUED_REGEX = "Subdued in\\s+(\\d+):(\\d{2})\\.(\\d{2})";

    // List of boss ids
    private static final Set<Integer> BOSS_IDS = Set.of(
            NpcID.ABYSSALSIRE_SIRE_APOCALYPSE,
            NpcID.HYDRABOSS_FINALDEATH,
            NpcID.AMOXLIATL,
            NpcID.ARAXXOR_DEAD,
            NpcID.CALLISTO_SINGLES,
            NpcID.COWBOSS,
            NpcID.COWBOSS_HARDMODE,
            NpcID.GB_MOSSGIANT,
            NpcID.CALLISTO,
            NpcID.VETION_2_SINGLE,
            NpcID.CERBERUS_ATTACKING,
            NpcID.OLM_HEAD,
            NpcID.CHAOSELEMENTAL,
            NpcID.CRAZY_ARCHAEOLOGIST,
            NpcID.CHAOS_FANATIC,
            NpcID.GODWARS_SARADOMIN_AVATAR,
            NpcID.CORP_BEAST,
            NpcID.DAGCAVE_MELEE_BOSS,
            NpcID.DAGCAVE_MAGIC_BOSS,
            NpcID.DAGCAVE_RANGED_BOSS,
            NpcID.FOSSIL_CRAZY_ARCHAEOLOGIST,
            NpcID.DOM_BOSS,
            NpcID.DUKE_SUCELLUS_AWAKE,
            NpcID.GODWARS_BANDOS_AVATAR,
            NpcID.MOLE_GIANT,
            NpcID.GARGBOSS_DAWN_SPAWN,
            NpcID.HESPORI,
            NpcID.KALPHITE_QUEEN,
            NpcID.KING_DRAGON,
            NpcID.SLAYER_KRAKEN_BOSS,
            NpcID.GODWARS_ARMADYL_AVATAR,
            NpcID.GODWARS_ZAMORAK_AVATAR,
            NpcID.MAD_ANGEL,
            NpcID.MAGGOT_KING,
            NpcID.TRAIL_MIMIC_COMBAT,
            NpcID.NEX,
            NpcID.NIGHTMARE_DYING,
            NpcID.NIGHTMARE_CHALLENGE_DYING,
            NpcID.HILLGIANT_BOSS,
            NpcID.MUSPAH_FINAL,
            NpcID.SARACHNIS,
            NpcID.SCORPIA,
            NpcID.RAT_BOSS_NORMAL,
            NpcID.RAT_BOSS_INSTANCE,
            NpcID.GRYPHON_BOSS,
            NpcID.CATA_BOSS,
            NpcID.COLOSSEUM_BOSS_SEATED,
            NpcID.VENENATIS_SINGLES,
            NpcID.TEMPOROSS_BOSS_READY,
            NpcID.CRYSTAL_HUNLLEF_DEATH,
            NpcID.CRYSTAL_HUNLLEF_DEATH_HM,
            NpcID.HUEY_HEAD,
            NpcID.LEVIATHAN,
            NpcID.RT_FIRE_QUEEN_INACTIVE,
            NpcID.WHISPERER,
            NpcID.VERZIK_DEATH_BAT,
            NpcID.SMOKE_DEVIL_BOSS,
            NpcID.TOA_WARDEN_P3_DEATH_ELIDINIS,
            NpcID.TOA_WARDEN_P3_DEATH_TUMEKEN,
            NpcID.INFERNO_TZKALZUK_PLACEHOLDER,
            NpcID.TZHAAR_FIGHTCAVE_SWARM_BOSS,
            NpcID.VARDORVIS,
            NpcID.VENENATIS,
            NpcID.VETION,
            NpcID.VORKATH,
            NpcID.WINT_TOAD,
            NpcID.YAMA,
            NpcID.ZALCANO,
            NpcID.SNAKEBOSS_BOSS_RANGED,
            NpcID.BARROWS_VERAC,
            NpcID.BARROWS_AHRIM,
            NpcID.BARROWS_DHAROK,
            NpcID.BARROWS_GUTHAN,
            NpcID.BARROWS_TORAG,
            NpcID.BARROWS_KARIL,
            NpcID.PMOON_BOSS_BLOOD_MOON,
            NpcID.PMOON_BOSS_BLUE_MOON,
            NpcID.PMOON_BOSS_ECLIPSE_MOON
    );

    @Inject
    private Client client;

    // Tracks what boss player is killing
    private NPC currentBoss;


    public void onNpcSpawned(NpcSpawned event) {
        NPC npc = event.getNpc();

        // Check to see if it's a boss
        if (BOSS_IDS.contains(npc.getId()))
        {
            currentBoss = npc;
            log.debug("{} has spawned.", currentBoss.getName());
        }
    }

    public void onNpcDespawned(NpcDespawned event) {
        if (event.getNpc() == currentBoss) {
            log.debug("{} has despawned.", currentBoss.getName());
            currentBoss = null;
        }
    }

    public void onBossKilled(ChatMessage event) {


        // We only want messages from the game
        if (event.getType() != ChatMessageType.GAMEMESSAGE) {
            return;
        }

        String message = event.getMessage();
        int timeInTicks = -1;

        // Most normal bosses
        if (message.toLowerCase(Locale.ROOT).contains("Fight Duration".toLowerCase(Locale.ROOT))) {
            timeInTicks = parseFightDurationTicks(message, FIGHT_DURATION_REGEX);
        }
        // Chambers, Doom, Zuk, ???
        else if (message.toLowerCase(Locale.ROOT).contains("Duration".toLowerCase(Locale.ROOT))) {
            timeInTicks = parseFightDurationTicks(message, DURATION_REGEX);
        }
        // Tombs of Amascut, Theatre of Blood
        else if (message.toLowerCase(Locale.ROOT).contains("Completion Time".toLowerCase(Locale.ROOT))) {
            timeInTicks = parseFightDurationTicks(message, COMPLETION_TIME_REGEX);
        }
        // Tempoross
        else if (message.toLowerCase(Locale.ROOT).contains("Subdued".toLowerCase(Locale.ROOT))) {
            timeInTicks = parseFightDurationTicks(message, COMPLETION_TIME_REGEX);
        }

        if (timeInTicks == -1) {
            return;
        }

        uploadKillTime(timeInTicks);
    }

    private void uploadKillTime(int timeInTicks) {
        client.addChatMessage(ChatMessageType.GAMEMESSAGE,
                "",
                client.getLocalPlayer().getName() + " killed " + currentBoss.getName() + " in " + timeInTicks + " ticks.",
                null);
    }

    private int parseFightDurationTicks(String fightDurationChatMessage, String regex) {
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(fightDurationChatMessage);

        if (!matcher.find())
        {
            return -1;
        }

        int hours = matcher.group(1) != null
                ? Integer.parseInt(matcher.group(1))
                : 0;

        int minutes = Integer.parseInt(matcher.group(2));
        int seconds = Integer.parseInt(matcher.group(3));
        int hundredths = Integer.parseInt(matcher.group(4));

        int milliseconds =
                hours * 3_600_000 +
                        minutes * 60_000 +
                        seconds * 1_000 +
                        hundredths * 10;

        return Math.round(milliseconds / 600.0f);
    }
}
