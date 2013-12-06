package edu.gatech.coc.cs6422.group16.algebraTree.treeVisualization;

import edu.gatech.coc.cs6422.group16.algebraTree.RelationalAlgebraTree;
import org.abego.treelayout.util.AbstractTreeForTreeLayout;

import java.util.List;

public class RelationAlgebraTreeAsTreeLayout extends AbstractTreeForTreeLayout<RelationalAlgebraTree>
{
    public RelationAlgebraTreeAsTreeLayout(RelationalAlgebraTree root)
    {
        super(root);
    }

    @Override
    public RelationalAlgebraTree getParent(RelationalAlgebraTree relationalAlgebraTree)
    {
        return relationalAlgebraTree.getParent();
    }

    @Override
    public List<RelationalAlgebraTree> getChildrenList(RelationalAlgebraTree relationalAlgebraTree)
    {
        return relationalAlgebraTree.getChildren();
    }
}
