package units;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import core.Player;
import core.Unit;
import core.Window;
import core.XYPair;

public class SaveButton extends Unit {

    /**
     *
     */
    private static final long serialVersionUID = 4690747785773571609L;

    public SaveButton() {
        owner = Player.INTERFACE;
        moveTo(new XYPair(50, 450));

        width = 40;
        height = 40;
        mobile = false;
        canCollide = false;
    }

    @Override
    public void destroy(int cause) {
        // TODO Auto-generated method stub

    }

    public void onClick() {
        System.out.println("Saving");
        Window.main.game.running = false;

        if (Window.jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            try {
                // create an output stream for serializing the object
                FileOutputStream outputFileStream = new FileOutputStream(
                        Window.jfc.getSelectedFile());

                ObjectOutputStream outputStream = new ObjectOutputStream(
                        outputFileStream);
				// writeObject method of ObjectOutputStream will write/serialize
                // the object to
                // the path provided by FileOutputStream
                outputStream.writeObject(Window.main.game);
                outputStream.close();
                outputFileStream.close();

                System.out
                        .println("Game successfully serialized and stored at "
                                + Window.jfc.getSelectedFile()
                                .getAbsolutePath());

            } catch (IOException e) {
                // Print any exception
                e.printStackTrace();
            }
        }
        System.out.println("unpausing");
        game.running = true;
    }

    public BufferedImage draw() {

        img = (BufferedImage) super.draw();

        Graphics2D g2 = img.createGraphics();

        g2.setColor(controllerColor());
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(0, 0, height - 1, width - 1);
        // g2.fillOval(10, 10, 15, 15);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString("Save", 3, 25);

        return img;
    }

}
