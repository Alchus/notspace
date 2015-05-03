package core;

import intents.CastAbilityIntent;
import intents.MoveAlongVectorIntent;
import intents.MoveAndShootAtMouseIntent;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.CopyOnWriteArrayList;

import abilities.MysticShotGunAbility;
//import items.Grenade;

import units.BuildPanelButton;
import units.DeleteButton;
import units.Drone;
import units.GenericBuildButton;
import units.LoadButton;
import units.Marine;
import units.Node;
import units.Panel;
import units.SaveButton;
import units.SelectButton;

public class Game implements MouseListener, MouseMotionListener,
        Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1303881493558763432L;

    /**
     *
     */
    public volatile boolean running = false;

    volatile CopyOnWriteArrayList<Unit> elements = new CopyOnWriteArrayList<>();
    public volatile ArrayList<Unit> babies = new ArrayList<>();
    int height = 1000;
    int width = 2200;

    public volatile int viewx = 0;
    public volatile int viewy = 0;

    Player localPlayer = Player.getPlayer(1);

    public static final short FREE_CAMERA = 0;
    public static final short LOCKED_CAMERA = 1;
    public static final short OVERRIDE_CAMERA = -1;
    public Unit cameraFocus;
    public short cameraMode;

    // Cursor type
    public int cursor = 0;

    public Unit selection; // Which unit is selected

    public static final int SELECTION = 0;

    public static final int POINTTARGET = 1;

    public static final int UNITTARGET = 2;

    public static final int MAPDRAG = 3;

    public static final int BLUEPRINT = 4;

    public static final int BASICATTACKTARGET = 5;

    public MouseEvent lastMousePos;
    public boolean mouse1State = false;
    public boolean mouse2State = false;

    int dragReferenceX = 0;
    int dragReferenceY = 0;

    public static char[] keys = {'A', 'D', 'W', 'S', 'Q', 'E', '1', '2', '3', '4'};
    XYPair playerMovementCommandVector = new XYPair(0, 0);

    public Game() {

    }

    public Unit[] checkCollisions(Area a) {
        ArrayList<Unit> hits = new ArrayList<Unit>();

        for (Unit v : elements) {

            if (!v.canCollide) {
                continue;
            }

            // TODO: Investigate performance
            Area x = (Area) a.clone();
            x.intersect(v.getBounds());
            if (!x.isEmpty()) {
                hits.add(v);
            }

        }
        Unit[] result = new Unit[hits.size()];
        result = hits.toArray(result);
        return result;
    }

    public Unit[] checkCollisions(Unit u) {

        ArrayList<Unit> hits = new ArrayList<Unit>();

        for (Unit v : elements) {
            if (u == v) {
                continue;
            }
            if (!v.canCollide) {
                continue;
            }

            // TODO: Investigate collision detection performance
            Area x = u.getBounds();
            x.intersect(v.getBounds());
            if (!x.isEmpty()) {
                hits.add(v);
            }

        }
        Unit[] result = new Unit[hits.size()];
        result = hits.toArray(result);
        return result;

    }

    Unit checkHits(MouseEvent e) {
        Unit hit = null;
        boolean consumed = false;
        for (Unit u : elements) {
            if (consumed) {
                break;
            }
			// this first pass only checks interface elements, as they have
            // higher
            // priority than other units.
            if (u.owner != Player.INTERFACE) {
                continue;
            }
            if (!u.selectable) {
                continue;
            }
            if (u.getBounds().contains(new Point(e.getX(), e.getY()))) {
                consumed = true;
                hit = u;
            }
        }

        for (Unit u : elements) {
            if (consumed) {
                break;
            }
            // This pass only checks non-interface elements
            if (u.owner == Player.INTERFACE) {
                continue;
            }
            if (!u.selectable) {
                continue;
            }
            if (u.getBounds().contains(new Point(e.getX(), e.getY()))) {
                consumed = true;
                hit = u;
            }
        }
        return hit;
    }

    public void init() {
        Unit next = new Marine(200, 200, Player.PLAYER1);
        elements.add(next);
        Player.PLAYER1.heroUnit = next;
        cameraMode = LOCKED_CAMERA;
        cameraFocus = next;

        elements.add(new BuildPanelButton());
        elements.add(new DeleteButton());
        elements.add(new SelectButton());
        elements.add(new SaveButton());
        elements.add(new LoadButton());
        elements.add(new GenericBuildButton(new Panel(10, 20, Player.PLAYER1)));
        next = new GenericBuildButton(new Drone(20, 20, Player.HOSTILE));
        next.moveTo(new XYPair(600, 600));
        elements.add(next);

        next = new GenericBuildButton(new Node(10, 20, Player.PLAYER1));
        next.moveTo(new XYPair(700, 600));
        elements.add(next);

        elements.add(new Panel(600, 200, Player.HOSTILE));
        elements.add(new Panel(100, 400, Player.HOSTILE));
        elements.add(new Panel(200, 600, Player.HOSTILE));
        elements.add(new Panel(1000, 800, Player.HOSTILE));
        elements.add(new Panel(1400, 400, Player.HOSTILE));

        elements.add(new ItemUnit(new Grenade(), new XYPair(500, 500)));

        // Awesome stuff here
    }

    public void keyStateChanged(ActionEvent e) {

        int index = Integer.parseInt(String.valueOf(e.getActionCommand().substring(2)));
        boolean isdown = (e.getActionCommand().charAt(0) == 'p');

        switch (index) {

            case 0: {//move left command
                playerMovementCommandVector.x += (isdown ? -1.0 : 1.0);
                break;
            }

            case 1: {//move right command
                playerMovementCommandVector.x += (isdown ? 1.0 : -1.0);
                break;
            }

            case 2: {//move up command
                playerMovementCommandVector.y += (isdown ? -1.0 : 1.0);
                break;
            }
            case 3: {//move down command
                playerMovementCommandVector.y += (isdown ? 1.0 : -1.0);
                break;
            }
            case 4: {//Q key - undefined function

            }
            case 5: {//E key - undefined function

            }
            case 6: {// 1 key

            }
            case 7: {// 2 key

            }
            case 8: {// 3 key

            }
            case 9: {// 4 key

            }

        }//end switch

        if (mouse1State) {
            localPlayer.heroUnit.issueCommand(new MoveAndShootAtMouseIntent(playerMovementCommandVector, lastMousePos));

        } else {

            localPlayer.heroUnit.issueCommand(new MoveAlongVectorIntent(playerMovementCommandVector));
        }

    }

    public void leftClick(MouseEvent e) {
        mouse1State = true;
        Unit hit = checkHits(e);

        if (hit == null && cursor == SELECTION) {

            cursor = BASICATTACKTARGET;
            localPlayer.heroUnit.issueCommand(new MoveAndShootAtMouseIntent(playerMovementCommandVector, e));

        } else if (hit != null
                && ((cursor == SELECTION) || (hit.owner == Player.INTERFACE))) {
            if (selection != null) {
                selection.selected = false;
            }
            selection = hit;
            hit.selected = true;
            hit.onClick();
        } else if (cursor == POINTTARGET) {
            selection.targetSelected(e.getX(), e.getY());
        } else if (cursor == BLUEPRINT) {
            selection.targetSelected(e.getX(), e.getY());
        } else if (cursor == UNITTARGET) {
            if (hit != null) {
                selection.targetSelected(hit);
            }
        } // More cursors here
        else {

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Not used: mousePressed used instead
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() == 1) {
            mouse1State = true;
        }
        if (e.getButton() == 2) {
            mouse2State = true;
        }

        e.translatePoint(viewx, viewy);
        lastMousePos = e;

        if (cursor == BASICATTACKTARGET) {
            localPlayer.heroUnit.issueCommand(new MoveAndShootAtMouseIntent(playerMovementCommandVector, e));
        }

        if (cursor == MAPDRAG) {
            shiftCamera(dragReferenceX - e.getXOnScreen(),
                    dragReferenceY - e.getYOnScreen());
            dragReferenceX = e.getXOnScreen();
            dragReferenceY = e.getYOnScreen();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        e.translatePoint(viewx, viewy);
        lastMousePos = e;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        e.translatePoint(viewx, viewy);
        lastMousePos = e;
        if (e.getButton() == (MouseEvent.BUTTON1)) {
            leftClick(e);
        }
        if (e.getButton() == (MouseEvent.BUTTON3)) {
            rightClick(e);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        e.translatePoint(viewx, viewy);
        lastMousePos = e;
        if (e.getButton() == 1) {
            mouse1State = false;
        }
        if (e.getButton() == 2) {
            mouse2State = false;
        }

        if (cursor == MAPDRAG) {
            cursor = SELECTION;
        }

        if (cursor == BASICATTACKTARGET) {
            cursor = SELECTION;
            localPlayer.heroUnit.issueCommand(new MoveAlongVectorIntent(playerMovementCommandVector));
        }

    }

    public BufferedImage render() {
        BufferedImage render = new BufferedImage(this.width, this.height,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = render.createGraphics();

        // Paint the background
        g.setColor(new Color(64, 32, 0));
        g.fillRect(0, 0, width, height);

		// Paint each element
        try {
            for (Unit u : this.elements) {

                g.drawImage(u.draw(), (int) u.position().x, (int) u.position().y,
                        new Color(0, 0, 0, 0), (ImageObserver) Window.main);
            }
        } catch (ConcurrentModificationException e) {
        }

        return render;
    }

    public void rightClick(MouseEvent e) {
        Ability a = new MysticShotGunAbility();
        a.targetPoint = new XYPair(e.getX(), e.getY());
        localPlayer.heroUnit.issueCommand(new CastAbilityIntent(a));

    }

    public synchronized void tick(int delta) {

        for (Unit u : babies) {
            elements.add(u);
        }

        babies.clear();

        for (Unit u : elements) {
            if (u.isDead()) {
                elements.remove(u);
            }
        }
        for (Unit u : elements) {
            u.tick(delta);

        }

        if (cameraMode == LOCKED_CAMERA) {
            XYPair view = new XYPair(viewx + (Window.main.getWidth() / 2), viewy + (Window.main.getHeight() / 2));
            XYPair shift = view.vectorTo(cameraFocus.position());
            if (shift.magnitude() > 1) {
                shiftCamera((int) shift.x, (int) shift.y);
            }
        }

    }

    public synchronized void shiftCamera(int deltax, int deltay) {
        if ((deltax < 0) && (viewx + deltax) < 0) {
            deltax = 0 - viewx;
        }
        if ((deltay < 0) && (viewy + deltay) < 0) {
            deltay = 0 - viewy;
        }

        if ((deltax > 0)
                && (viewx + deltax + Window.main.getContentPane().getWidth() > width)) {
            deltax = width - Window.main.getContentPane().getWidth() - viewx;
        }

        if ((deltay > 0)
                && (viewy + deltay + Window.main.getContentPane().getHeight() > height)) {
            deltay = height - Window.main.getContentPane().getHeight() - viewy;
        }

        viewx += deltax;
        viewy += deltay;

        if (lastMousePos != null) {
            lastMousePos.translatePoint(deltax, deltay);
        }

        for (Unit u : elements) {
            if (u.owner == Player.INTERFACE) {
                u.shift(new XYPair(deltax, deltay));
                u.generateBounds();
            }
        }

    }

}
