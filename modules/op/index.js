module.exports = {
    name: 'op',
    flags: [],
    args: [
      { name: "username", description: "Username of the player to make OP", required: true }
    ],
    description: 'Make a user OP on the server.',
    code: (async function ban(interaction) {
      const { Rcon } = require('rcon-client');
      const { EmbedBuilder } = require('discord.js');
      const username = interaction.options.getString('username');
      const rcon = new Rcon({host: bot.Config.mc_ip, port: bot.Config.rcon_port, password: bot.Config.rcon_pw});
      let answer;
  
      try {
        try {
            await rcon.connect();
        } catch (error) {
            console.log(error);
            return {
                type: 4,
                data: {
                    content: 'Could not connect to Rcon server.',
                    ephemeral: true,
                },
            }
        }

        await rcon.send(`op ${username}`);
        await rcon.send(`tellraw @p [\"\",{\"text\":\"[Discord] \",\"color\":\"dark_purple\",\"bold\":true},{\"text\":\" ${username} \",\"color\":\"white\",\"bold\":false},{\"text\":\" is a new OP on the server!\",\"color\":\"dark_red\",\"bold\":false}]`);
  
        console.log(interaction)

        const embedMessage = new EmbedBuilder()
          .setColor('#B71C1C')
          .setTitle('A new op has been issued')
          .setAuthor({
            name: interaction.user.username,
            iconURL: interaction.user.avatarURL(),
          })
          .setDescription(`User ${username} has become an OP on the server.`)
          .addFields(
            { name: 'Changed by', value: interaction.user.username, inline: true },
          )
          .setTimestamp()
          .setFooter({text: 'OPed using RconInvite', url: 'https://github.com/derLesh/RconInvite'})
        
        answer = {
          type: 4,
          data: {
            embeds: [embedMessage],
            ephemeral: false,
          },
          
        };
      } catch (error) {
        console.log(error);
      } finally {
        await rcon.end();
      }
      
      return answer;
    })
};