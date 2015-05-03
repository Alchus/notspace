package units;

import intents.MoveAlongVectorIntent;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import abilities.SingleParticleAbility;

import core.Ability;
import core.Intent;
import core.Player;
import core.Unit;
import core.XYPair;

public class Marine extends Unit implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 9198027583121777944L;

    @SuppressWarnings("serial")
    public Marine(int xpos, int ypos, Player player) {
        owner = player;
        moveTo(new XYPair(xpos, ypos));

        mobile = true;

        isHero = true;

        canCollide = true;
        maxhealth = 500;
        health = 1000;

        energy = 200;
        energyregen = 30;
        maxenergy = 200;

        healthregen = 0;

        height = 40;
        width = 40;
        itemSlots = 6;

        movespeed = 200.;

        Unit ba_particle = new GenericMissile(position(), owner, new XYPair(10, 10)) {
            public void onCollide(Unit u) {
                if (u.owner.isHostileTo(owner)) {
                    u.damage(200, this);
                    destroy(SELFDESTRUCT);
                }
            }
        };
        ba_particle.movespeed = 1200.;
        Ability ba = new SingleParticleAbility(ba_particle) {
            public ArrayList<Intent> particleIntents() {
                ArrayList<Intent> di = new ArrayList<Intent>();
                di.add(new MoveAlongVectorIntent(center().vectorTo(targetPoint).clone()).clone());
                return di;
            }
        };

        ba.cooldown = 400;
        basicAttack = ba;

    }

    @Override
    public void onTick(int delta) {

        redraw = (health != maxhealth || energy != maxenergy);

    }

    public void generateBounds() {
        bounds = new Area(new Ellipse2D.Double(position().x, position().y, width,
                height));
    }

    @Override
    public BufferedImage draw() {

        img = (BufferedImage) super.draw();

        Graphics2D g2 = img.createGraphics();

        g2.setColor(getEnergyColor());

        g2.setStroke(new BasicStroke(3));

        g2.fillRect(2, 2, width - 4, height - 4);
        g2.setColor(controllerColor());
        g2.drawRect(2, 2, width - 4, height - 4);
        g2.fillRect(9, 9, 13, 13);

        drawHealthBar(g2, 10, 10);

        return img;
    }

}
