package xpparty;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.ExperienceOrb;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.AttackEntityEvent;
import org.spongepowered.api.event.entity.ChangeEntityExperienceEvent;
import org.spongepowered.api.event.entity.CollideEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;

/**
 * Created by Frani on 02/10/2017.
 */
public class PluginEvents {

    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onXp(CollideEntityEvent event, @First Player p) {
        int experience = 0;
        for (Entity entity : event.getEntities()) {
            if (entity instanceof ExperienceOrb) {
                experience += ((ExperienceOrb) entity).experience().getDirect().get();
                entity.remove();
            }
        }

        if (experience > 0) {
            Party party = XPParty.pManager.parties.stream().filter(party1 -> party1.getPlayers().contains(p)).findFirst().orElse(null);
            if (party != null) {
                party.deliverXp(p, experience);
            }
        }
    }

    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onDisconnect(ClientConnectionEvent.Disconnect event, @Root Player p) {
        Party party = XPParty.pManager.parties.stream().filter(party1 -> party1.getPlayers().contains(p)).findFirst().orElse(null);
        if (party != null) {
            if (party.getLeader() == p) {
                party.disband();
                return;
            }
            party.removePlayer(p);
        }
    }

    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onDeath(AttackEntityEvent event, @First EntityDamageSource src) {
        if (event.getTargetEntity() instanceof Player && src.getSource() instanceof Player) {
            Player player = (Player)src.getSource();
            Player target = (Player) event.getTargetEntity();
            Party targetParty = XPParty.pManager.parties.stream().filter(p -> p.getPlayers().contains(target)).findFirst().orElse(null);
            Party playerParty = XPParty.pManager.parties.stream().filter(p -> p.getPlayers().contains(player)).findFirst().orElse(null);
            if (targetParty != null && playerParty != null) {
                if (playerParty == targetParty) {
                    event.setCancelled(true);
                }
            }
        }
    }
}