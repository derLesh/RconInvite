module.exports = {
    name: 'kick',
    flags: [],
    args: [
      { name: "username", description: "Username of the player to be kicked", required: true },
      { name: "reason", description: "Reason for the ban", required: false }
    ],
    description: 'Kick a user from the server.',
    code: (async function ban(interaction) {
      const { Rcon } = require('rcon-client');
      const { EmbedBuilder } = require('discord.js');
      const username = interaction.options.getString('username');
      const reason = interaction.options.getString('reason');
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

        await rcon.send(`kick ${username} ${reason}`);
        await rcon.send(`tellraw @p [\"\",{\"text\":\"[Discord] \",\"color\":\"dark_purple\",\"bold\":true},{\"text\":\" ${username} \",\"color\":\"white\",\"bold\":false},{\"text\":\" has been kicked from the server! Reason: ${reason}\",\"color\":\"dark_red\",\"bold\":false}]`);
  
        console.log(interaction)

        const embedMessage = new EmbedBuilder()
          .setColor('#B71C1C')
          .setTitle('A new kick has been issued')
          .setAuthor({
            name: interaction.user.username,
            iconURL: interaction.user.avatarURL(),
          })
          .setDescription(`User ${username} has been kicked from the server.`)
          .addFields(
            { name: 'Reason', value: reason, inline: false},
            { name: 'Kicked by', value: interaction.user.username, inline: true },
          )
          .setTimestamp()
          .setFooter({text: 'Kicked using RconInvite', url: 'https://github.com/derLesh/RconInvite'})
        
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