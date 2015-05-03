package units;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import core.*;

public class GenericMissile extends Unit {

    /**
     *
     */
    private static final long serialVersionUID = -1734296481596001430L;

    public GenericMissile(XYPair location, Player controller, XYPair dimensions, Intent[] intents) {
        owner = controller;
        moveTo(location);

        mobile = true;
        canCollide = true;
        collidesWithEnemies = true;
        maxhealth = 1;
        health = 1;
        movespeed = 100;
        destroyOnOffMap = true;
        healthregen = 0;
        height = (int) dimensions.x;
        width = (int) dimensions.y;
        for (Intent i : intents) {
            defaultIntents.add(i);
        }
        affectsOthersPathing = false;
        isSquare_notRound = false;
    }

    public GenericMissile(XYPair location, Player controller, XYPair dimensions, Intent intent) {
        this(dimensions, controller, dimensions, new Intent[0]);
        defaultIntents.add(intent);
    }

    public GenericMissile(XYPair position, Player owner, XYPair dimensions) {
        this(position, owner, dimensions, new Intent[0]);
    }

    public BufferedImage draw() {

        img = (BufferedImage) super.draw();

        Graphics2D g2 = img.createGraphics();

        g2.setColor(controllerColor());

        g2.setStroke(new BasicStroke(3));

        g2.fillOval(2, 2, width - 4, height - 4);
        g2.setColor(controllerColor());
        g2.drawOval(2, 2, width - 4, height - 4);
        g2.fillOval(1, 1, 8, 8);

        return img;
    }

}
