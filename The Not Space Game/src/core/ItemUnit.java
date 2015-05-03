package core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import intents.HoldPositionIntent;

public class ItemUnit extends Unit {

    /**
     *
     */
    private static final long serialVersionUID = 9082922765704458985L;
    public static int baseHeight = 40;
    public static int baseWidth = 40;

    public Item item = null;

    public ItemUnit(Item item, XYPair position) {
        this.item = item;
        owner = Player.ITEMS;
        mobile = true;
        selected = false;
        canCollide = true;
        affectsOthersPathing = false;
        collidesWithAllies = true;
        collidesWithEnemies = false;
        collidesWithTerrain = true;
        height = baseHeight;
        width = baseWidth;
        defaultIntents.add(new HoldPositionIntent());
        this.moveTo(position);

    }

    public BufferedImage draw() {

        img = (BufferedImage) super.draw();

        img.getGraphics().drawImage(Item.icon, 0, 0, new Color(0, 0, 0, 0), Window.main);
        return img;
    }

    public boolean isDead() {
        return (super.isDead() || (item.quantity <= 0));
    }

}
