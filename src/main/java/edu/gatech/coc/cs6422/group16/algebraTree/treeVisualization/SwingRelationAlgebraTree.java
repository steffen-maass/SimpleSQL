package edu.gatech.coc.cs6422.group16.algebraTree.treeVisualization;

import edu.gatech.coc.cs6422.group16.algebraTree.RelationalAlgebraTree;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;

import javax.swing.*;
import java.awt.*;

/**
 * Adapted from this code: http://code.google.com/p/treelayout/source/browse/trunk/org.abego.treelayout
 * .demo/src/main/java/org/abego/treelayout/demo/swing/SwingDemo.java
 */
public class SwingRelationAlgebraTree
{
    private static void showInDialog(JComponent panel, String title)
    {
        JDialog dialog = new JDialog();
        Container contentPane = dialog.getContentPane();
        ((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(panel);
        contentPane.add(scroll);

        dialog.pack();
        dialog.setTitle(title);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static void showInDialog(RelationalAlgebraTree root, String title)
    {
        RelationAlgebraTreeAsTreeLayout tree = new RelationAlgebraTreeAsTreeLayout(root);

        // setup the tree layout configuration
        double gapBetweenLevels = 50;
        double gapBetweenNodes = 10;
        DefaultConfiguration<RelationalAlgebraTree> configuration = new DefaultConfiguration<>(gapBetweenLevels,
                gapBetweenNodes);

        // create the NodeExtentProvider for TextInBox nodes
        RelationAlgebraTreeNodeExtentProvider nodeExtentProvider = new RelationAlgebraTreeNodeExtentProvider();

        // create the layout
        TreeLayout<RelationalAlgebraTree> treeLayout = new TreeLayout<>(tree, nodeExtentProvider, configuration);

        // Create a panel that draws the nodes and edges and show the panel
        TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
        showInDialog(panel, title);
    }
}
