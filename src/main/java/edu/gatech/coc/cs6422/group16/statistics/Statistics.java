package edu.gatech.coc.cs6422.group16.statistics;

import edu.gatech.coc.cs6422.group16.algebraTree.RelationalAlgebraTree;
import edu.gatech.coc.cs6422.group16.algebraTree.treeVisualization.SwingRelationAlgebraTree;
import edu.gatech.coc.cs6422.group16.executionConfiguration.ExecutionConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statistics
{
    private static final Statistics INSTANCE = new Statistics();

    public static Statistics getInstance()
    {
        return INSTANCE;
    }

    private TimeObject full = new TimeObject();

    private TimeObject optimization = new TimeObject();

    private TimeObject parse = new TimeObject();

    private List<TreeStatistics> statistics = new ArrayList<>();

    private TimeObject treeGeneration = new TimeObject();

    private TimeObject validation = new TimeObject();

    public void addQueryTree(RelationalAlgebraTree tree)
    {
        statistics.add(new TreeStatistics(tree));
    }

    public void print()
    {
        ExecutionConfig config = ExecutionConfig.getInstance();
        System.out.println("Processing time: " + this.full.difference() + " ms");
        System.out.println("\tProcessing time parse: " + this.parse.difference() + " ms");
        System.out.println("\tProcessing time validation: " + this.validation.difference() + " ms");
        System.out.println("\tProcessing time optimization: " + this.optimization.difference() + " ms");
        System.out.println("\tProcessing time treeGeneration: " + this.treeGeneration.difference() + " ms");

        // find best TreePlan:
        TreeStatistics best = Collections.min(this.statistics);
        TreeStatistics worst = Collections.max(this.statistics);

        if (config.isShowVisualTrees())
        {
            if (config.isShowVisualBestTree())
            {
                SwingRelationAlgebraTree.showInDialog(best.getTree(), "Best tree");
            }
            if (config.isShowVisualWorstTree())
            {
                SwingRelationAlgebraTree.showInDialog(worst.getTree(), "Worst tree");
            }
        }

        System.out.println("-----");
        System.out.println("Cost of best plan: " + best.getCost());
        System.out.println("Best plan: " + best.getTreeAsString());
        System.out.println("-----");
        System.out.println("Cost of worst plan: " + worst.getCost());
        System.out.println("Worst plan: " + worst.getTreeAsString());
        System.out.println("-----");
        System.out.println("Number of trees: " + this.statistics.size());
        System.out.println("-----");
        if (config.isShowAllTrees())
        {
            for (TreeStatistics stat : this.statistics)
            {
                if (stat != best)
                {
                    System.out.println(stat.toString());
                    System.out.println("-----");
                }
            }
        }
    }

    public void reset()
    {
        this.statistics.clear();
        this.full.reset();
        this.parse.reset();
        this.validation.reset();
        this.optimization.reset();
        this.treeGeneration.reset();
    }

    public void start(TimerType type)
    {
        switch (type)
        {
            case FULL:
                this.full.start();
                break;
            case PARSE:
                this.parse.start();
                break;
            case VALIDATION:
                this.validation.start();
                break;
            case OPTIMIZATION:
                this.optimization.start();
                break;
            case TREE_GENERATION:
                this.treeGeneration.start();
                break;
        }
    }

    public void stop(TimerType type)
    {
        switch (type)
        {
            case FULL:
                this.full.stop();
                break;
            case PARSE:
                this.parse.stop();
                break;
            case VALIDATION:
                this.validation.stop();
                break;
            case OPTIMIZATION:
                this.optimization.stop();
                break;
            case TREE_GENERATION:
                this.treeGeneration.stop();
                break;
        }
    }
}
