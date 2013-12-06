package edu.gatech.coc.cs6422.group16.frontend.commandLineObjects;

public class NoOperationCommand implements ICommandLineObject
{
    @Override
    public void execute()
    {
        System.err.println("Unrecognized command!");
    }

    @Override
    public String longHelp()
    {
        return null;
    }

    @Override
    public boolean providesLongHelp()
    {
        return false;
    }

    @Override
    public boolean providesShortHelp()
    {
        return false;
    }

    @Override
    public void setCommand(String command)
    {
    }

    @Override
    public String shortHelp()
    {
        return null;
    }
}
