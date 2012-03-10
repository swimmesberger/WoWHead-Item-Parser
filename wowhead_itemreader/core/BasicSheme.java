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
package wowhead_itemreader.core;

import wowhead_itemreader.Sheme;
import wowhead_itemreader.WoWHeadData;

public abstract class BasicSheme implements Sheme
{
    private String[] colums;

    public BasicSheme(String[] colums)
    {
        this.colums = colums;
    }

    public String getQuery(WoWHeadData data)
    {
        String doColums = doColums();
        return createQuery(data, doColums);
    }
    
    
    public String createQuery(WoWHeadData data, String colums)
    {

        String query = "INSERT INTO `"+getTableName()+"` "
             + "("+colums.toString()+") "
             + "VALUES ("+getValueData(data)+");";
        return query;
    }
    
    protected abstract String getValueData(WoWHeadData data);
    public abstract String getTableName();
    
    
    private String doColums()
    {
        StringBuilder colum = new StringBuilder();
        for(int i = 0; i<colums.length; i++)
        {
            colum.append("`").append(colums[i]);
            if(i != colums.length-1)
            {
                colum.append("`,");
            }
            else
            {
                colum.append("`");
            }
        }
        return colum.toString();
    }
    
    protected String getSpellStatsQuery(WoWHeadData data)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<data.spellStatsAr.length; i++)
        {
            sb.append(data.spellStatsAr[i].getStatType()).append(",");
            sb.append(data.spellStatsAr[i].getStatValue()).append(",");
        }
        return sb.toString();
    }
    
    protected String getSpellQuery(WoWHeadData data)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<data.itemSpells.length; i++)
        {
            sb.append(data.itemSpells[i].getSpellId()).append(",");
            sb.append(data.itemSpells[i].getSpellTrigger()).append(",");
            sb.append(data.itemSpells[i].getSpellCharges()).append(",");
            sb.append(data.itemSpells[i].getSpellCooldown()).append(",");
            sb.append(data.itemSpells[i].getSpellCategory()).append(",");
            sb.append(data.itemSpells[i].getSpellCategoryCooldown()).append(",");
        }
        return sb.toString();
    }
    
    protected int getSocketAmount(WoWHeadData data, int socketColor)
    {
        if(socketColor == 0)return 0;
        int socketCount = data.nsockets;
        if(socketCount == 0)return 0;
        int amount = 0;
        if(data.socket_color1 == socketColor)amount++;
        if(data.socket_color2 == socketColor)amount++;
        if(data.socket_color3 == socketColor)amount++;
        return amount;
    }
}
