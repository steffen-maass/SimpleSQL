package edu.gatech.coc.cs6422.group16.heuristics;

import edu.gatech.coc.cs6422.group16.algebraTree.*;

public class PushSelectionDown
{
    public static boolean TraverseCartesianProductNode(final SelectNode selNode, RelationalAlgebraTree root)
    {
        boolean change = false;
        CartesianProductNode carProdNode = root.getCurrentNodeAs(CartesianProductNode.class);
        if (carProdNode == null)
        {
            return change;
        }
        RelationalAlgebraTree c1 = carProdNode.getChildren().get(0);
        RelationalAlgebraTree c2 = carProdNode.getChildren().get(1);
        //currently ignore multiple selection on a single relation
        RelationNode relNode;
        if (c1 != null)
        {
            if ((relNode = c1.getCurrentNodeAs(RelationNode.class)) != null)
            {
                //is a relation node
                if (relNode.getRelation().equals(selNode.getField().getRelation()))
                {
                    SelectNode newSelNode = new SelectNode(selNode.getField(), selNode.getComparison(),
                            selNode.getValue());
                    carProdNode.insertNodeInSubtree(0, newSelNode);
                    change = true;
                }
            }

        }
        if (c2 != null)
        {
            if ((relNode = c2.getCurrentNodeAs(RelationNode.class)) != null)
            {
                //is a relation node
                if (relNode.getRelation().equals(selNode.getField().getRelation()))
                {
                    SelectNode newSelNode = new SelectNode(selNode.getField(), selNode.getComparison(),
                            selNode.getValue());
                    carProdNode.insertNodeInSubtree(1, newSelNode);
                    change = true;
                }
            }
        }
        return change || TraverseCartesianProductNode(selNode, c1) || TraverseCartesianProductNode(selNode, c2);
    }

    public static void pushSelectionDown(RelationalAlgebraTree root) throws IllegalArgumentException
    {
        if (root == null)
        {
            throw new IllegalArgumentException("Relational algebra tree cannot be null.");
        }
        RelationalAlgebraTree searchCur, searchParent, cur, parent = root;
        ProjectNode pn = root.getCurrentNodeAs(ProjectNode.class);
        SelectNode selNode;

        if (pn == null)
        {
            System.out.println("Root node must be a project node.");
            return;
        }
        cur = root.getChildren().get(0);
        //locate the first SelectNode
        while (cur != null && cur.getCurrentNodeAs(SelectNode.class) == null)
        {
            parent = cur;
            if (cur.getChildCount() > 0)
            {
                cur = parent.getChildren().get(0);
            }
            else
            {
                // no SelectNode found!
                return;
            }
        }
        //traverse the following SelectNode, for each of them, check if push-down is available
        while (cur != null && cur.getCurrentNodeAs(SelectNode.class) != null)
        {
            //from the cur SelectNode, search down till CartesianProductNode is found
            selNode = cur.getCurrentNodeAs(SelectNode.class);
            searchParent = cur;
            searchCur = searchParent.getChildren().get(0);
            while (searchCur != null && searchCur.getCurrentNodeAs(CartesianProductNode.class) == null)
            {
                if (searchCur.getCurrentNodeAs(JoinNode.class) != null)
                //need to figure out what JoinNode is
                {
                    return;
                }
                searchParent = searchCur;
                searchCur = searchParent.getChildren().get(0);
            }
            //if no CartesianProductNode is found, there would be no push-down opportunity
            if (searchCur == null)
            {
                break;
            }
            if (TraverseCartesianProductNode(selNode, searchCur))
            {
                RelationalAlgebraTree tempCur = cur.getChildren().get(0);
                cur.deleteNode();
                cur = tempCur;
                //                cur = cur.getChildren().get(0);
                //                parent.getChildren().remove(0);
                //                parent.getChildren().add(cur);
            }
            else
            {
                parent = cur;
                cur = parent.getChildren().get(0);
            }
        }
    }
}
