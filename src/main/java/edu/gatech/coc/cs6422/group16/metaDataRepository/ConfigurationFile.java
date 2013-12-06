package edu.gatech.coc.cs6422.group16.metaDataRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: nIcKcHEn
 * Date: 10/31/13
 * Time: 3:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationFile extends Files {
    private HashMap<ConfigurationType, String> confPars;
    public static String databaseDirectory = "db/";

    public enum ConfigurationType {
        DATABASE,INPUT
    }

    public ConfigurationFile() {
        super(null, GenConfigurationFileName(null));
        confPars = new HashMap<>();
        ReadFile();
    }

    public ConfigurationFile(String str) {
        super(null, GenConfigurationFileName(str));
        confPars = new HashMap<>();
        ReadFile();
    }

    private void InitiateConfigParameters() {
        confPars.clear();
        confPars.put(ConfigurationType.DATABASE, GenDatabaseInfoFileName(null));
        confPars.put(ConfigurationType.INPUT, null);
    }

    public void CloseFile() {
        try {
            fileLineReader.close();
        } catch (IOException e) {

        } finally {

        }
        try {
            fileReader.close();
        } catch (IOException e) {

        }
    }

    public void ReadFile() {
        if(OpenFile("Configuration") == true) {
            InitiateConfigParameters();
            try {
                String line;
                String delimiter = "=";
                while((line = fileLineReader.readLine()) != null) {
                    //allow for empty line
                    line = line.trim();
                    if(line.isEmpty() == true)
                        continue;
                    //allow for annotation line starting with a #
                    if(line.charAt(0) == '#')
                        continue;
                    String[] parts = line.split(delimiter);
                    if(parts.length != 2) {
                        String msg = "Line " + fileLineReader.getLineNumber() + " from configuration file " + fileName + " has more than two fields.";
                        throw new IndexOutOfBoundsException();
                    }
                    if(parts[1].trim().isEmpty() == false) {
                        ConfigurationType type = ConfigurationType.valueOf(parts[0].trim().toUpperCase());
                        confPars.put(type, parts[1].trim());
                    }
                }
            } catch (IOException E) {
                CleanUp();
                System.out.println("Error parsing line " + fileLineReader.getLineNumber() + " from configuration file " + fileName + ".");
            } catch (IndexOutOfBoundsException E) {
                CleanUp();
                System.out.println();
            }
        }
        else {

        }
    }

    @Override
    protected void CleanUp() {
        super.CleanUp();
        confPars.clear();
    }

    public boolean SetConfigurationValue(ConfigurationType confType, String val) {
        if(confType == ConfigurationType.DATABASE) {
            if(confPars.get(ConfigurationType.DATABASE) != GenDatabaseInfoFileName(val))  {
                confPars.put(ConfigurationType.DATABASE, GenDatabaseInfoFileName(val));
                return true;
            }
        }
        return false;
    }

    public String GetConfigurationValue(ConfigurationType type) {
        return confPars.get(type);
    }

    public static String GenDatabaseDirectoryFromFile(String name) {
        String str = name.substring(0, name.lastIndexOf(".ind"));
        return str;
    }
    /*
    public String GetInputFileName(ConfigurationType type)
    {
        return confPars.get(type);
    }
    */


    public static String GenConfigurationFileName(String conf) {
        if(conf == null)
            return "config.conf";
        else
            return conf + ".conf";
    }


    //static methods used to generate file name patterns

    public static String GenDatabaseInfoFileName(String name) {
        if(name == null || name.trim().isEmpty())
            return "database.ind";
        else
            return name + ".ind";
    }

    public static String GenDatabaseDirectory(String name) {
        if(name == null || name.trim().isEmpty())
            return databaseDirectory + "database/";
        else
            return databaseDirectory + name + "/";
    }

    public static String GenDatabaseInfoFileFullPath(String name) {
        if(name == null || name.trim().isEmpty())
            return databaseDirectory + "database.ind";
        else
            return databaseDirectory + GenDatabaseInfoFileName(name);
    }

}
