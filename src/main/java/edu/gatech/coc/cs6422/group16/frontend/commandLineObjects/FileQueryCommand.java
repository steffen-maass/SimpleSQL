package edu.gatech.coc.cs6422.group16.frontend.commandLineObjects;

import edu.gatech.coc.cs6422.group16.algebraTree.RelationalAlgebraTree;
import edu.gatech.coc.cs6422.group16.frontend.CommandLineInterface;
import edu.gatech.coc.cs6422.group16.parsing.QueryParser;

import java.io.IOException;
import java.util.List;

public class FileQueryCommand extends ProcessQueryCommand
{
    private String filteredFileName;

    @Override
    public void setCommand(String command)
    {
        filteredFileName = CommandLineInterface.preProcessCommand(command.substring(4));
    }

    @Override
    public String shortHelp()
    {
        return "load {filename}\tExecute the sql-statement in the given file and display statistics on the " +
                "commandline.";
    }

    @Override
    protected List<RelationalAlgebraTree> parseTree() throws IOException
    {
        return QueryParser.parseFile(filteredFileName);
    }
}
