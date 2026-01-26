[<img width="1536" height="375" alt="PintoKits_Banner" src="https://github.com/user-attachments/assets/cbfc524a-7934-4e65-8f14-313ac1360a20" />](https://docs.pintocraft.com)

<h1 align="center">PintoKits</h1>
<p align="center">PintoKits is a Minecraft Bukkit/Spigot plugin that allows server administrators to manage custom kits for players.<br>
Kits are defined in a <code>kits.yml</code> file and can be saved, removed, and given to players via commands.</p>

## Features

- Create and save custom kits
- Remove existing kits
- Give kits to players
- Tab completion for kit commands
- Dynamic permissions for kit access
- Starting kits, to give a player a kit on join (optionally only first join)

## Configuration

- The `kits.yml` file stores all kits, edit this file using ingame commands
- The `startingkit.yml` file stores information about the join-based kits
  - `joinkit` refers to the kit that will be given to the player when any player joins.
  - `startingkit` refers to the kit that will be given to all players when they first join.
    - `joined` is a list of players that have joined the server __AND__ have been given the `startingkit`
  - To disable either join-based kit, set the values to a blank value or nonexisting kit

**DO NOT EDIT THE KITS.YML FILE**

## Commands

- `/savekit <kitname>`: Saves the player's current inventory as a kit.
- `/kit <kitname>`: Gives the specified kit to the player.
- `/removekit <kitname>`: Removes the specified kit.

## Permissions

- pintokits.kit (default permission for using kits)
- pintokits.kit.\<kitname> (permission for using a specific kit)
- pintokits.kit.* (permission for using all kits)
- pintokits.savekit (default permission for saving kits)
- pintokits.removekit (default permission for removing kits)
- pintokits.joinkit.disable (disable giving this player join-based kits)

## Support

For issues or feature requests, open an issue on the project's repository.
