package edu.gatech.coc.cs6422.group16.frontend.commandLineObjects;

import edu.gatech.coc.cs6422.group16.algebraTree.RelationalAlgebraTree;
import edu.gatech.coc.cs6422.group16.frontend.CommandLineInterface;
import edu.gatech.coc.cs6422.group16.parsing.QueryParser;

import java.io.IOException;
import java.util.List;

public class StringQueryCommand extends ProcessQueryCommand
{
    private String filteredCommand;

    @Override
    public void setCommand(String command)
    {
        filteredCommand = CommandLineInterface.preProcessCommand(command);
    }

    @Override
    public String shortHelp()
    {
        return "'SELECT * FROM ...'\tProvide your query directly on the commandline!";
    }

    @Override
    protected List<RelationalAlgebraTree> parseTree() throws IOException
    {
        return QueryParser.parseString(filteredCommand);
    }
}
