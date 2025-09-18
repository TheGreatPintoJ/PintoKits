# PintoKits

PintoKits is a Minecraft Bukkit/Spigot plugin that allows server administrators to manage custom kits for players. Kits are defined in a `kits.yml` file and can be saved, removed, and given to players via commands.

## Features

- Create and save custom kits
- Remove existing kits
- Give kits to players
- Tab completion for kit commands
- Dynamic permissions for kit access

## Installation

1. Download the PintoKits jarfile from the releases.
2. Place the jar file into your server's `plugins` folder.
3. Start or restart your Minecraft server.

## Configuration

- The plugin generates a `kits.yml` file in the plugin's data folder.
- Use the plguins's commands to edit the kits,
**DO NOT EDIT THE KITS.YML FILE**

## Commands

- `/savekit <kitname>`: Saves the player's current inventory as a kit.
- `/kit <kitname>`: Gives the specified kit to the player.
- `/removekit <kitname>`: Removes the specified kit.

## Permissions

- pintokits.kit (default permission for using kits)
- pintokits.kit.<kitname> (permission for using a specific kit)
- pintokits.kit.* (permission for using all kits)
- pintokits.savekit (default permission for saving kits)
- pintokits.removekit (default permission for removing kits)

## Support

For issues or feature requests, open an issue on the project's repository.
