const { SlashCommandBuilder, Partials, GatewayIntentBits, Client } = require('discord.js');
const fs = require('fs');
const path = require('path');
const { addUser, removeUser } = require('./user.js');

let config;
try {
  config = require('./config.json');
} catch (error) {
  console.log("Couldn't find config.json. Creating new one");
  setup();
}

(async function() {
  globalThis.bot = {}
  bot.Module = require('./classes/module.js');
  bot.Config = config;

  this.client = new Client({
    intents: [
      GatewayIntentBits.Guilds,
      GatewayIntentBits.GuildMessages,
      GatewayIntentBits.GuildMembers,
      GatewayIntentBits.MessageContent,
    ],
    partials: [Partials.Channel]
  });
  const { loadModules } = require('./modules/index.js');
  const modules = await loadModules();
  await bot.Module.importData(modules.loaded);



  this.client.once('ready', () => {
    console.log('Starting RconInvite ...');
    setup();

    for(const module of bot.Module.data) {
      try {
        createSlashCommands(this.client, module);
      } catch (e) {
        console.log(e)
      }
    }

    console.log('Ready to use ... Have fun');
  });
  
  this.client.on('interactionCreate', async (interaction) => {
    if(bot.Module.get(interaction.commandName)) {
      const answer = await bot.Module.checkCommand(interaction.commandName, interaction);
      console.log(answer)
      await interaction.reply(answer.data);
    }
  });
  
  this.client.login(config.bot_token);
})();

function setup() {
  try {
    const file = path.join(__dirname, 'users.json');
    if (!fs.existsSync(file)) {
      const userJson = {
        discord_name: "",
        discord_id: "",
        mc_name: "",
        hasInviteLeft: false
      };
      fs.writeFileSync(file, JSON.stringify(userJson, null, 2));
    }
  } catch (error) {
    console.log(error);
  }

  // config.json
  try {
    const file = path.join(__dirname, 'config.json');
    if (!fs.existsSync(file)) {
      const configSetup = {
        mc_ip: "",
        rcon_pw: "",
        rcon_port: 25575,
        bot_token: ""
      };
      fs.writeFileSync(file, JSON.stringify(configSetup, null, 2));
      console.log('Successful created CONFIG file');
      process.exit(0);
    } else {
      const loadedConfig = JSON.parse(fs.readFileSync(file));
      if (!loadedConfig.bot_token) {
        console.log('There is no token. Set your Token for your bot');
        return;
      }
      // Continue checking for other configuration values...
      console.log('Config has been loaded ... ');
    }
  } catch (error) {
    console.log(error);
  }
}

function createSlashCommands(client, module) {
  console.log(module) 
  const command = new SlashCommandBuilder()
    .setName(module.name)
    .setDescription(module.description)
  for (const arg of module.args) {
    command.addStringOption(option => 
      option.setName(arg.name)
        .setDescription(arg.description)
        .setRequired(arg.required)
    );
  }
  
  client.application.commands.create(command);
}


