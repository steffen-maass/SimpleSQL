package edu.gatech.coc.cs6422.group16.algebraTree;

public enum Comparison
{
    EQUAL, LESS, LESSOREQUAL, GREATEROREQUAL, GREATER;

    @Override
    public String toString()
    {
        switch (this)
        {
            case EQUAL:
                return "=";
            case LESS:
                return "<";
            case LESSOREQUAL:
                return "<=";
            case GREATEROREQUAL:
                return ">=";
            case GREATER:
                return ">";
            default:
                return "";
        }
    }
}
