package xpparty.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

/**
 * Created by Frani on 29/09/2017.
 */
@ConfigSerializable
public class ConfigCategory {

    @Setting(value = "party-limit", comment = "How many players can join a party?")
    public int party_limit = 3;

    @Setting(value = "invite-message")
    public String invite_message = "&aYou just invited %player% to your party, now he should accept the invite!";

    @Setting(value = "invite-received-message")
    public String invite_received_message = "&a You received an invite to join %player%'s party! Type /party accept to accept it!";

    @Setting(value = "declined-party")
    public String decline_party = "&aYou just declined the party invite!";

    @Setting(value = "declined-party-leader")
    public String declined_party_leader = "&c%player% declined your party invite!";

    @Setting(value = "disband-message")
    public String disband_message = "&c The %player%'s party was disbanded!";

    @Setting(value = "party-full")
    public String party_full = "&cThis party is full!";

    @Setting(value = "accept-party")
    public String accept_party = "&a%player% joined the party!";

    @Setting(value = "left-party")
    public String left_party = "&c%player% just left the party!";

    @Setting(value = "not-leader")
    public String not_leader = "&cYou aren't the leader of your party!";

    @Setting(value = "not-on-party")
    public String not_on_party = "&cYou aren't on a party!";

    @Setting(value = "cannot-invite-yourself")
    public String cannot_invite_yourself = "&cYou cannot invite yourself!";

    @Setting(value = "no-invite-to-accept")
    public String no_invites = "&cYou don't have party invites!";

}
