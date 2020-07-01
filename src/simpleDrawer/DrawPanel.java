/*
 * DrawPanel.java
 *
 * Created on 2008/11/21, 18:28
 * @author  tadaki
 */
package simpleDrawer;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import utils.FileIO;

public class DrawPanel extends javax.swing.JPanel
        implements MouseListener, MouseMotionListener {

    private BufferedImage image = null;
    private Point point = null;
    private BasicStroke stroke = null;
    private boolean eraser = false;
    private BasicStroke eraserStroke;//消しゴム用ストローク

    /**
     * Creates new form DrawPanel
     */
    public DrawPanel() {
        initComponents();
    }

    /**
     * 初期化：イメージ初期化、イベントリスナー登録、線属性初期化
     */
    public void initialize() {
        initializeImage();
        addMouseListener(this);
        addMouseMotionListener(this);
        stroke = new BasicStroke(1, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        eraserStroke = new BasicStroke(20.0f,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        if (image == null) {
            return;
        }
        //イメージを表示する
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), this);
    }

    /**
     * 描画イメージを初期化する
     */
    public void initializeImage() {
        Dimension dimension = getPreferredSize();
        //空のイメージ生成
        image = new BufferedImage(dimension.width, dimension.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(this.getBackground());//背景色で塗りつぶし
        g.fillRect(0, 0, dimension.width, dimension.height);
    }

    /**
     * イメージの保存
     *
     * @param file 保存先ファイル
     */
    public void saveImage(File file) {
        if (!fileChooser.FileUtilGUI.checkWritable(file)) {
            return;
        }
        try ( FileOutputStream out = new FileOutputStream(file)) {
            String ext = FileIO.getExtention(file.getName());
            javax.imageio.ImageIO.write(image, ext, out);
            String message
                    = "イメージを" + file.getName() + "に保存しました。";
            fileChooser.FileUtilGUI.showMessage(message);
        } catch (IOException ex) {
            fileChooser.FileUtilGUI.showError(ex.getMessage());
        }
    }

    /**
     * 線幅変更
     *
     * @param w 新しい線幅
     */
    public void setLineWidth(int w) {
        if (w < 1) {
            w = 1;
        }
        stroke = new BasicStroke((float) w, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
    }

    public void setEraser(boolean eraser) {
        this.eraser = eraser;
    }

    /**
     * 直前の点と現在の点を結ぶ
     *
     * @param x
     * @param y
     */
    private void lineSegment(int x, int y) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        if (eraser) {//消しゴムの場合
            g.setColor(this.getBackground());
            g.setStroke(eraserStroke);
        } else {
            g.setColor(this.getForeground());
            g.setStroke(stroke);
        }
        //前の点から現在の点へ線を引く
        g.drawLine(point.x, point.y, x, y);
    }

    /**
     * * マウスイベントの動作 **************************************
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * マウスボタンを押す
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        //新たな位置の生成
        point = new Point(e.getPoint());
    }

    /**
     * マウスボタンを離す
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (point != null) {
            lineSegment(e.getX(), e.getY());
            point = null;//現在の点を消去
        }
        repaint();
    }

    /**
     * マウスドラッグ
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (point != null) {
            lineSegment(e.getX(), e.getY());
            point = new Point(e.getPoint());//現在の点を更新
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(600, 600));
        setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
