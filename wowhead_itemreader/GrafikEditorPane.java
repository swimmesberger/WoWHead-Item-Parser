/*
 * Copyright (C) 2011 Thedeath<www.fseek.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package wowhead_itemreader;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;

public class GrafikEditorPane extends JEditorPane
{

    private ImageIcon icon;

    public GrafikEditorPane(ImageIcon icon)
    {
        super();
        this.icon = icon;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        try
        {
            // set background green - but can draw image here too
            // uncomment the following to draw an image
            // Image img = ...;
            ImageIcon newimg = scale(icon, this.getWidth(), this.getHeight());
            g.drawImage(newimg.getImage(), 0, 0, this);
            g.setColor(Color.WHITE);
            super.paintComponent(g);
        } catch (IOException ex)
        {
            Logger.getLogger(GrafikEditorPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ImageIcon scale(ImageIcon img, int width, int height)
    throws IOException
    {
        BufferedImage bsrc = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        bsrc.getGraphics().drawImage(img.getImage(), 0, 0, img.getImageObserver());
        BufferedImage bdest =
        new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bdest.createGraphics();
        AffineTransform at =
        AffineTransform.getScaleInstance((double) width / bsrc.getWidth(),
        (double) height / bsrc.getHeight());
        g.drawRenderedImage(bsrc, at);
        return new ImageIcon(bdest);
    }
}
