package SimpleSQL;

import edu.gatech.coc.cs6422.group16.frontend.CommandLineInterface;

public class SimpleSQLMain
{
    public static void main(String[] args) throws Exception
    {
        // call the command-line-interface:
        CommandLineInterface cmd = new CommandLineInterface();
        cmd.processCommandLine();
    }
}
