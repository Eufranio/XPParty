package xpparty;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frani on 29/09/2017.
 */
public class Party {

    public Party(Player leader) {
        players = new ArrayList<>();
        this.leader = leader;
        players.add(leader);
    }

    private List<Player> players;
    private Player leader;

    public List<Player> getPlayers() {
        return players;
    }

    public Player getLeader() {
        return leader;
    }

    public boolean addPlayer(Player player) {
        if (players.size() >= XPParty.CONFIG.party_limit) return false;
        return players.add(player);
    }

    public void removePlayer(Player player) {
        if (player == leader || players.size() == 1) disband();
        players.remove(player);
        leader.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.left_party.replace("%player%", player.getName())));
    }

    public void deliverXp(Player user, int count) {
        players.forEach(player -> {
            if (user.getWorld() == player.getWorld()) player.offer(Keys.TOTAL_EXPERIENCE, player.get(Keys.TOTAL_EXPERIENCE).get() + count);
        });
    }

    public void disband() {
        players.forEach(p -> p.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.disband_message.replace("%player%", getLeader().getName()))));
        XPParty.pManager.parties.remove(this);
    }

}
