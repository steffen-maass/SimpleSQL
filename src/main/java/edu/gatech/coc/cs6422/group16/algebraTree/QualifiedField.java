package edu.gatech.coc.cs6422.group16.algebraTree;

import edu.gatech.coc.cs6422.group16.metaDataRepository.MetaDataRepository;

import java.util.List;

public class QualifiedField extends RelationalAlgebraTree
{
    private String attribute;

    private String relation;

    public QualifiedField(String relation, String attribute)
    {
        this.attribute = attribute;
        this.relation = relation;
    }

    @Override
    public QualifiedField copyNode()
    {
        return new QualifiedField(this.relation, this.attribute);
    }

    @Override
    public double evaluateCost(List<Double> childrenCost)
    {
        return 0;
    }

    @Override
    public String getNodeContent()
    {
        return "";
    }

    @Override
    public boolean validate(List<RelationNode> relationNodes)
    {
        if (RelationNodesIncludeRelation(relationNodes, this.relation))
        {
            MetaDataRepository meta = MetaDataRepository.GetInstance();
            boolean isValid = meta.IsAttributeValid(this);
            if (isValid)
            {
                return true;
            }
            else
            {
                System.err.println("Field invalid: " + this.toString());
                return false;
            }
        }
        else
        {
            System.err.println("Field invalid: " + this.toString());
            return false;
        }
    }

    @Override
    public String toString()
    {
        return relation + "." + attribute;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final QualifiedField rhs = (QualifiedField) obj;
        if (this.relation != rhs.relation)
        {
            return false;
        }
        if (this.attribute != rhs.attribute)
        {
            return false;
        }
        return true;
    }

    public String getAttribute()
    {
        return attribute;
    }

    public String getRelation()
    {
        return relation;
    }

    public void setAttribute(String attribute)
    {
        this.attribute = attribute;
    }

    public void setRelation(String relation)
    {
        this.relation = relation;
    }
}
