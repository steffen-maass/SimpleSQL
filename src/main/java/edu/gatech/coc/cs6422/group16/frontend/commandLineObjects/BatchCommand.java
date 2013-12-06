package edu.gatech.coc.cs6422.group16.frontend.commandLineObjects;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import edu.gatech.coc.cs6422.group16.frontend.CommandLineInterface;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BatchCommand implements ICommandLineObject
{
    private String batchFileName = "";

    private CommandLineInterface cmdInterface;

    public BatchCommand(CommandLineInterface cmdInterface)
    {
        this.cmdInterface = cmdInterface;
    }

    @Override
    public void execute()
    {
        // get all the lines:
        List<String> lines = null;
        try
        {
            File f = new File(batchFileName);
            if (f.exists())
            {
                lines = Files.readLines(f, Charsets.UTF_8);
                for (String command : lines)
                {
                    System.out.println("> " + command);
                    cmdInterface.processString(command);
                }
            }
            else
            {
                System.err.println("Script-File not found: " + batchFileName);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // execute each individual line:
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
        batchFileName = command.substring(3).trim();
    }

    @Override
    public String shortHelp()
    {
        return "run {filename}\tExecutes the commands given in {filename} in sequential order";
    }

    public void setCmdInterface(CommandLineInterface cmdInterface)
    {
        this.cmdInterface = cmdInterface;
    }
}
