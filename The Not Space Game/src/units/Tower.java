/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package units;

import abilities.SingleParticleAbility;
import abilities.TestBasicAttackAbility;
import core.Ability;
import core.Player;
import core.Unit;
import core.XYPair;
import intents.CastAbilityIntent;
import intents.MoveToUnitIntent;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Alchus
 */
public class Tower extends Unit{

    /**
     *
     */
    private static final long serialVersionUID = 9198027583121777944L;

    public Tower(int xpos, int ypos, Player controller) {
        owner = controller;
        moveTo(new XYPair(xpos, ypos));
        mobile = true;

        canCollide = true;
        maxhealth = 600;
        health = 600;

        energy = 0;
        energyregen = 200;
        maxenergy = 601;

        healthregen = 50;

        height = 40;
        width = 40;
        
        onTick(0);
        
//        GenericMissile gm = new GenericMissile(center(), owner, new XYPair(8,8));
//        gm.defaultIntents.add(new MoveToUnitIntent(target));
        basicAttack = new TestBasicAttackAbility();
        basicAttack.energyCost = 150;
        defaultIntents.add(new CastAbilityIntent(basicAttack));
        isSquare_notRound = false;
    }

    @Override
    public void onTick(int delta) {

        
            target = nearestUnit(Player.HOSTILE);
        try {
            basicAttack.targetPoint = nearestUnit(Player.HOSTILE).position();
        } catch (NullPointerException e) {
        }
        

    }

    @Override
    public BufferedImage draw() {

        img = (BufferedImage) super.draw();

        Graphics2D g2 = img.createGraphics();

        g2.setColor(controllerColor());
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(0, 0, height - 1, width - 1);
        // g2.fillOval(10, 10, 15, 15);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString("Tower", 3, 25);

        return img;
    }

}
