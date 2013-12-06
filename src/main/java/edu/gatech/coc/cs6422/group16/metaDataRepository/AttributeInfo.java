package edu.gatech.coc.cs6422.group16.metaDataRepository;

/**
 * Created with IntelliJ IDEA.
 * User: nIcKcHEn
 * Date: 10/31/13
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class AttributeInfo {
    private String Attribute;
    private AttributeType Type;
    private int Constraint;

    public final static class AttributeMask {
        public final static int PRIMARY = 0x01;
        public final static int INDEX = 0x02;

        public static int GetMask(String maskStr) {
            if(maskStr.equals("PRIMARY"))
                return PRIMARY;
            else if(maskStr.equals("INDEX"))
                return INDEX;
            else
                return 0;
        }
    }

    public enum AttributeType {
        STRING,
        INT
    }

    public AttributeInfo(String attr, AttributeType type) {
        Attribute = attr;
        Type = type;
        Constraint = 0;
    }

    public AttributeInfo(String attr, AttributeType type, int constraint) {
        Attribute = attr;
        Type = type;
        Constraint = constraint;
    }

    public void SetAttribute(String attr) {
        Attribute = attr;
    }

    public String GetAttribute() {
        return Attribute;
    }

    public void SetType(AttributeType type) {
        Type = type;
    }

    public AttributeType GetType() {
        return Type;
    }

    public void SetPrimary(boolean b) {
        if(b)
            Constraint = Constraint & AttributeMask.PRIMARY;
        else
            Constraint = Constraint | ~AttributeMask.PRIMARY;
    }

    public boolean IsPrimary() {
        return (Constraint & AttributeMask.PRIMARY) != 0;
    }

    public void SetIndex(boolean b) {
        if(b)
            Constraint = Constraint & AttributeMask.INDEX;
        else
            Constraint = Constraint | ~AttributeMask.INDEX;
    }

    public boolean IsIndex() {
        return (Constraint & AttributeMask.INDEX) != 0;
    }

    public void SetConstraint(int constraint) {
        Constraint = constraint;
    }

    public int GetConstraint() {
        return Constraint;
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
        final AttributeInfo rhs = (AttributeInfo) obj;
        if (this.Attribute != rhs.Attribute)
        {
            return false;
        }
        if (this.Type != rhs.Type)
        {
            return false;
        }
        return true;
    }
}
