class Module {
    name;
    args = [];
    description = null;
    code;
    
    data = { };

    constructor(data) {
        // super();
        this.name = data.name;
        if (this.name.length === 0) {
            console.error("Module name cannot be empty.");
            this.name = "";
        }
        this.args = data.args;

        this.description = data.description;

        if (typeof data.code === 'function') {
            this.code = data.code;
        } else {
            try {
                this.code = eval(data.code);
            } catch (e) {
                console.error(`Error evaluating module code for ${this.name}: ${e}`);
            }
        }
    }

    destroy() {
        this.data = null;
    }

    execute(...args) {
        return this.code(...args);
    }

    static importData(modules) {
        if(this.data && this.data.length !== 0) {
            for (const instance of this.data) {
                instance.destroy();
            }

            this.data = [];
        }

        this.data = modules.map(module => new this(module));
    }

    static is (string) {
        return true;
    }

    static get (module) {
        if (module instanceof Module) return module;
        else if (typeof module === 'string') {
            return this.data.find(m => m.name === module || m.aliases.includes(module));
        } else {
            throw new Error("Invalid module type.");
        }
    }

    static destroy() {
        for (const module of Module.data) {
            module.destroy();
        }

        super.destroy();
    }

    static async checkCommand(module, interaction, args, channel, user, options = {}) {
        if(!module) return false;
        const command = this.get(module);
        if(!command) return false;
        let execute;
        try {
            execute = await command.code(interaction); // Pass interaction to the function
        } catch (e) {
            console.error(`Error executing module code for ${command.name}: ${e}`);
            // Return a default error response:
            return { 
                type: 4,
                data: {
                    content: `Error executing module code for ${command.name}: ${e}`,
                    ephemeral: true
                }
            };
        }
    
        // Check if execute is a valid response object:
        if (execute && typeof execute === 'object' && execute.data) {
            return execute;
        } else {
            // Return a default error response:
            return { 
                type: 4,
                data: {
                    content: 'Invalid command execution result',
                    ephemeral: true
                }
            };
        }
    }
    

}

module.exports = Module;