package edu.gatech.coc.cs6422.group16.algebraTree.treeVisualization;

import edu.gatech.coc.cs6422.group16.algebraTree.RelationalAlgebraTree;
import org.abego.treelayout.NodeExtentProvider;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class RelationAlgebraTreeNodeExtentProvider implements NodeExtentProvider<RelationalAlgebraTree>
{
    private static double computeHeight(String s)
    {
        Font font = new Font("Tahoma", Font.PLAIN, 14);
        AffineTransform affinetransform = font.getTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);

        int numberOfLines = s.split("\n").length;

        return font.getStringBounds(s, frc).getHeight() * numberOfLines;
    }

    private static double computeWidth(String s)
    {
        Font font = new Font("Tahoma", Font.PLAIN, 14);
        AffineTransform affinetransform = font.getTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);

        double maxWidth = Double.MIN_VALUE;
        for (String split : s.split("\n"))
        {
            maxWidth = Math.max(maxWidth, font.getStringBounds(split, frc).getWidth());
        }
        return maxWidth;
    }

    @Override
    public double getWidth(RelationalAlgebraTree relationalAlgebraTree)
    {
        return computeWidth(relationalAlgebraTree.getNodeContent()) + 10;
    }

    @Override
    public double getHeight(RelationalAlgebraTree relationalAlgebraTree)
    {
        return computeHeight(relationalAlgebraTree.getNodeContent()) + 5;
    }
}
