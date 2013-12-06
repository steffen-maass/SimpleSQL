package edu.gatech.coc.cs6422.group16.metaDataRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;


/**
 * Created with IntelliJ IDEA.
 * User: nIcKcHEn
 * Date: 10/31/13
 * Time: 4:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseInfoFile extends Files {

    private HashSet<String> relationSet;
    private HashMap<String, Vector<AttributeInfo>> attributeMap;


    public DatabaseInfoFile(String str) {
        super(ConfigurationFile.databaseDirectory, str);
        relationSet = new HashSet<>();
        attributeMap = new HashMap<>();
        ReadFile();
    }

    public void ReadFile() {
        if(OpenFile("Database information file") == true) {
            relationSet.clear();
            attributeMap.clear();
            try {
                String line;
                String delimiter = ",";
                String typeDelimiter = ":";

                //skip empty lines
                do {
                    line = fileLineReader.readLine();
                }while(line != null && (line.trim().isEmpty() || line.trim().charAt(0) == '#'));
                if(line == null) {
                    String msg = "Line " + fileLineReader.getLineNumber() + " from database information file " + fileName + ": an error occurred while reading the relation table.";
                    throw new RuntimeException(msg);
                }

                //the first line: a table for all the relations
                String[] parts = line.split(delimiter);
                Vector<String> relVector = new Vector<>(parts.length);
                for(String str : parts) {
                    str = str.trim();
                    relationSet.add(str);
                    attributeMap.put(str, new Vector<AttributeInfo>());
                    relVector.add(str);
                }

                //read consecutive lines,each for a specific relation in the order
                int i = 0;          //attribute counter
                while((line = fileLineReader.readLine()) != null) {
                    line = line.trim();
                    if(line.isEmpty())
                        continue;
                    if(line.charAt(0) == '#')
                        continue;
                    //if the counter is equal to parts.length, the number of relations has exceeded that is indicated
                    if(i == parts.length) {
                        String msg = "Line " + fileLineReader.getLineNumber() + " from statistics file " + fileName + " has exceeded the number of relations.";
                        throw new IndexOutOfBoundsException(msg);
                    }

                    //attributes are spaced by comma
                    String[] attributes = line.split(delimiter);
                    for( String attr : attributes) {
                        //each attribute is represented by multiple segments spaced by colon
                        //ATTRIBUTE_NAME: ATTRIBUTE_TYPE:CONSTRAINT....
                        String[] singleField = attr.split(typeDelimiter);
                        int constraint = 0;
                        if(singleField.length > 2) {
                            for(int it = 2; it < singleField.length; it++) {
                                constraint = constraint | AttributeInfo.AttributeMask.GetMask(singleField[it].trim());
                            }
                        }
                        AttributeInfo.AttributeType attrType = AttributeInfo.AttributeType.valueOf(singleField[1].trim().toUpperCase());
                        if(attrType == null) {
                            String msg = "Error parsing line " + fileLineReader.getLineNumber() + " from database information file " + fileName + ": attribute type can't be recognized.";
                            throw new RuntimeException(msg);
                        }
                        attributeMap.get(relVector.elementAt(i)).add(new AttributeInfo(singleField[0].trim(), attrType, constraint));
                    }
                    i++;
                }
                if(i != parts.length) {
                    String msg = "Statistics file " + fileName + " has fewer number of attributes than indicated.";
                    throw new IndexOutOfBoundsException(msg);
                }

            } catch (IOException E) {
                CleanUp();
                System.out.println("Error parsing line " + fileLineReader.getLineNumber() + " from database information file " + fileName + ".");
            } catch (IndexOutOfBoundsException E) {
                CleanUp();
                System.out.println(E.getMessage());
            } catch (RuntimeException E) {
                CleanUp();
                System.out.println(E.getMessage());
            }
        }
    }

     @Override
     protected void CleanUp(){
         super.CleanUp();
         relationSet.clear();
         attributeMap.clear();
     }


    public HashSet<String> GetRelations() {
        return relationSet;
    }

    public Vector<AttributeInfo> GetAttributes(String rel) {
        return attributeMap.get(rel);
    }

    public boolean IsRelationValid(String rel) {
        return relationSet.contains(rel);
    }

    public boolean IsAttributeValid(String rel, AttributeInfo attrInfo) {
        Vector<AttributeInfo> attrVec = attributeMap.get(rel);
        if(attrVec == null)
            return false;
        return attrVec.contains(attrInfo);
    }

    public boolean IsAttributeValid(String rel, String attr) {
        Vector<AttributeInfo> attrVec = attributeMap.get(rel);
        if(attrVec == null)
            return false;
        for(AttributeInfo attrInfo : attrVec) {
            if(attrInfo.GetAttribute().equals(attr))
                return true;
        }
        return false;
    }

    public boolean AttributeHasIndex(String rel, AttributeInfo attr) {
        Vector<AttributeInfo> attrVec = attributeMap.get(rel);
        if(attrVec == null)
            return false;
        for(AttributeInfo attrInfo : attrVec) {
            if(attrInfo == attr) {
                return attrInfo.IsIndex() || attrInfo.IsPrimary();
            }
        }
        return false;
    }

    public boolean AttributeHasIndex(String rel, String attr) {
        Vector<AttributeInfo> attrVec = attributeMap.get(rel);
        if(attrVec == null)
            return false;
        for(AttributeInfo attrInfo : attrVec) {
            if(attrInfo.GetAttribute().equals(attr)) {
                return attrInfo.IsIndex() || attrInfo.IsPrimary();
            }
        }
        return false;
    }
}
