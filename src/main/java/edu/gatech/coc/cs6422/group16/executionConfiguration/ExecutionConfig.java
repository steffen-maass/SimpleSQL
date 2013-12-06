package edu.gatech.coc.cs6422.group16.executionConfiguration;

public class ExecutionConfig
{
    private static final int DEFAULT_NUMBER_OF_TREES = 50;

    private static final ExecutionConfig INSTANCE = new ExecutionConfig();

    public static ExecutionConfig getInstance()
    {
        return INSTANCE;
    }

    private ExecutionMode mode;

    private int numberOfTrees;

    private boolean showAllTrees;

    private boolean enableHeuristics;

    private boolean showCostsInVisualTree;

    private boolean showVisualTrees;

    private boolean showVisualFirstTree;

    private boolean showVisualBestTree;

    private boolean showVisualWorstTree;

    private ExecutionConfig()
    {
        this.reset();
    }

    public ExecutionMode getMode()
    {
        return mode;
    }

    public int getNumberOfTrees()
    {
        return numberOfTrees;
    }

    public boolean isEnableHeuristics()
    {
        return enableHeuristics;
    }

    public boolean isShowAllTrees()
    {
        return showAllTrees;
    }

    public boolean isShowCostsInVisualTree()
    {
        return showCostsInVisualTree;
    }

    public boolean isShowVisualBestTree()
    {
        return showVisualBestTree;
    }

    public boolean isShowVisualFirstTree()
    {
        return showVisualFirstTree;
    }

    public boolean isShowVisualTrees()
    {
        return showVisualTrees;
    }

    public boolean isShowVisualWorstTree()
    {
        return showVisualWorstTree;
    }

    public void setEnableHeuristics(boolean enableHeuristics)
    {
        this.enableHeuristics = enableHeuristics;
    }

    public void setExecutionMode(ExecutionMode mode)
    {
        this.mode = mode;
        switch (mode)
        {
            case NAIVE:
                numberOfTrees = 1;
                break;
            case ADVANCED:
                numberOfTrees = DEFAULT_NUMBER_OF_TREES;
                break;
            case FULL:
                numberOfTrees = -1;
                break;
        }
    }

    public void setNumberOfTrees(int numberOfTrees)
    {
        if (this.mode == ExecutionMode.ADVANCED)
        {
            this.numberOfTrees = numberOfTrees;
        }
        else
        {
            System.err.println("Wrong mode for setting the numberOfTrees!");
        }
    }

    public void setShowAllTrees(boolean showAllTrees)
    {
        this.showAllTrees = showAllTrees;
    }

    public void setShowCostsInVisualTree(boolean showCostsInVisualTree)
    {
        this.showCostsInVisualTree = showCostsInVisualTree;
    }

    public void setShowVisualBestTree(boolean showVisualBestTree)
    {
        this.showVisualBestTree = showVisualBestTree;
    }

    public void setShowVisualFirstTree(boolean showVisualFirstTree)
    {
        this.showVisualFirstTree = showVisualFirstTree;
    }

    public void setShowVisualTrees(boolean showVisualTrees)
    {
        this.showVisualTrees = showVisualTrees;
    }

    public void setShowVisualWorstTree(boolean showVisualWorstTree)
    {
        this.showVisualWorstTree = showVisualWorstTree;
    }

    @Override
    public String toString()
    {
        return "ExecutionConfig{" +
                "mode=" + mode +
                ", numberOfTrees=" + numberOfTrees +
                ", showAllTrees=" + showAllTrees +
                ", enableHeuristics=" + enableHeuristics +
                '}';
    }

    private void reset()
    {
        this.showVisualBestTree = true;
        this.showVisualFirstTree = true;
        this.showVisualWorstTree = true;
        this.showCostsInVisualTree = false;
        this.showVisualTrees = false;
        this.showAllTrees = false;
        this.enableHeuristics = true;
        this.setExecutionMode(ExecutionMode.FULL);
    }
}
