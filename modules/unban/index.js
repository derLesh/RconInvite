module.exports = {
    name: 'unban',
    flags: [],
    args: [
      { name: "username", description: "Username of the player to be unbanned", required: true },
    ],
    description: 'Ban a user from the server.',
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
                    content: 'Could not connect to Rcon server. Check your config.json file.',
                    ephemeral: true,
                },
            }
        }

        await rcon.send(`pardon ${username}`);
        await rcon.send(`tellraw @p [\"\",{\"text\":\"[Discord] \",\"color\":\"dark_purple\",\"bold\":true},{\"text\":\" ${username} \",\"color\":\"white\",\"bold\":false},{\"text\":\" has been unbanned!\",\"color\":\"green\",\"bold\":false}]`);
  
        console.log(interaction)

        const embedMessage = new EmbedBuilder()
          .setColor('#B71C1C')
          .setTitle('A new unban has been issued')
          .setAuthor({
            name: interaction.user.username,
            iconURL: interaction.user.avatarURL(),
          })
          .setDescription(`User ${username} has been unbanned from the server.`)
          .addFields(
            { name: 'Unbanned by', value: interaction.user.username, inline: true },
          )
          .setTimestamp()
          .setFooter({text: 'Unbanned using RconInvite', url: 'https://github.com/derLesh/RconInvite'})
        
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