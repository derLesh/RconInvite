const Discord = require('discord.js');
const fs = require('fs');
const path = require('path');
const config = require('./config.json');
const { addUser, removeUser } = require('./user.js');

const client = new Discord.Client();

client.once('ready', () => {
  console.log('Starting RconInvite ...');
  setup();
  console.log('Ready to use ... Have fun');
});

client.on('message', message => {
  // Implement your bot logic here
});

function setup() {
    // userlist.json
    try {
      const file = path.join(__dirname, 'users.json');
      if (!fs.existsSync(file)) {
        const userJson = {
          discordName: "",
          discordID: "",
          minecraftName: "",
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
          hostIp: "",
          rconPw: "",
          rconPort: 25575,
          token: ""
        };
        fs.writeFileSync(file, JSON.stringify(configSetup, null, 2));
        console.log('Successful created CONFIG file');
        process.exit(0);
      } else {
        const loadedConfig = JSON.parse(fs.readFileSync(file));
        if (!loadedConfig.token) {
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
  

client.login(config.token);
