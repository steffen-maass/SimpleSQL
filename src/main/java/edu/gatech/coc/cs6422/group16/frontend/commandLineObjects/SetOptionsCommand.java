package edu.gatech.coc.cs6422.group16.frontend.commandLineObjects;

import edu.gatech.coc.cs6422.group16.executionConfiguration.ExecutionConfig;
import edu.gatech.coc.cs6422.group16.executionConfiguration.ExecutionMode;
import edu.gatech.coc.cs6422.group16.metaDataRepository.MetaDataRepository;

public class SetOptionsCommand implements ICommandLineObject
{
    private String options;

    @Override
    public void execute()
    {
        ExecutionConfig config = ExecutionConfig.getInstance();

        String[] optionsAsArray = options.split(" ");
        if (optionsAsArray.length != 2)
        {
            System.err.println("Wrong command structure!");
            return;
        }
        String key = optionsAsArray[0];
        String value = optionsAsArray[1];

        switch (key.toLowerCase())
        {
            case "mode":
            case "execmode":
            case "executionmode":
                switch (value.toLowerCase())
                {
                    case "naive":
                        config.setExecutionMode(ExecutionMode.NAIVE);
                        break;
                    case "advanced":
                        config.setExecutionMode(ExecutionMode.ADVANCED);
                        break;
                    case "full":
                        config.setExecutionMode(ExecutionMode.FULL);
                        break;
                    default:
                        System.err.println("Wrong value for execMode!");
                        break;
                }
                break;
            case "numberoftrees":
                int numberOfTrees;
                try
                {
                    numberOfTrees = Integer.parseInt(value);
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                    break;
                }
                config.setNumberOfTrees(numberOfTrees);
                break;
            case "showalltrees":
                boolean showAllTrees = Boolean.parseBoolean(value);
                config.setShowAllTrees(showAllTrees);
                break;
            case "heuristics":
                boolean heuristics = Boolean.parseBoolean(value);
                config.setEnableHeuristics(heuristics);
                break;
            case "showvisualtrees":
                boolean showVisualTrees = Boolean.parseBoolean(value);
                config.setShowVisualTrees(showVisualTrees);
                break;
            case "showcostsinvisual":
            case "showcostsinvisualtrees":
                boolean showCostsInTrees = Boolean.parseBoolean(value);
                config.setShowCostsInVisualTree(showCostsInTrees);
                break;
            case "showvisualfirsttree":
                boolean showVisualFirstTree = Boolean.parseBoolean(value);
                config.setShowVisualFirstTree(showVisualFirstTree);
                break;
            case "showvisualbesttree":
                boolean showVisualBestTree = Boolean.parseBoolean(value);
                config.setShowVisualBestTree(showVisualBestTree);
                break;
            case "showvisualworsttree":
                boolean showVisualWorstTree = Boolean.parseBoolean(value);
                config.setShowVisualWorstTree(showVisualWorstTree);
                break;
            case "database":
                String newDBName = value.trim().toLowerCase();
                MetaDataRepository.GetInstance().SetDatabaseName(newDBName);
                break;
            default:
                System.err.println("Invalid command given!");
                break;
        }
    }

    @Override
    public String longHelp()
    {
        return "set\n" +
                "\texecutionMode naive/advanced/full\n" +
                "\tdatabase {string}\n" +
                "\tnumberOfTrees {integer}\n" +
                "\tshowAllTrees {boolean}\n" +
                "\theuristics {boolean}\n" +
                "\tshowVisualTrees {boolean}\n" +
                "\tshowCostsInVisualTrees {boolean}\n" +
                "\tshowVisualFirstTree {boolean}\n" +
                "\tshowVisualBestTree {boolean}\n" +
                "\tshowVisualWorstTree {boolean}\n";
    }

    @Override
    public boolean providesLongHelp()
    {
        return true;
    }

    @Override
    public boolean providesShortHelp()
    {
        return true;
    }

    @Override
    public void setCommand(String command)
    {
        options = command.substring(3);
        options = options.trim();
    }

    @Override
    public String shortHelp()
    {
        return "set\tAllows setting various options for controlling the way the database functions.";
    }
}
