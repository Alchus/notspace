package core;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class KeyBindingComponent extends JComponent {

    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

    private static final long serialVersionUID = -2622301793954908553L;

    public KeyBindingComponent() {

        for (int i = 0; i < Game.keys.length; i++) {
            char key = Game.keys[i];
            getInputMap(IFW).put(KeyStroke.getKeyStroke("pressed " + key), "p " + i);
            getActionMap().put("p " + i, new KeyAction("p " + i));
            getInputMap(IFW).put(KeyStroke.getKeyStroke("released " + key), "r " + i);
            getActionMap().put("r " + i, new KeyAction("r " + i));
        }

    }

    private boolean[] states = new boolean[Game.keys.length];

    class KeyAction extends AbstractAction {

        public KeyAction(String cmd) {
            super(cmd);
            putValue(ACTION_COMMAND_KEY, cmd);
        }

        private static final long serialVersionUID = -3161189210396293426L;

        @Override
        public void actionPerformed(ActionEvent arg0) {

            boolean isdown = (arg0.getActionCommand().charAt(0) == 'p');
			//System.out.println(arg0.getActionCommand().charAt(2));
            //System.out.println(keys.get(0));
            int index = Integer.parseInt(String.valueOf(arg0.getActionCommand().substring(2)));

            if (isdown ^ states[index]) {
                Window.main.game.keyStateChanged(arg0);
            }

            states[index] = isdown;
        }

    }

}
