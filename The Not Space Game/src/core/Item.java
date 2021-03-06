package core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Item {

    public static int iconScale = 40;
    public static BufferedImage icon;
    protected int quantity = 1;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void changeQuantity(int by) {
        quantity += by;
    }
    String name = "Unnamed Item";
    long cooldown = 0;
    long lastUsed = 0;

    public Item() {
        icon = generateIcon();
    }

    public BufferedImage generateIcon() {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = img.createGraphics();

        g2.setColor(Color.white);

        g2.setStroke(new BasicStroke(3));

        g2.fillOval(2, 2, iconScale - 4, iconScale - 4);
        g2.setColor(Color.pink);
        g2.drawRect(2, 2, iconScale - 4, iconScale - 4);
        g2.fillOval(9, 9, 13, 13);

        return img;
    }

    public void activate() {

    }
}
