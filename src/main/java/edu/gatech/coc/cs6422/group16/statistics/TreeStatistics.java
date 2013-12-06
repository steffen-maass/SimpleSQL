package edu.gatech.coc.cs6422.group16.statistics;

import edu.gatech.coc.cs6422.group16.algebraTree.RelationalAlgebraTree;

public class TreeStatistics implements Comparable<TreeStatistics>
{
    private double cost;

    private RelationalAlgebraTree tree;

    private String treeAsString;

    public TreeStatistics(RelationalAlgebraTree tree)
    {
        this.tree = tree;
        this.treeAsString = tree.toString();
        this.cost = tree.computeCost();
    }

    public double getCost()
    {
        return cost;
    }

    @Override
    public int compareTo(TreeStatistics s)
    {
        return (int) (this.cost - s.cost);
    }

    public RelationalAlgebraTree getTree()
    {
        return tree;
    }

    public String getTreeAsString()
    {

        return treeAsString;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public void setTreeAsString(String treeAsString)
    {
        this.treeAsString = treeAsString;
    }

    @Override
    public String toString()
    {
        return "Cost: " + cost + "\n" +
                "Plan: " + treeAsString;
    }
}
