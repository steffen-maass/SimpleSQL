package edu.gatech.coc.cs6422.group16.metaDataRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * Created with IntelliJ IDEA.
 * User: nIcKcHEn
 * Date: 10/31/13
 * Time: 3:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class Files {
    protected String fileName;
    protected String fileFullPath;
    protected FileReader fileReader;
    protected LineNumberReader fileLineReader;

    protected Files(String path, String name) {
        if(path == null)
            fileFullPath = name;
        else
            fileFullPath = path + name;
        fileName = name;
    }

    protected boolean OpenFile(String str) {
        try
        {
            fileReader = new FileReader(fileFullPath);
            fileLineReader = new LineNumberReader(fileReader);
        } catch (FileNotFoundException E)
        {
            System.out.println(str + " file: " + fileFullPath + " is not found.");
            CleanUp();
            return false;
        }
        return true;
    }


    public String GetFileName() {
        return fileName;
    }

    public String GetFileFullPath() {
        return fileFullPath;
    }

    protected void CleanUp() {
        fileName = null;
        fileFullPath = null;
        fileReader = null;
        fileLineReader = null;
    }
}
