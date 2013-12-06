package edu.gatech.coc.cs6422.group16.metaDataRepository;


import edu.gatech.coc.cs6422.group16.algebraTree.QualifiedField;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.io.*;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: nIcKcHEn
 * Date: 10/20/13
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetaDataRepository {
    //configuration file
    private ConfigurationFile configFile;
    private DatabaseInfoFile databaseInfoFile;
    //Relation -> Stats
    private HashMap<String, StatisticsFile> statisticsFile;

    private boolean changedDatabase;

    private static MetaDataRepository instance = null;

    public static synchronized MetaDataRepository GetInstance() {
        if(instance == null) {
            instance = new MetaDataRepository();
        }
        return instance;
    }

    public static synchronized MetaDataRepository GetInstance(String confName) {
        if(instance == null) {
            instance = new MetaDataRepository(confName);
        }
        else {
            if(instance.GetConfigurationFileName().equals(ConfigurationFile.GenConfigurationFileName(confName)) == false) {
                instance.InstantiateInstance(confName);
            }
        }
        return instance;
    }

    private MetaDataRepository()
    {
        InstantiateInstance(null);
    }

    private MetaDataRepository(String confName)
    {
        InstantiateInstance(confName);
    }

    public void InstantiateInstance(String confName) {
        if(confName == null) {
            configFile = new ConfigurationFile();
        }
        else
            configFile = new ConfigurationFile(confName);
        //enforce reloading all files
        changedDatabase = true;
        ReadData();
    }

    public boolean ReadData() {
        if(configFile == null)
            return false;
        String dbName = configFile.GetConfigurationValue(ConfigurationFile.ConfigurationType.DATABASE);
        if(changedDatabase) {
            databaseInfoFile = new DatabaseInfoFile(dbName);
        }
        String dir = ConfigurationFile.GenDatabaseDirectoryFromFile(dbName);
        if(statisticsFile == null)
            statisticsFile = new HashMap<>();
        else
            statisticsFile.clear();
        for(String rel : databaseInfoFile.GetRelations()) {
            statisticsFile.put(rel, new StatisticsFile(dir, rel, databaseInfoFile.GetAttributes(rel)));
        }
        changedDatabase = false;
        return true;
    }
    //interfaces to the outside world

    public boolean SetDatabaseName(String val) {
        if(configFile == null)
            return false;
        changedDatabase = configFile.SetConfigurationValue(ConfigurationFile.ConfigurationType.DATABASE, val);
        ReadData();
        return true;
    }

    public String GetConfigurationFileName() {
        return configFile.GetFileName();
    }

    public String GetDatabaseInfoFileName() {
        return databaseInfoFile.GetFileName();
    }

    public boolean IsRelationValid(String rel) {
        return databaseInfoFile.IsRelationValid(rel);
    }

    public boolean IsAttributeValid(String rel, AttributeInfo attrInfo) {
        return databaseInfoFile.IsAttributeValid(rel, attrInfo);
    }

    public boolean IsAttributeValid(String rel, String attr) {
        return databaseInfoFile.IsAttributeValid(rel, attr);
    }

    public boolean IsAttributeValid(QualifiedField field) {
        return databaseInfoFile.IsAttributeValid(field.getRelation(), field.getAttribute());
    }

    public boolean AttributeHasIndex(String rel, AttributeInfo attr) {
        return databaseInfoFile.AttributeHasIndex(rel, attr);
    }

    public boolean AttributeHasIndex(String rel, String attr) {
        return databaseInfoFile.AttributeHasIndex(rel, attr);
    }

    public int GetDistinctValueOfAttribute(QualifiedField field) {
        return GetDistinctValueOfAttribute(field.getRelation(), field.getAttribute());
    }

    public int GetDistinctValueOfAttribute(String rel, String attr) {
        if(statisticsFile.get(rel) == null)
            return 0;
        else
            return statisticsFile.get(rel).GetDistinctValueOfAttribute(attr);

    }

    public int GetRelationSize(String rel) {

        if(statisticsFile.get(rel) == null)
            return 0;
        else
            return statisticsFile.get(rel).GetRelationSize();
    }

}
