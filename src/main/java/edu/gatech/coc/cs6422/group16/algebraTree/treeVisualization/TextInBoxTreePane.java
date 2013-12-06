package edu.gatech.coc.cs6422.group16.algebraTree.treeVisualization;

import edu.gatech.coc.cs6422.group16.algebraTree.RelationalAlgebraTree;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TextInBoxTreePane extends JComponent
{
    private final static int ARC_SIZE = 10;

    private final static Color BORDER_COLOR = Color.darkGray;

    private final static Color BOX_COLOR = Color.orange;

    private final static Color TEXT_COLOR = Color.black;

    private final TreeLayout<RelationalAlgebraTree> treeLayout;

    public TextInBoxTreePane(TreeLayout<RelationalAlgebraTree> treeLayout)
    {
        this.treeLayout = treeLayout;

        Dimension size = treeLayout.getBounds().getBounds().getSize();
        setPreferredSize(size);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        paintEdges(g, getTree().getRoot());

        // paint the boxes
        for (RelationalAlgebraTree textInBox : treeLayout.getNodeBounds().keySet())
        {
            paintBox(g, textInBox);
        }
    }

    private Rectangle2D.Double getBoundsOfNode(RelationalAlgebraTree node)
    {
        return treeLayout.getNodeBounds().get(node);
    }

    private Iterable<RelationalAlgebraTree> getChildren(RelationalAlgebraTree parent)
    {
        return getTree().getChildren(parent);
    }

    private TreeForTreeLayout<RelationalAlgebraTree> getTree()
    {
        return treeLayout.getTree();
    }

    private void paintBox(Graphics g, RelationalAlgebraTree textInBox)
    {
        // draw the box in the background
        g.setColor(BOX_COLOR);
        Rectangle2D.Double box = getBoundsOfNode(textInBox);
        g.fillRoundRect((int) box.x, (int) box.y, (int) box.width - 1, (int) box.height - 1, ARC_SIZE, ARC_SIZE);
        g.setColor(BORDER_COLOR);
        g.drawRoundRect((int) box.x, (int) box.y, (int) box.width - 1, (int) box.height - 1, ARC_SIZE, ARC_SIZE);

        // draw the text on top of the box (possibly multiple lines)
        g.setColor(TEXT_COLOR);
        g.setFont(new Font("Tahoma", Font.PLAIN, 14));
        String[] lines = textInBox.getNodeContent().split("\n");
        FontMetrics m = getFontMetrics(getFont());
        int x = (int) box.x + ARC_SIZE / 2;
        int y = (int) box.y + m.getAscent() + m.getLeading() + 1;

        for (int i = 0; i < lines.length; i++)
        {
            g.drawString(lines[i], x, y);
            y += m.getHeight();
        }
    }

    private void paintEdges(Graphics g, RelationalAlgebraTree parent)
    {
        if (!getTree().isLeaf(parent))
        {
            Rectangle2D.Double b1 = getBoundsOfNode(parent);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            for (RelationalAlgebraTree child : getChildren(parent))
            {
                Rectangle2D.Double b2 = getBoundsOfNode(child);
                g.drawLine((int) x1, (int) y1, (int) b2.getCenterX(), (int) b2.getCenterY());

                paintEdges(g, child);
            }
        }
    }
}