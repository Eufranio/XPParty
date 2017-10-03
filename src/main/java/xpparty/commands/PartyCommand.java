package xpparty.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import xpparty.Party;
import xpparty.XPParty;

/**
 * Created by Frani on 30/09/2017.
 */
public class PartyCommand {

    private CommandSpec invite = CommandSpec.builder()
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    Player player = args.<Player>getOne("player").get();
                    XPParty.pManager.sendInvite(player, XPParty.pManager.getOrCreateParty((Player)src));
                    return CommandResult.success();
                }
            })
            .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
            .build();

    private CommandSpec accept = CommandSpec.builder()
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    if (XPParty.pManager.invites.containsKey((Player)src)) {
                        Party party = XPParty.pManager.invites.remove((Player)src);
                        if (!party.addPlayer((Player)src)) {
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.party_full));
                        }
                        party.getLeader().sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.accept_party.replace("%player%", src.getName())));
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.accept_party.replace("%player%", src.getName())));
                    } else {
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.no_invites));
                    }
                    return CommandResult.success();
                }
            })
            .build();

    private CommandSpec decline = CommandSpec.builder()
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    if (XPParty.pManager.invites.containsKey((Player)src)) {
                        Party party = XPParty.pManager.invites.remove((Player)src);
                        party.getLeader().sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.declined_party_leader
                            .replace("%player%", src.getName())));
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.decline_party));
                    } else {
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.no_invites));
                    }
                    return CommandResult.success();
                }
            })
            .build();

    private CommandSpec leave = CommandSpec.builder()
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    if (XPParty.pManager.parties.stream().anyMatch(party -> party.getPlayers().contains((Player)src))) {
                        Party party = XPParty.pManager.parties.stream().filter(party1 -> party1.getPlayers().contains((Player)src)).findFirst().get();
                        party.removePlayer((Player)src);
                        return CommandResult.success();
                    }
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.not_on_party));
                    return CommandResult.success();
                }
            })
            .build();

    private CommandSpec disband = CommandSpec.builder()
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    if (XPParty.pManager.parties.stream().anyMatch(party -> party.getLeader() == (Player)src)) {
                        XPParty.pManager.parties.stream().filter(p -> p.getLeader() == (Player)src).findFirst().get().disband();
                    } else {
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(XPParty.CONFIG.not_leader));
                    }
                    return CommandResult.success();
                }
            })
            .build();

    CommandSpec help = CommandSpec.builder()
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("\n&aAvailable commands: \n" +
                            "&9 /party invite <player> &7> &aInvites <player> to your party\n" +
                            "&9 /party decline&7 > &aDeclines an party invite\n" +
                            "&9 /party leave &7> &aLeaves your current party\n" +
                            "&9 /party disband &7> &aDisbands your party, if you're the leader\n" +
                            "&9 /party accept&7 > &aAccepts an party invite\n"
                    ));
                    return CommandResult.success();
                }
            })
            .permission("party.general")
            .child(invite, "invite")
            .child(accept, "accept")
            .child(decline, "decline")
            .child(leave, "leave")
            .child(disband, "disband")
            .build();

    public void register(XPParty instance) {
        Sponge.getCommandManager().register(instance, help, "party");
    }

}
