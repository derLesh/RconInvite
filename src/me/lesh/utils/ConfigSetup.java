package me.lesh.utils;

public class ConfigSetup {

    public String getHostIp() {
        return hostIp;
    }

    public String getRconPw() {
        return rconPw;
    }

    public int getRconPort() {
        return rconPort;
    }

    public String getToken(){
        return token;
    }

    private String hostIp = "";
    private String rconPw = "";
    private int rconPort;
    private String token = "";

}
