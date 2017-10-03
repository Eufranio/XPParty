# XPParty
XPParty is a plugin made for the [R:Craft server](http://cyberconnects2.com/games/rcraft/) that basically adds parties to the game. Parties can have an maximum of N players, where party members share the xp points gained and cannot kill eachother.

## Features
* Adds parties to the game, which can group X players (defined in the config)
* Party members cannot hit/kill eachother
* XP points are shared between party members
* Removes players from a party if they disconnect
* Party leaders can disband the party
* All messages configurable, as well as the party member limit

## How to get started
* Once you installed and loaded the plugin, it will already be working. If you want, you can change the limit of max members of a party on the config.
* Give your players the **party.general** permission, the main permission for the plugin commands
* To create a party, use **/party invite playername**. When **playername** accepts the invite (**/party accept**), a new party will be created
* To add more members to a party, just invite them. The party leader can disband the party with **/party disband**, and members can leave the party using **/party leave**
* Once any of the party members gain XP through experience orbs (such as killing mobs, mining...), all other members of the same party will receive the same XP, and the party members won't be able to kill other members.

## Config
You can see an example of the current config [here](https://gist.github.com/Eufranio/e95030a4f4050545a75e7ad6b3c254c0).

## Permissions
The main permission of this plugin is **party.general**.

## Command List
* **/party**: main and help command of the plugin
* **/party invite playername**: sends a party invite to **playername**
* **/party accept**: accepts an party invite that you've received
* **/party decline**: declines an party invite that you've received
* **/party leave**: leaves the party that you're in
* **/party disband**: disbands/ends the party that you're leader in

If you find any issues, report them to the [plugin's issue tracker](https://github.com/Eufranio/XPParty/issues). If you want, you can donate for me through PayPal, my paypal email is frani@magitechserver.com.