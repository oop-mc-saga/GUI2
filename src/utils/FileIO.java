package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * utilities for operating files
 *
 * @author tadaki
 */
public class FileIO {
    public static final String nl = System.getProperty("line.separator");

    /**
     * Open BufferedWriter by specifing filename
     *
     * @param filename
     * @return BufferedWriter
     * @throws IOException
     */
    public static BufferedWriter openWriter(String filename)
            throws IOException {
        return openWriter(new File(filename));
    }

    public static BufferedWriter openWriter(File file)
            throws IOException {
        FileOutputStream fStream = new FileOutputStream(file);
        return new BufferedWriter(new OutputStreamWriter(fStream));
    }

    /**
     * Output objects with commas as delimitter
     *
     * @param out destination
     * @param objects list of objects
     * @throws IOException
     */
    public static void writeCSV(BufferedWriter out, Object... objects)
            throws IOException {
        writeVars(out, ',', objects);
    }

    /**
     * Output objects with space as delimitter
     *
     * @param out destination
     * @param objects list of objects
     * @throws IOException
     */
    public static void writeSSV(BufferedWriter out, Object... objects)
            throws IOException {
        writeVars(out, ' ', objects);
    }

    /**
     * Output objects with specified delimitter
     *
     * @param out densination
     * @param sep delimitter
     * @param objects list of objects
     * @throws IOException
     */
    public static void writeVars(
            BufferedWriter out, char sep, Object... objects)
            throws IOException {
        //connect objects with delimitter
        String str = object2String(sep, objects);
        out.write(str);
        out.newLine();
    }

    /**
     * Open BufferedReader by specified filename
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static BufferedReader openReader(String filename) throws
            IOException {
        return openReader(new File(filename));
    }

    public static BufferedReader openReader(File file) throws
            IOException {
        FileInputStream fStream = new FileInputStream(file);
        return new BufferedReader(new InputStreamReader(fStream));
    }

    /**
     * Read one line with space as delimitter
     *
     * @param in
     * @return
     * @throws java.io.IOException
     */
    public static String[] readSSV(BufferedReader in)
            throws IOException {
        return readVars(in, "\\s+");
    }

    /**
     * Read one line with comma as delimitter
     *
     * @param in
     * @return
     * @throws java.io.IOException
     */
    public static String[] readCSV(BufferedReader in)
            throws IOException {
        return readVars(in, "\\s*,\\s*");
    }

    /**
     * Read one line with specified delimitter
     *
     * @param in input
     * @param sep delimitter (regex allowed)
     * @return output
     * @throws java.io.IOException
     */
    public static String[] readVars(BufferedReader in, String sep)
            throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            return line.split(sep);
        }
        return null;
    }
    
    /**
     * Read lines from Reader
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static String readFromReader(BufferedReader in) throws IOException{
        StringBuilder sb=new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line).append(nl);
        }
        return sb.toString();
    }
    /**
     * Combine objects with delimitter
     *
     * @param sep　delimitter
     * @param objects
     * @return
     */
    public static String object2String(char sep, Object... objects) {
        StringBuilder sb = new StringBuilder();
        int n = objects.length;
        for (int i = 0; i < n - 1; i++) {
            sb.append(objects[i]).append(sep);
        }
        sb.append(objects[n - 1]);
        return sb.toString();
    }
    
    /**
     * Investigate file extention
     *
     * @param filename
     * @return 拡張子の文字列
     */
    static public String getExtention(String filename) {
        String ext = null;
        int index = filename.lastIndexOf(".");
        if (index > 0) {
            ext = filename.substring(index + 1);
            if (ext.length() < 1) {
                ext = null;
            }
        }
        return ext;
    }   
}
