package edu.gatech.coc.cs6422.group16.frontend;

import edu.gatech.coc.cs6422.group16.frontend.commandLineObjects.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CommandLineInterface
{
    public enum Operations
    {
        STRING_QUERY, FILE_QUERY, EXIT, CHANGE_OPTIONS, SHOW_OPTIONS, NO_OPERATION, BATCH_MODE, HELP
    }

    public static Operations StringToOperation(String op)
    {
        Operations mode;
        if (op.startsWith("select"))
        {
            mode = Operations.STRING_QUERY;
        }
        else if (op.startsWith("load"))
        {
            mode = Operations.FILE_QUERY;
        }
        else if (op.startsWith("exit") || op.startsWith("q"))
        {
            mode = Operations.EXIT;
        }
        else if (op.startsWith("set"))
        {
            mode = Operations.CHANGE_OPTIONS;
        }
        else if (op.trim().equals("show options"))
        {
            mode = Operations.SHOW_OPTIONS;
        }
        else if (op.startsWith("run"))
        {
            mode = Operations.BATCH_MODE;
        }
        else if (op.startsWith("help"))
        {
            mode = Operations.HELP;
        }
        else
        {
            mode = Operations.NO_OPERATION;
        }
        return mode;
    }

    public static String preProcessCommand(String command)
    {
        return command.trim();
    }

    private final Map<Operations, ICommandLineObject> operationToObject = new HashMap<>();

    public CommandLineInterface()
    {
        operationToObject.put(Operations.CHANGE_OPTIONS, new SetOptionsCommand());
        operationToObject.put(Operations.BATCH_MODE, new BatchCommand(this));
        operationToObject.put(Operations.EXIT, new ExitCommand());
        operationToObject.put(Operations.HELP, new HelpCommand(this));
        operationToObject.put(Operations.SHOW_OPTIONS, new ShowOptionsCommand());
        operationToObject.put(Operations.STRING_QUERY, new StringQueryCommand());
        operationToObject.put(Operations.FILE_QUERY, new FileQueryCommand());
        operationToObject.put(Operations.NO_OPERATION, new NoOperationCommand());
    }

    public Map<Operations, ICommandLineObject> getOperationToObject()
    {
        return operationToObject;
    }

    public void processCommandLine()
    {
        boolean alive = true;
        do
        {
            System.out.println("Enter your query:");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            try
            {
                System.out.print("> ");
                String command = reader.readLine();
                alive = processString(command);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.exit(1);
            }
        } while (alive);
        System.exit(0);
    }

    public boolean processString(String command) throws IOException
    {
        boolean alive = true;
        if (command != null)
        {
            Operations mode;
            mode = StringToOperation(command);
            if (mode == Operations.EXIT)
            {
                alive = false;
            }
            else
            {
                ICommandLineObject commandObject = this.operationToObject.get(mode);
                commandObject.setCommand(command);
                commandObject.execute();
            }
        }
        else
        {
            return false;
        }
        return alive;
    }
}
