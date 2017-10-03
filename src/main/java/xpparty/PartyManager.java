package xpparty;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Frani on 30/09/2017.
 */
public class PartyManager {

    public List<Party> parties;
    public Map<Player, Party> invites;

    public PartyManager() {
        this.parties = new ArrayList<>();
        this.invites = new HashMap<>();
    }

    public Party getOrCreateParty(Player player) {
        if (parties.stream().anyMatch(party -> party.getPlayers().contains(player))) {
            return parties.stream().filter(party -> party.getPlayers().contains(player)).findFirst().get();
        }
        Party party = new Party(player);
        parties.add(party);
        return party;
    }

    public void sendInvite(Player p, Party party) {
        if (p != party.getLeader()) {
            party.getLeader().sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.invite_message.replace("%player%", p.getName())));
            p.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.invite_received_message.replace("%player%", party.getLeader().getName())));
            invites.put(p, party);
        } else {
            p.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.cannot_invite_yourself));
        }
    }

}
