package edu.gatech.coc.cs6422.group16.algebraTree;

import edu.gatech.coc.cs6422.group16.executionConfiguration.ExecutionConfig;

import java.util.List;

public class CartesianProductNode extends RelationalAlgebraTree
{
    @Override
    public RelationalAlgebraTree copyNode()
    {
        return super.copyFields(new CartesianProductNode());
    }

    @Override
    public double evaluateCost(List<Double> childrenCost)
    {
        return childrenCost.get(0) * childrenCost.get(1);
    }

    @Override
    public String getNodeContent()
    {
        ExecutionConfig config = ExecutionConfig.getInstance();
        if (config.isShowCostsInVisualTree())
        {
            return "x \n " + this.computeCost();
        }
        else
        {
            return "x";
        }
    }

    @Override
    public boolean validate(List<RelationNode> relationNodes)
    {
        if (getChildCount() == 2)
        {
            return true;
        }
        else
        {
            if (this.getChildCount() != 2)
            {
                System.err.println("Childcount for CartesianProductNode invalid: " + this.getChildCount());
            }
            return false;
        }
    }

    @Override
    public String toString()
    {
        String s1 = "(" + this.getChildren().get(0).toString() + ")";
        String s2 = "(" + this.getChildren().get(1).toString() + ")";
        return s1 + "x" + s2;
    }
}
