# RconInvite
Basic Minecraft Admin Control by using a Discord Bot

## Overview

Available Command:
    - /ban <player> <reason (optional)>
    - /unban <player>
    - /kick <player> <reason (optional)>
    - /whitelist <add/remove> <player>
    - /op <player>

### Installation
Prerequisites: [Node.js](https://nodejs.org) and [npm](https://www.npmjs.com/get-npm) (which comes with Node.js) installed.

1. Clone the repository
2. Run `npm install` to install all required dependencies
3. Run `npm start` to start the bot and initialize the config file
4. Edit **config.json** and the necessary values
    - **mc_ip** - Your Minecraft Server IP
    - **rcon_pw** - Your Minecraft Server RCON Password
    - **rcon_port** - Your Minecraft Server RCON Port
    - **bot_token** - Your Discord Bot Token (Get it [here](https://discordapp.com/developers/applications/me))
5. Run `npm start` again to start the bot

