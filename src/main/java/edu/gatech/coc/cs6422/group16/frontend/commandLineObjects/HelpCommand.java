package edu.gatech.coc.cs6422.group16.frontend.commandLineObjects;

import edu.gatech.coc.cs6422.group16.frontend.CommandLineInterface;

import java.util.Map;

public class HelpCommand implements ICommandLineObject
{
    private CommandLineInterface cmd;

    public HelpCommand(CommandLineInterface cmd)
    {
        this.cmd = cmd;
    }

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
        String cmd = command.trim();
        // if there is more input, process in long mode, else...
        if (cmd.length() > 4)
        {
            String operation = cmd.substring(4).trim();
            CommandLineInterface.Operations op = CommandLineInterface.StringToOperation(operation);
            ICommandLineObject cmdObject = this.cmd.getOperationToObject().get(op);
            if (cmdObject != null)
            {
                if (cmdObject.providesLongHelp())
                {
                    System.out.println(cmdObject.longHelp());
                }
                else
                {
                    System.err.println("No long help available for '" + operation + "'");
                }
            }
            else
            {
                System.err.println("Command '" + operation + "' not recognized!");
            }
        }
        else
        {
            // ... process in short mode:
            for (Map.Entry<CommandLineInterface.Operations, ICommandLineObject> entry : this.cmd.getOperationToObject
                    ().entrySet())
            {
                if (entry.getValue().providesShortHelp())
                {
                    System.out.println(entry.getValue().shortHelp());
                }
            }
        }
    }

    @Override
    public String shortHelp()
    {
        return "help\tShows this screen...";
    }
}
