const loadModules = (async (config) => {
    const fs = require('fs');
    const path = require('path');

    const nodeModules = await fs.promises.readdir(__dirname, { withFileTypes: true });

    const loaded = [];

    const dirList = nodeModules.filter(dirent => dirent.isDirectory());
    for (const dir of dirList) {
        let module;
        const modulePath = path.join(__dirname, dir.name, 'index.js');
        try {
            module = require(modulePath);
        } catch (e) {
            console.log('Could not find module: ', modulePath);
        }

        if (module) {
            loaded.push(module);
        } else {
            console.log(`Failed to load module ${dir.name}`)
        }
    }

    return {
        loaded
    }
});

module.exports = { loadModules };