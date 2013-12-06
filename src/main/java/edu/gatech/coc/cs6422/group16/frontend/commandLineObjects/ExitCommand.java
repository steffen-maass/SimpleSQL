package edu.gatech.coc.cs6422.group16.frontend.commandLineObjects;

public class ExitCommand implements ICommandLineObject
{
    @Override
    public void execute()
    {
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
        return true;
    }

    @Override
    public void setCommand(String command)
    {
    }

    @Override
    public String shortHelp()
    {
        return "exit\tExits the command-line\tSynonym: q";
    }
}
