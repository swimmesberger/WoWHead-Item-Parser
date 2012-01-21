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

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SearchThread extends Thread
{
    private int itemId;
    private int fromId;
    private int toId;
    private MainFrame wowhead;
    private int addedIn;
    private String lang;
    
    private ArrayList<WoWHeadData> items = new ArrayList<WoWHeadData>();

    public SearchThread(int itemid, int fromid, int toid, MainFrame wowhead, int addedIn, String lang)
    {
        super();
        this.wowhead = wowhead;
        this.itemId = itemid;
        this.fromId = fromid;
        this.toId = toid;
        this.addedIn = addedIn;
        this.lang = lang;
    }

    @Override
    public void run()
    {
        WoWHeadData head = new WoWHeadData();
        StringBuilder sb = new StringBuilder();
        boolean selected = wowhead.deepCheckBox.isSelected();
        if(wowhead.multiCheckBox.isSelected())
        {
            int count = 0;
            for(int i = fromId; i<=toId; i++)
            {
                try
                {
                    head = new WoWHeadData();
                    head.search(i, lang, true, selected, addedIn);
                    wowhead.progressBar.setValue(count);
                    wowhead.progressBar.paint(wowhead.progressBar.getGraphics());
                    if(head.itemId > 0)
                    {
                        String createSql = head.createSql(wowhead.getCore());
                        getQuerys().add(head);
                        String text = wowhead.queryPane.getText();
                        String seperator = "\n";
                        if(text.isEmpty())
                        {
                            seperator = "";
                        }
                        wowhead.queryPane.setText(text + seperator  + createSql);
                    }
                    count++;
                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(wowhead, ex.getMessage());
                    wowhead.threadFinished();
                    return;
                }
            }
        }
        else
        {
            try
            {
                head.search(itemId, lang, false, selected, addedIn);
            } catch (Exception ex)
            {
                JOptionPane.showMessageDialog(wowhead, ex.getMessage());
                wowhead.threadFinished();
                return;
            }
        }
        if(head.urlexists == true && head.scanned == true)
        {
            String url = "http://static.wowhead.com/images/wow/icons/large/"+head.iconName.toLowerCase()+".jpg";
            wowhead.showPicture(url, wowhead.picture);
            wowhead.nameField.setText(head.name);
            wowhead.idField.setText(String.valueOf(head.itemId));
            if(!(wowhead.multiCheckBox.isSelected()))
            {
                String createSql = head.createSql(wowhead.getCore());
                sb = sb.append(createSql);
                getQuerys().add(head);
                wowhead.queryPane.setText(sb.toString());
            }
            wowhead.editorPane.setText("<body text=\"white\"><div id=\"tt71086\" class=\"wowhead-tooltip tooltip-slider\">"+head.tooltip+"</div></body>");
            wowhead.progressBar.setValue(wowhead.progressBar.getMaximum());
            wowhead.progressBar.paint(wowhead.progressBar.getGraphics());
            wowhead.repaint();
            wowhead.setWoWHeadLink(head.url);
            JOptionPane.showMessageDialog(wowhead,"Succeed !");
            wowhead.threadFinished();
        }
        else
        {
            wowhead.threadFinished();
        }
    }

    /**
     * @return the querys
     */
    public ArrayList<WoWHeadData> getQuerys()
    {
        return items;
    }

}
