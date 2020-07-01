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
 * ファイル操作関係のユーティリティ
 *
 * @author tadaki
 */
public class FileIO {
    public static final String nl = System.getProperty("line.separator");

    /**
     * ファイル名を指定してBufferedWriterを開く
     *
     * @param filename
     * @return 開いたBufferedWriter
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
     * カンマ区切りで、対象オブジェクトを出力する
     *
     * @param out 出力先
     * @param objects 出力するオブジェクト列（不定）
     * @throws IOException
     */
    public static void writeCSV(BufferedWriter out, Object... objects)
            throws IOException {
        writeVars(out, ',', objects);
    }

    /**
     * スペース区切りで、対象オブジェクトを出力する
     *
     * @param out 出力先
     * @param objects 出力するオブジェクト列（不定）
     * @throws IOException
     */
    public static void writeSSV(BufferedWriter out, Object... objects)
            throws IOException {
        writeVars(out, ' ', objects);
    }

    /**
     * 任意の区切り文字で、対象オブジェクトを出力する
     *
     * @param out 出力先
     * @param sep 区切り文字
     * @param objects 出力するオブジェクト列（不定）
     * @throws IOException
     */
    public static void writeVars(
            BufferedWriter out, char sep, Object... objects)
            throws IOException {
        //objectを区切り文字で転結した文字列を生成
        String str = object2String(sep, objects);
        out.write(str);
        out.newLine();
    }

    /**
     * ファイル名を指定してBufferedReaderを開く
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
     * 空白を区切り文字として、一行読みだす
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
     * カンマを区切り文字として、一行読みだす
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
     * BufferedReaderから一行読み、sepで分割した文字列配列を返す
     *
     * @param in 入力
     * @param sep 文字列の分割を指定する正規表現
     * @return 文字列
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
     * Readerから内容を読みだし、文字列として返す
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
     * Objectを区切り文字で連結する
     *
     * @param sep　区切り文字
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
     * ファイル名中の拡張子を調べる
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
