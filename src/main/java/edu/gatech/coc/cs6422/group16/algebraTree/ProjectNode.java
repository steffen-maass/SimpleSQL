package edu.gatech.coc.cs6422.group16.algebraTree;

import edu.gatech.coc.cs6422.group16.executionConfiguration.ExecutionConfig;

import java.util.ArrayList;
import java.util.List;

public class ProjectNode extends RelationalAlgebraTree
{
    private List<QualifiedField> projections = new ArrayList<>();

    private SelectionType type;

    public void addProjection(QualifiedField proj)
    {
        this.projections.add(proj);
    }

    @Override
    public RelationalAlgebraTree copyNode()
    {
        ProjectNode newProject = new ProjectNode();

        newProject.type = this.type;
        for (QualifiedField field : this.projections)
        {
            newProject.projections.add(field.copyNode());
        }

        return super.copyFields(newProject);
    }

    @Override
    public double evaluateCost(List<Double> childrenCost)
    {
        return childrenCost.get(0);
    }

    @Override
    public String getNodeContent()
    {
        String s = "\u03c0(";
        if (this.type == SelectionType.STAR)
        {
            s += "*";
        }
        else
        {
            int size = this.projections.size();
            int i = 1;
            for (QualifiedField field : this.projections)
            {
                s += field.toString();
                if (i != size)
                {
                    s += ", ";
                }
                i++;
            }
        }
        s += ")";

        ExecutionConfig config = ExecutionConfig.getInstance();
        if (config.isShowCostsInVisualTree())
        {
            s += "\n" + this.computeCost();
        }
        return s;
    }

    @Override
    public boolean validate(List<RelationNode> relationNodes)
    {
        boolean projectionsValid = true;
        for (QualifiedField field : this.projections)
        {
            if (!field.validate(relationNodes))
            {
                projectionsValid = false;
                break;
            }
        }

        if (projectionsValid && this.getChildCount() == 1)
        {
            return true;
        }
        else
        {
            if (this.getChildCount() != 1)
            {
                System.err.println("Childcount for ProjectNode invalid: " + this.getChildCount());
            }
            return false;
        }
    }

    @Override
    public void addChild(RelationalAlgebraTree node)
    {
        if (this.getChildren().size() == 0)
        {
            super.addChild(node);
        }
        else
        {
            throw new IllegalStateException("There is more than one child for this ProjectNode, this cannot exist!");
        }
    }

    @Override
    public String toString()
    {
        String s = "\u03c0(";

        if (this.type == SelectionType.STAR)
        {
            s += "*";
        }
        else
        {
            int size = this.projections.size();
            int i = 1;
            for (QualifiedField field : this.projections)
            {
                s += field.toString();
                if (i != size)
                {
                    s += ", ";
                }
                i++;
            }
        }
        s += ")";

        s += "(";
        s += this.getChildren().get(0).toString();
        s += ")";

        return s;
    }

    public List<QualifiedField> getProjections()
    {
        return projections;
    }

    public SelectionType getType()
    {
        return type;
    }

    public void setType(SelectionType type)
    {
        this.type = type;
    }
}
