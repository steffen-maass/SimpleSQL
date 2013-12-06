package edu.gatech.coc.cs6422.group16.algebraTree.treeGeneration;

import edu.gatech.coc.cs6422.group16.algebraTree.CartesianProductNode;
import edu.gatech.coc.cs6422.group16.algebraTree.RelationNode;
import edu.gatech.coc.cs6422.group16.algebraTree.RelationalAlgebraTree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TreeGenerator
{
    /**
     * Fill in the leaves of a tree, works recursively
     *
     * @param leaves The queue of leaves still left to fill in
     * @param tree   The current (sub-)tree root-node
     */
    private static void fillInLeaves(Deque<RelationNode> leaves, RelationalAlgebraTree tree)
    {
        for (RelationalAlgebraTree child : tree.getChildren())
        {
            // check if leave node or not:
            if (child.getChildCount() != 0)
            {
                // if no leave, continue searching for leaves:
                fillInLeaves(leaves, child);
            }
            else
            {
                // else set relation-name to first of given leave-relations
                RelationNode r = child.getCurrentNodeAs(RelationNode.class);
                // get first leave and remove it:
                RelationNode newLeave = leaves.poll();

                // set relation of this leave to new leave:
                r.setRelation(newLeave.getRelation());
            }
        }
    }

    /**
     * Loop over all possible trees and all permutations, creating all possible trees!
     *
     * @param relations The relations to fill into the trees
     * @return A list of all possible binary relation-trees
     */
    public static List<RelationalAlgebraTree> generateAllPossibleTrees(List<RelationNode> relations, int k)
    {
        int treeK;
        int permutationK;
        if (k > 1)
        {
            int singleK = (int) Math.ceil(Math.sqrt(4 * k));
            treeK = Math.max(singleK / 4, 1);
            permutationK = 3 * singleK / 4;
        }
        else
        {
            treeK = k;
            permutationK = k;
        }

        List<RelationalAlgebraTree> trees = new ArrayList<>();

        if (relations.size() > 1)
        {
            // "relations.size() - 1" because of n leave nodes for n-1 intermediate nodes!
            List<RelationalAlgebraTree> treeNodes = generateTrees(relations.size() - 1);
            List<List<RelationNode>> permutations = permute(relations);

            if (permutationK >= 1)
            {
                treeNodes = treeNodes.subList(0, Math.min(treeK, treeNodes.size()));
                permutations = permutations.subList(0, Math.min(permutationK, permutations.size()));
            }
            for (RelationalAlgebraTree root : treeNodes)
            {
                for (List<RelationNode> permutation : permutations)
                {
                    RelationalAlgebraTree copyRoot = root.copyNode();
                    fillInLeaves(new LinkedList<>(permutation), copyRoot);
                    trees.add(copyRoot);
                }
            }
        }
        else if (relations.size() == 1)
        {
            trees.add(relations.get(0));
        }

        return trees;
    }

    /**
     * Resources used:
     * * http://blogs.msdn.com/b/ericlippert/archive/2010/04/19/every-binary-tree-there-is.aspx
     * * http://www.careercup.com/question?id=14945787&ModPagespeed=noscript
     * * http://stackoverflow.com/questions/12418575/code-in-prolog-generate-all-structurally-distinct-full-binary
     * -trees-with-n-node
     *
     * @param n The number of different nodes to create
     * @return A list of structually different trees
     */
    public static List<RelationalAlgebraTree> generateTrees(int n)
    {
        List<RelationalAlgebraTree> trees = new ArrayList<>();

        if (n == 0)
        {
            trees.add(new RelationNode(""));
        }
        else
        {
            for (int i = 0; i < n; i++)
            {
                for (RelationalAlgebraTree left : generateTrees(i))
                {
                    for (RelationalAlgebraTree right : generateTrees(n - 1 - i))
                    {
                        RelationalAlgebraTree newTree = new CartesianProductNode();

                        newTree.addChild(left);
                        newTree.addChild(right);

                        trees.add(newTree);
                    }
                }
            }
        }

        return trees;
    }

    /**
     * Adapted from here: http://codereview.stackexchange
     * .com/questions/11598/java-code-for-permutations-of-a-list-of-numbers
     *
     * @param relations The input relations to permute
     * @return The list of all permutations of the input-list of relations
     */
    public static List<List<RelationNode>> permute(List<RelationNode> relations)
    {
        List<List<RelationNode>> output = new ArrayList<>();
        if (relations.isEmpty())
        {
            output.add(new ArrayList<RelationNode>());
            return output;
        }
        List<RelationNode> list = new ArrayList<>(relations);
        RelationNode firstRelation = list.get(0);
        List<RelationNode> remainingList = list.subList(1, list.size());
        for (List<RelationNode> permutations : permute(remainingList))
        {
            List<List<RelationNode>> subLists = new ArrayList<>();
            for (int i = 0; i <= permutations.size(); i++)
            {
                List<RelationNode> subList = new ArrayList<>();
                subList.addAll(permutations);
                subList.add(i, firstRelation);
                subLists.add(subList);
            }
            output.addAll(subLists);
        }
        return output;
    }
}
