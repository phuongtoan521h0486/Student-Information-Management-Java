package org.thd.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageRenderer extends DefaultTableCellRenderer {
    private static int width;
    private static int height;

    public ImageRenderer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();

        if (value instanceof ImageIcon) {
            ImageIcon originalIcon = (ImageIcon) value;
            ImageIcon resizedIcon = resizeIcon(originalIcon, width, height);
            label.setIcon(resizedIcon);
        }

        return label;
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}
