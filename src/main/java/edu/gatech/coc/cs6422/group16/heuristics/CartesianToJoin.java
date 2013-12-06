package edu.gatech.coc.cs6422.group16.heuristics;

import edu.gatech.coc.cs6422.group16.algebraTree.*;

import java.util.ArrayList;
import java.util.List;

public class CartesianToJoin
{
    public static void cartesianToJoin(RelationalAlgebraTree root)
    {
        List<RelationalAlgebraTree> markedNodes = new ArrayList<>();
        List<JoinAsSelectNode> joinAsSelectNodes = new ArrayList<>();
        getAllNodesOfType(root, JoinAsSelectNode.class, joinAsSelectNodes);

        // while not all join-selects are converted or |markedNode| < |joinAsSelectNodes|, continue converting
        while ((joinAsSelectNodes.size() > 0) && (markedNodes.size() < joinAsSelectNodes.size()))
        {
            RelationalAlgebraTree nextCartProdNode = searchNextCartesianProductNode(root, markedNodes);
            if (nextCartProdNode != null)
            {
                List<RelationNode> possibleRelations = new ArrayList<>();
                getAllNodesOfType(nextCartProdNode, RelationNode.class, possibleRelations);
                List<JoinAsSelectNode> matchingJoinAsSelects = new ArrayList<>();

                for (JoinAsSelectNode joinAsSelect : joinAsSelectNodes)
                {
                    // we can only continue, if both relations are present in this sub-tree:
                    if (relationInList(possibleRelations, joinAsSelect.getCondition1()) && relationInList(
                            possibleRelations, joinAsSelect.getCondition2()))
                    {
                        matchingJoinAsSelects.add(joinAsSelect);
                    }
                }
                if (matchingJoinAsSelects.size() > 0)
                {
                    // Maybe: Include cost-based estimation for different matching joins
                    // but for now, just take the first matching join:
                    JoinAsSelectNode match = matchingJoinAsSelects.get(0);
                    JoinNode join = match.toJoinNode();

                    // now: remove matched node, replace cartesian-product-node with join:
                    match.deleteNode();
                    nextCartProdNode.replaceNode(join);

                    // remove used JoinAsSelectNode:
                    joinAsSelectNodes.remove(match);
                }
                else
                {
                    // mark current cart-product-node as unprocessable:
                    markedNodes.add(nextCartProdNode);
                }
            }
        }
    }

    private static <T extends RelationalAlgebraTree> void getAllNodesOfType(RelationalAlgebraTree current,
            Class<? extends T> classType, List<T> nodeList)
    {
        if (current.isClass(classType))
        {
            nodeList.add(current.getCurrentNodeAs(classType));
        }
        for (RelationalAlgebraTree child : current.getChildren())
        {
            getAllNodesOfType(child, classType, nodeList);
        }
    }

    private static boolean relationInList(List<RelationNode> relations, QualifiedField field)
    {
        for (RelationNode relation : relations)
        {
            if (relation.getRelation().equals(field.getRelation()))
            {
                return true;
            }
        }
        return false;
    }

    private static RelationalAlgebraTree searchNextCartesianProductNode(RelationalAlgebraTree start,
            List<RelationalAlgebraTree> markedNodes)
    {
        // loop all children, calling recursively, resulting in a depth-first-search:
        for (RelationalAlgebraTree child : start.getChildren())
        {
            RelationalAlgebraTree nextCartNode = searchNextCartesianProductNode(child, markedNodes);
            if (nextCartNode != null)
            {
                return nextCartNode;
            }
        }
        // only return if the current node has not been marked and is a CartesianProductNode:
        if (start.isClass(CartesianProductNode.class) && !markedNodes.contains(start))
        {
            return start;
        }
        return null;
    }
}
