package edu.gatech.coc.cs6422.group16.frontend.commandLineObjects;

import edu.gatech.coc.cs6422.group16.algebraTree.RelationalAlgebraTree;
import edu.gatech.coc.cs6422.group16.algebraTree.treeVisualization.SwingRelationAlgebraTree;
import edu.gatech.coc.cs6422.group16.executionConfiguration.ExecutionConfig;
import edu.gatech.coc.cs6422.group16.heuristics.CartesianToJoin;
import edu.gatech.coc.cs6422.group16.heuristics.PushSelectionDown;
import edu.gatech.coc.cs6422.group16.metaDataRepository.MetaDataRepository;
import edu.gatech.coc.cs6422.group16.statistics.Statistics;
import edu.gatech.coc.cs6422.group16.statistics.TimerType;

import java.io.IOException;
import java.util.List;

public abstract class ProcessQueryCommand implements ICommandLineObject
{
    private static void optimizeQueries(List<RelationalAlgebraTree> trees)
    {
        if (trees != null)
        {
            ExecutionConfig config = ExecutionConfig.getInstance();
            Statistics stat = Statistics.getInstance();
            if (config.isEnableHeuristics())
            {
                stat.start(TimerType.OPTIMIZATION);
                for (RelationalAlgebraTree singleTree : trees)
                {
                    PushSelectionDown.pushSelectionDown(singleTree);
                    CartesianToJoin.cartesianToJoin(singleTree);
                }
                stat.stop(TimerType.OPTIMIZATION);
            }

            // add all trees to statistics-module:
            for (RelationalAlgebraTree singleTree : trees)
            {
                stat.addQueryTree(singleTree);
            }
        }
    }

    @Override
    public void execute()
    {
        ExecutionConfig config = ExecutionConfig.getInstance();

        Statistics stat = Statistics.getInstance();
        stat.start(TimerType.FULL);
        List<RelationalAlgebraTree> trees = null;
        try
        {
            trees = this.parseTree();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        MetaDataRepository.GetInstance().ReadData();

        // only continue on valid trees:
        if (trees != null)
        {
            if (config.isShowVisualTrees() && config.isShowVisualFirstTree())
            {
                RelationalAlgebraTree t0 = trees.get(0).copyNode();
                SwingRelationAlgebraTree.showInDialog(t0, "First tree - Unoptimized");
            }

            optimizeQueries(trees);

            if (config.isShowVisualTrees() && config.isShowVisualFirstTree())
            {
                RelationalAlgebraTree t1 = trees.get(0).copyNode();
                SwingRelationAlgebraTree.showInDialog(t1, "First tree - Optimized");
            }

            stat.stop(TimerType.FULL);
            stat.print();
        }

        // clean-up after we are done:
        stat.reset();
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

    protected abstract List<RelationalAlgebraTree> parseTree() throws IOException;
}
