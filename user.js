const fileName = path.join(__dirname, 'users.json');
let userlist = [];

function loadUserList() {
  try {
    const data = fs.readFileSync(fileName);
    return JSON.parse(data);
  } catch (error) {
    logger.error(error);
    return [];
  }
}

function saveUserList() {
  try {
    fs.writeFileSync(fileName, JSON.stringify(userlist, null, 2));
  } catch (error) {
    logger.error(error);
  }
}

function addUser(discordName, discordID, minecraftName, hasInviteLeft) {
  userlist.push({ discordName, discordID, minecraftName, hasInviteLeft });
  saveUserList();
}

function removeUser(discordName, discordID, minecraftName, hasInviteLeft) {
  userlist = userlist.filter(user =>
    user.discordName !== discordName ||
    user.discordID !== discordID ||
    user.minecraftName !== minecraftName ||
    user.hasInviteLeft !== hasInviteLeft
  );
  saveUserList();
}

userlist = loadUserList();


module.exports = {
    addUser,
    removeUser   
}