module.exports = {
    name: 'ban',
    args: [user, reason],
    description: 'Ban a user from the server.',
    code: async function(interaction) {
      const args = interaction.options.map(opt => opt.value);
      const rcon = new rconClient.Rcon({host: hostIp, port: rconPort, password: rconPw});
  
      try {
        await rcon.connect();
  
        let banReason = 'NaN';
        if (args.length > 1) {
          banReason = args.slice(1).join(' ');
        }
        await rcon.send(`ban ${args[0]} ${banReason}`);
        await rcon.send(`tellraw @p [\"\",{\"text\":\"[Discord] \",\"color\":\"dark_purple\",\"bold\":true},{\"text\":\" ${args[0]} \",\"color\":\"white\",\"bold\":false},{\"text\":\" wurde vom Server gebannt! Grund: ${banReason}\",\"color\":\"dark_red\",\"bold\":false}]`);
  
        const embedMessage = new Discord.MessageEmbed()
          .addField(`User ${args[0]} wurde gebannt`, `${interaction.user.username} hat ${args[0]} gebannt.\n Banngrund: ${banReason}`)
          .setColor('#B71C1C');
        
        await interaction.reply({ embeds: [embedMessage] });
      } catch (error) {
        console.log(error);
      } finally {
        await rcon.end();
      }
    }
};