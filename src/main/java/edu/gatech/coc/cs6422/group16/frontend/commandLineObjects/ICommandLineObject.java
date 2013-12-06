package edu.gatech.coc.cs6422.group16.frontend.commandLineObjects;

public interface ICommandLineObject
{
    public void execute();

    public String longHelp();

    public boolean providesLongHelp();

    public boolean providesShortHelp();

    public void setCommand(String command);

    public String shortHelp();
}
