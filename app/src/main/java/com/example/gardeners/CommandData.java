package com.example.gardeners;

public class CommandData {
    private int commandId;
    private int profileId;
    private String location;
    private String command;
    private String korCommand;
    private boolean isDone;

    public CommandData(int commandId, int profileId, String location, String command, String korCommand, boolean isDone) {
        this.commandId = commandId;
        this.profileId = profileId;
        this.location = location;
        this.command = command;
        this.korCommand = korCommand;
        this.isDone = isDone;
    }

    public int getCommandId() {
        return commandId;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getKorCommand() {
        return korCommand;
    }

    public void setKorCommand(String korCommand) {
        this.korCommand = korCommand;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
