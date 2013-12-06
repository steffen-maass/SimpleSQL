package edu.gatech.coc.cs6422.group16.parsing;

import SimpleSQL.SimpleSQLParser;
import edu.gatech.coc.cs6422.group16.algebraTree.*;

import java.util.ArrayList;
import java.util.List;

public class ParseHelpers
{
    public static Comparison parseComparison(SimpleSQLParser.ComparisonContext cc)
    {
        String comparisonString = cc.getText();
        Comparison comparison;
        switch (comparisonString)
        {
            case "=":
                comparison = Comparison.EQUAL;
                break;
            case "<":
                comparison = Comparison.LESS;
                break;
            case "<=":
                comparison = Comparison.LESSOREQUAL;
                break;
            case ">=":
                comparison = Comparison.GREATEROREQUAL;
                break;
            case ">":
                comparison = Comparison.GREATER;
                break;
            default:
                comparison = Comparison.EQUAL;
                break;
        }
        return comparison;
    }

    public static QualifiedField parseFieldIdentifier(SimpleSQLParser.FieldIdentifierContext fic)
    {
        return parseQualifiedField(fic.getChild(SimpleSQLParser.QualifiedFieldContext.class, 0));
    }

    public static List<JoinNode> parseJoinNodes(SimpleSQLParser.SelectionsContext selections)
    {
        List<JoinNode> nodes = new ArrayList<>();

        List<SimpleSQLParser.SelectionContext> selectionContexts = selections.getRuleContexts(
                SimpleSQLParser.SelectionContext.class);
        for (SimpleSQLParser.SelectionContext selection : selectionContexts)
        {
            SimpleSQLParser.JoinStatementContext joinStatement = selection.getChild(
                    SimpleSQLParser.JoinStatementContext.class, 0);
            if (joinStatement != null)
            {
                QualifiedField cond1 = parseFieldIdentifier(joinStatement.getChild(
                        SimpleSQLParser.FieldIdentifierContext.class, 0));
                QualifiedField cond2 = parseFieldIdentifier(joinStatement.getChild(
                        SimpleSQLParser.FieldIdentifierContext.class, 1));
                Comparison comparison = parseComparison(joinStatement.getChild(SimpleSQLParser.ComparisonContext.class,
                        0));
                nodes.add(new JoinNode(cond1, comparison, cond2));
            }
        }

        return nodes;
    }

    public static QualifiedField parseProjectionFragment(SimpleSQLParser.ProjectionFragmentContext pfc)
    {
        return parseQualifiedField(pfc.getChild(SimpleSQLParser.QualifiedFieldContext.class, 0));
    }

    public static ProjectNode parseProjections(SimpleSQLParser.ProjectionsContext projections)
    {
        SimpleSQLParser.ProjectionFragmentContext projectionFragment = projections.getChild(
                SimpleSQLParser.ProjectionFragmentContext.class, 0);

        ProjectNode projNode = new ProjectNode();
        // we hit a '*'-query!
        if (projectionFragment == null)
        {
            projNode.setType(SelectionType.STAR);
        }
        else
        {
            projNode.setType(SelectionType.FIELDS);

            List<SimpleSQLParser.ProjectionFragmentContext> projectionFragments = projections.getRuleContexts(
                    SimpleSQLParser.ProjectionFragmentContext.class);
            for (SimpleSQLParser.ProjectionFragmentContext projection : projectionFragments)
            {
                projNode.addProjection(parseProjectionFragment(projection));
            }
        }
        return projNode;
    }

    public static QualifiedField parseQualifiedField(SimpleSQLParser.QualifiedFieldContext qfc)
    {
        SimpleSQLParser.TablePrefixContext tpc = qfc.getChild(SimpleSQLParser.TablePrefixContext.class, 0);
        SimpleSQLParser.TableFieldContext tfc = qfc.getChild(SimpleSQLParser.TableFieldContext.class, 0);

        return new QualifiedField(tpc.getText(), tfc.getText());
    }

    public static List<RelationNode> parseRelations(SimpleSQLParser.RelationsContext relations)
    {
        List<RelationNode> nodes = new ArrayList<>();
        List<SimpleSQLParser.RelationFragmentContext> relationFragments = relations.getRuleContexts(
                SimpleSQLParser.RelationFragmentContext.class);

        for (SimpleSQLParser.RelationFragmentContext relationFragment : relationFragments)
        {
            RelationNode r = new RelationNode(relationFragment.getText());
            nodes.add(r);
        }
        return nodes;
    }

    public static List<SelectNode> parseSelectNodes(SimpleSQLParser.SelectionsContext selections)
    {
        List<SelectNode> nodes = new ArrayList<>();

        List<SimpleSQLParser.SelectionContext> selectionContexts = selections.getRuleContexts(
                SimpleSQLParser.SelectionContext.class);
        for (SimpleSQLParser.SelectionContext selection : selectionContexts)
        {
            SimpleSQLParser.FieldComparisonContext select = selection.getChild(
                    SimpleSQLParser.FieldComparisonContext.class, 0);
            if (select != null)
            {
                QualifiedField cond1 = parseFieldIdentifier(select.getChild(
                        SimpleSQLParser.FieldIdentifierContext.class, 0));
                Comparison comparison = parseComparison(select.getChild(SimpleSQLParser.ComparisonContext.class, 0));
                String compareValue = select.getChild(SimpleSQLParser.SelectionFragmentContext.class, 0).getText();
                nodes.add(new SelectNode(cond1, comparison, compareValue));
            }
        }

        return nodes;
    }
}
