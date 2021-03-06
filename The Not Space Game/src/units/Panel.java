package units;

import intents.CastAbilityIntent;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import abilities.SingleParticleAbility;

import core.Ability;
import core.Player;
import core.Unit;
import core.XYPair;

public class Panel extends Unit {

    /**
     *
     */
    private static final long serialVersionUID = 9198027583121777944L;

    public Panel(int xpos, int ypos, Player controller) {
        owner = controller;
        moveTo(new XYPair(xpos, ypos));
        mobile = true;

        canCollide = true;
        maxhealth = 600;
        health = 600;

        energy = 425;
        energyregen = 60;
        maxenergy = 601;

        healthregen = 50;

        height = 40;
        width = 40;

        basicAttack = new SingleParticleAbility(new Drone(center().x, center().y, owner));
        basicAttack.energyCost = 600;
        defaultIntents.add(new CastAbilityIntent(basicAttack));
        isSquare_notRound = false;
    }

    @Override
    public void onTick(int delta) {

        redraw = (health != maxhealth || energy != maxenergy);

    }

    @Override
    public BufferedImage draw() {

        img = (BufferedImage) super.draw();

        Graphics2D g2 = img.createGraphics();

        g2.setColor(getEnergyColor());

        g2.setStroke(new BasicStroke(3));

        g2.fillOval(2, 2, width - 4, height - 4);
        g2.setColor(controllerColor());
        g2.drawOval(2, 2, width - 4, height - 4);
        g2.fillOval(9, 9, 13, 13);

        return img;
    }

}
