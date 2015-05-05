package core;

import intents.IdleIntent;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Unit implements Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = 6543292141761606796L;

    public ArrayList<Unit> children = new ArrayList<Unit>();

    public boolean found = false; // For network mapping
    public Unit parent = null;
    public Game game;
    public boolean isProducer = false;
    public boolean isConsumer = false;
    private ArrayList<Intent> intents = new ArrayList<Intent>();
    private ArrayList<Item> items = new ArrayList<Item>();

    // unit controllers
    public static final int PLAYER1 = 1;

    public static final int PLAYER2 = 2;
    public static final int PASSIVE = 0;
    public static final int HOSTILE = -1;
    public static final int INTERFACE = -2;
    public static final int INTERFACE_LOCAL = -5;
    public static final int BLUEPRINT = -3;
    public static final int MISSILE = -4;
    // reasons for death
    public static final int HEALTH = 1;

    public static final int SELL = 2;
    public static final int SELFDESTRUCT = 3;
    public Unit target = null;

    public Ability basicAttack = new Ability();

    long born = System.currentTimeMillis();

	// Default values. These should be overwritten in subclass constructor.
    public Player owner = Player.PASSIVE;

    public boolean active = true;
    public boolean visible = true;
    public boolean selectable = false;
    public boolean initialized = false;
    public double movespeed = 100; // units per second

    public int itemSlots = 0;

    private XYPair position = new XYPair(100, 100);

    public boolean mobile = true;

    public boolean selected = false;

    public XYPair velocity = new XYPair(0, 0);

    public boolean canCollide = false;

    public boolean affectsOthersPathing = true;
    public boolean collidesWithAllies = false;
    public boolean collidesWithEnemies = false;
    public boolean collidesWithTerrain = true;
    public int height = 10;

    public int width = 10;
    // int radius = 0; //
    public int maxhealth = 5;

    public double health = 5;
    public double energy = 0;

    public int maxenergy = 5;
    public int maxshield = 0;

    public int shield = 0;
    public boolean isHero = false;

    public boolean built = false;
    public transient BufferedImage img;

    public boolean redraw = true;// Indicates if the unit should be redrawn. TODO:
    // Notify grandparents.
    public double energyregen = 1.0; // per second

    public double healthregen = 0.0; // per second
    public transient Area bounds = new Area(new Rectangle((int) position.x,
            (int) position.y, width, height));
    public boolean alive = true;

    public boolean limitNextMove = false;// If the unit's next movement tick should

    // have a distance cap
    public XYPair moveLimit;
    public ArrayList<Intent> defaultIntents = new ArrayList<Intent>();

    public int healthBarOffsetX = -20;

	// Notify grandparents.
    public int healthBarOffsetY = -25;

    public int healthBarLength = 40;

    public boolean isSquare_notRound = true;

    public Unit() {
        game = Window.main.game;
    }

    public XYPair position() {
        return position.clone();
    }

    public XYPair center() {
        return position().sum(width / 2., height / 2.);
    }

    public void moveTo(XYPair location) {
        position = location.clone();
    }

    public void shift(XYPair vector) {
        position.add(vector);
    }

    @Override
    public Unit clone() {
        try {
            Unit copy = (Unit) super.clone();
            copy.defaultIntents = new ArrayList<Intent>();
            copy.intents = new ArrayList<Intent>();
            for (Intent i : defaultIntents) {
                copy.defaultIntents.add(i.clone());
            }
            copy.intents.clear();
            return copy;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Color controllerColor() {
        if (selected) {
            return Color.white;
        }
        if (owner == Player.PLAYER1) {
            return Color.blue;
        }
        if (owner == Player.PLAYER2) {
            return Color.green;
        }
        if (owner == Player.PLAYER3) {
            return Color.orange;
        }
        if (owner == Player.PLAYER4) {
            return Color.magenta;
        }
        if (owner == Player.HOSTILE) {
            return Color.red;
        }
        if (owner == Player.INTERFACE) {
            return Color.gray;
        }

        return Color.pink; // this shouldn't happen

    }

    public void damage(double amount, Unit source) {
        // TODO: shields
        health -= amount;
    }

    public void damage(double amount, Unit source, int type) {
        // for armor types etc. TODO: NYI.
        damage(amount, source);
    }

    public Unit deepestComponentAt(int x2, int y2) {

        if ((x2 >= position.x) && (y2 >= position.y)
                && ((x2 - position.x) <= width)
                && ((y2 - position.y) <= height)) {
            return this;
            /*
             * TODO: Check children for hits for (Unit u : children){ Unit hit =
             * u.deepestComponentAt(x2 - (int)x, y2-(int)y); if (hit != null)
             * return hit; }
             */
        }

        return null;
    }

    public void destroy(int cause) {
        alive = false;
    }

    public BufferedImage draw() {
        if (img == null) {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
        for (Unit u : children) { // redraw if any children need redrawing
            if (u.redraw) {
                redraw = true;
            }
        }
        if (!redraw) {
            return img; // if neither this nor its children need redrawing,
        }						// don't redraw.

        Graphics2D g2 = img.createGraphics();

        g2.setComposite(AlphaComposite.DstIn);// clears the previous frame.
        g2.setColor(new Color(255, 255, 255, 0));// should investigate
        // performance for this task
        g2.fillRect(0, 0, width, height); // ...eventually....
        g2.setPaintMode();

        for (Unit u : children) {
            g2.drawImage(u.draw(), (int) u.position.x, (int) u.position.y,
                    new Color(0, 0, 0, 0), Window.main);
        }
        redraw = false;
        return img;
    }

    public void drawHealthBar(Graphics2D g2, int xOffset, int yOffset) {
        // TODO: Line thickness check
        g2.setColor(Color.RED);
        g2.drawLine((int) position.x + healthBarOffsetX + xOffset,
                (int) position.y + healthBarOffsetY + yOffset, (int) position.x
                + healthBarOffsetX + xOffset + healthBarLength,
                (int) position.y + healthBarOffsetY + yOffset);
        g2.setColor(Color.GREEN);
        g2.drawLine((int) position.x + healthBarOffsetX + xOffset,
                (int) position.y + healthBarOffsetY + yOffset, (int) position.x
                + healthBarOffsetX + xOffset + healthBarLength
                * (int) (health / maxhealth), (int) position.y
                + healthBarOffsetY + yOffset);

    }

    void enforceWorldBoundaries() {

        boolean offMap = false;

        // TODO: use this.bounds instead?
        if ((position.x + width) > game.width) {
            position.x = game.width - width;
            offMap = true;
        }
        if (position.x < 0.) {
            position.x = 1;
            offMap = true;
        }
        if ((position.y + height) > game.height) {
            position.y = game.height - height;
            offMap = true;
        }
        if (position.y < 0.) {
            position.y = 1;
            offMap = true;
        }

        if (offMap) {
            offMap();
        }
    }

    public void generateBounds() {
        if (isSquare_notRound) {
            bounds = new Area(new Rectangle((int) position.x, (int) position.y,
                    width, height));
        } else {
            bounds = new Area(new Ellipse2D.Double(position().x, position().y, width,
                    height));
        }
    }

    public Area getBounds() {
        if (bounds == null) {
            generateBounds();
        }
        return (Area) bounds.clone();
    }

    public Color getEnergyColor() {
        energy = Math.min(maxenergy, energy);
        energy = Math.max(0, energy);

        int energyLevel = (int) ((255 * energy) / maxenergy);
        return new Color((255 - energyLevel), energyLevel, energyLevel / 2);

    }

    void initialize() {

		// This method is basically a default constructor,
        // except it should be run after the subclass constructors
        refreshIntents();

        initialized = true;

    }

    public boolean isDead() {
        return !alive;
    }

    public synchronized void issueCommand(Intent incoming) {
        if (incoming.removesEqualOrLowerPrority) {
            for (Intent i : intents) {
                if (i.priority <= incoming.priority) {
                    i.expired = true;
                }
            }
        } else if (incoming.removesLowerPrority) {
            for (Intent i : intents) {
                if (i.priority < incoming.priority) {
                    i.expired = true;
                }
            }
        }

        intents.add(incoming);
        Collections.sort(intents);
    }

    boolean redrawItems = false;

    public boolean giveItem(Item i) {
        redrawItems = true;
        for (Item j : items) {
            if (j.name.hashCode() == i.name.hashCode()) {
                j.quantity += i.quantity;
                return true;
            }
        }
        if (items.size() >= itemSlots) {
            return false;
        }
        items.add(i);
        System.out.println("Gave a " + i.getClass() + " to a " + this.getClass());
        return true;
    }

    public Item getItem(int index) {
        if (items.size() > index) {
            return items.get(index);
        }
        return null;
    }

    public Unit nearestUnit(Player controlledBy) {
        Unit closest = null;
        double dist = Double.MAX_VALUE;
        for (Unit u : game.elements) {
            if (u.equals(this)) {
                continue;
            }
            if (u.owner != controlledBy) {
                continue;
            }
            if (position.vectorTo(u.position).magnitude() < dist) {
                dist = position.vectorTo(u.position).magnitude();
                closest = u;
            }
        }
        return closest;
    }

    public boolean destroyOnOffMap = false;

    void offMap() {

        if (destroyOnOffMap) {
            destroy(0);
        }

		// TODO: use this.bounds instead?
        if ((position.x + width) > game.width) {
            position.x = game.width - width;
            velocity = new XYPair(0, 0);
        }
        if (position.x < 0.) {
            position.x = 1;
            velocity = new XYPair(0, 0);
        }
        if ((position.y + height) > game.height) {
            position.y = game.height - height;
            velocity = new XYPair(0, 0);
        }
        if (position.y < 0.) {
            position.y = 1;
            velocity = new XYPair(0, 0);
        }
    }

    public void onClick() {

    }

    public void onCollide(Unit u) {

    }

    public void refreshIntents() {

        for (Intent i : defaultIntents) {
            intents.add(i.clone());
        }

        if (intents.isEmpty()) {
            System.out.println("Empty defaultIntents on a " + this.getClass());
            intents.add(new IdleIntent());
        }
    }

    public void targetSelected(int tarX, int tarY) {

    }

    public void targetSelected(Unit tar) {

    }

    final void tick(int delta) { // delta in milli-gameseconds

        if (!initialized) {
            initialize();
        }

        onTick(delta);

        health += (healthregen * delta / 1000.0);
        if (health > maxhealth) {
            health = maxhealth;
        }
        if (health < 0) {
            destroy(HEALTH);
        }

        energy += (energyregen * delta / 1000.0);
        if (energy > maxenergy) {
            energy = maxenergy;
        }
        if (energy < 0) {
            energy = 0;
        }

        generateBounds();
        enforceWorldBoundaries();

        updateIntents();

        double x = position.x;
        double y = position.y;
        double xvel = velocity.x;
        double yvel = velocity.y;
        double newx = x;
        double newy = y;

        double vel = Math.sqrt((xvel) * (xvel) + (yvel) * (yvel));
        if (vel > 0) {
            xvel = (xvel) * (movespeed / vel);
            yvel = (yvel) * (movespeed / vel);
        }

        if (mobile) {

            newx = x + (xvel / 1000.0 * delta);
            newy = y + (yvel / 1000.0 * delta);
        }

        if (!isDead() && canCollide
                && ((xvel > 0.00001) || (yvel > 0.00001) || (xvel < -0.00001) || (yvel < -0.00001))) {

            Unit[] hits = Window.main.game.checkCollisions(new Area(
                    new Rectangle((int) newx, (int) newy, width, height)));
            for (Unit u : hits) {
                if (u == this) {
                    continue;
                }
                if (u.owner == Player.ITEMS) {
                    ItemUnit iu = (ItemUnit) u;
                    if (!iu.isDead()) {
                        boolean success = giveItem(iu.item);
                        if (success) {
                            iu.destroy(0);
                        }
                    }
                    continue;
                }

                boolean theseUnitsInteract = false;
                if ((owner.isAlliedTo(u.owner))) {
                    theseUnitsInteract = (collidesWithAllies && u.affectsOthersPathing);
                }
                if ((owner.isHostileTo(u.owner))) {
                    theseUnitsInteract = (collidesWithEnemies && u.affectsOthersPathing);
                }
                if (collidesWithTerrain && u.owner == Player.PASSIVE) {
                    theseUnitsInteract = u.affectsOthersPathing;
                }
                if (u.isDead()) {
                    theseUnitsInteract = false;
                }

                if (theseUnitsInteract && !isDead()) {
                    onCollide(u);
                    double uCenterx = u.position.x + u.width / 2;
                    double uCentery = u.position.y + u.height / 2;
                    double centerx = newx + width / 2;
                    double centery = newy + height / 2;
                    double dx = (centerx - uCenterx);
                    double dy = (centery - uCentery);
                    double ddx = delta * 1000. * dx
                            / Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 2);
                    double ddy = delta * 1000. * dy
                            / Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 2);

                    newx += ddx;
                    newy += ddy;
                }

            }
            XYPair changeVector = new XYPair(newx - x, newy - y);

            if (changeVector.magnitude() > movespeed) {
                changeVector = changeVector.unitVector().rescale(movespeed);
                System.out.println("Capped Move Speed");
            }

            if (limitNextMove) {
                if (changeVector.magnitude() > moveLimit.magnitude()) {
                    changeVector = changeVector.unitVector().rescale(
                            moveLimit.magnitude());
                }
            }

            x += changeVector.x;
            y += changeVector.y;

            position = new XYPair(x, y);

        }

    }

    private synchronized void updateIntents() {
        for (int i = 0; i < intents.size(); i++) {
            Intent in = intents.get(i);
            if (in.expired) {
                intents.remove(in);
            } else {
                break;
            }
        }

        if (intents.isEmpty()) {

            refreshIntents();
        }

        Collections.sort(intents);
        intents.get(0).update(this);

    }

    public void onTick(int delta) {

    }

	// TODO: Image files?
}
