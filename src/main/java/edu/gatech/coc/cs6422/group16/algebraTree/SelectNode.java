package edu.gatech.coc.cs6422.group16.algebraTree;

import edu.gatech.coc.cs6422.group16.executionConfiguration.ExecutionConfig;
import edu.gatech.coc.cs6422.group16.metaDataRepository.MetaDataRepository;

import java.util.List;

public class SelectNode extends RelationalAlgebraTree
{
    private Comparison comparison;

    private QualifiedField field;

    private String value;

    public SelectNode(QualifiedField field, Comparison comparison, String value)
    {
        this.field = field;
        this.comparison = comparison;
        this.value = value;
    }

    @Override
    public RelationalAlgebraTree copyNode()
    {
        QualifiedField newField = this.field.copyNode();
        return super.copyFields(new SelectNode(newField, this.comparison, this.value));
    }

    @Override
    public double evaluateCost(List<Double> childrenCost)
    {
        MetaDataRepository meta = MetaDataRepository.GetInstance();
        // formula: T(R) = T(S) / V(S, a)
        return childrenCost.get(0) / (double) meta.GetDistinctValueOfAttribute(this.field);
    }

    @Override
    public String getNodeContent()
    {
        ExecutionConfig config = ExecutionConfig.getInstance();
        if (config.isShowCostsInVisualTree())
        {
            return "\u03c3(" + this.field.toString() + " " + this.comparison.toString() + " " + this.value + ")\n" +
                    this.computeCost();
        }
        else
        {
            return "\u03c3(" + this.field.toString() + " " + this.comparison.toString() + " " + this.value + ")";
        }
    }

    @Override
    public boolean validate(List<RelationNode> relationNodes)
    {
        if (this.field.validate(relationNodes) && this.getChildCount() == 1)
        {
            return true;
        }
        else
        {
            if (this.getChildCount() != 1)
            {
                System.err.println("Childcount for SelectNode invalid: " + this.getChildCount());
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
            throw new IllegalStateException("There is more than one child for this SelectNode, this cannot exist!");
        }
    }

    @Override
    public String toString()
    {
        String s1;
        if (this.getChildren().size() > 0)
        {
            s1 = "(" + this.getChildren().get(0).toString() + ")";
        }
        else
        {
            s1 = "()";
        }
        return "\u03c3(" + field.toString() + " " + comparison.toString() + " " + value +
                ")" + s1;
    }

    public Comparison getComparison()
    {
        return comparison;
    }

    public QualifiedField getField()
    {
        return field;
    }

    public String getValue()
    {
        return value;
    }

    public void setComparison(Comparison comparison)
    {
        this.comparison = comparison;
    }

    public void setField(QualifiedField field)
    {
        this.field = field;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}

