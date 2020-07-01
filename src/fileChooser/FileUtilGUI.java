package fileChooser;

import java.io.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.FileIO;

/**
 * fileChooserに関連したファイル関係の処理
 * @author tadaki
 */
public class FileUtilGUI {

    /**
     * このクラスのインスタンスは作らない
     */
    private FileUtilGUI() {
    }

    /**
     * fileの内容を文字列として返す
     *
     * @param file 読み込むfile
     * @return fileから読み込まれた文字列
     */
    static public String openFile(File file) {
        if (!file.canRead()) {//ファイルが読めることを確認
            showError(file.getName() + " は読めません");
            return null;
        }
        //読み出し開始
        BufferedReader in;
        try {
            in = FileIO.openReader(file);//readerを開く
        } catch (IOException ex) {
            showError(ex.getMessage());
            return null;
        }
        try {
            String str = FileIO.readFromReader(in);//readerから読み出し
            return str;
        } catch (IOException ex) {
            showError(ex.getMessage());
            return null;
        }
    }

    /**
     * textAreaの内容をfileに保存する。
     *
     * @param file　保存先file
     * @param text 保存する文字列
     */
    static public void saveFile(File file, String text) {
        if (!checkWritable(file)) {//ファイルに書けることを確認
            //checkWritableの中からエラー送信済
            return;
        }
        try {
            //保存開始
            BufferedWriter out;
            try {
                out = FileIO.openWriter(file);
            } catch (FileNotFoundException ex) {
                showError(ex.getMessage());
                return;
            }
            out.write(text);
            out.close();
        } catch (IOException ex) {
            showError(ex.getMessage());
        }
    }

    /**
     * file への書き込み可能性を確認
     *
     * @param file
     * @return 書き込み可能ならばtrue
     */
    static public boolean checkWritable(File file) {
        boolean isWritable = true;
        if (file.isFile()) {//ファイルが存在する場合
            if (!file.canWrite()) {//上書き可能性の確認
                showError(file.getName() + " に書き込めません");
                return false;
            } else {
                if (!checkOverwrite(file.getName())) {
                    return false;
                }
            }
        } else {
            try {
                if (!file.createNewFile()) {//新規ファイル作成
                    showError(file.getName() + " を生成できません");
                    return false;
                }
            } catch (IOException ex) {
                showError(ex.getMessage());
                return false;
            }
        }
        return isWritable;
    }

    /**
     * 上書き保存の確認ダイアログを表示
     *
     * @param filename　保存先ファイル名
     * @return 上書きする場合true
     */
    static public boolean checkOverwrite(String filename) {
        boolean b = true;
        String message = filename + "は存在します。上書きしますか？";
        int answer = JOptionPane.showConfirmDialog(
                new JFrame(), message, "上書き確認",
                JOptionPane.OK_CANCEL_OPTION);
        if (answer != JOptionPane.OK_OPTION) {
            b = false;
        }
        return b;
    }

    /**
     * エラーを示すダイアログを表示
     *
     * @param message エラーメッセージ
     */
    static public void showError(String message) {
        JOptionPane.showMessageDialog(
                new JFrame(), message, "エラー発生",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * メッセージを示すダイアログを表示
     *
     * @param message エラーメッセージ
     */
    static public void showMessage(String message) {
        JOptionPane.showMessageDialog(
                new JFrame(), message, "メッセージ",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
