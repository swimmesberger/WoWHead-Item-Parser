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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import org.dom4j.DocumentException;
import wowDatatypes.Spell;

public class DeepParser
{
    public DeepParser(String url) throws MalformedURLException, IOException, DocumentException, Exception
    {
        this.url = url;
    }
    
    public DeepParser(URL url) throws MalformedURLException, IOException, DocumentException, Exception
    {
        this.rURL = url;
    }
    
    private String url;
    private URL rURL;
    private Source source = null;
    
    private ArrayList<Spell> itemSpells = new ArrayList<Spell>();
    private String description;
    private int bounding;
    private int reqDisentSkill;
    private String addedInPatch;
    
    
    public void scanTooltip() throws MalformedURLException, IOException, DocumentException, Exception
    {
        source= new Source(url);
        source.fullSequentialParse();
        Element[] spellTriggerElements = getContentOfClass("span", "q2", "class", source);
        if(spellTriggerElements != null)
        {
            for(int i = 0; i<spellTriggerElements.length; i++)
            {
                Element e = spellTriggerElements[i];
                String attributeValue = e.getTextExtractor().toString();
                int index = attributeValue.indexOf(":");
                if(index != -1)
                {
                    String trigger = attributeValue.substring(0, index);
                    Element[] spellElements = getContentOfClass("a", "q2", "class", e);
                    if(spellElements != null)
                    {
                        Spell get = new Spell();
                        get.setSpellTrigger(parseTrigger(trigger));
                        String spellValue = spellElements[0].getAttributeValue("href");
                        int spell = getSpell(spellValue);
                        if(spell != -1)
                        {
                            get.setSpellId(spell);
                        }
                        this.itemSpells.add(get);
                    }
                }
            }
        }
        Element[] contentOfClass1 = getContentOfClass("span", "q", "class", source);
        if(contentOfClass1 != null)
        {
            this.description = contentOfClass1[0].getTextExtractor().toString();
        }
        else
        {
            this.description = "";
        }
        this.setBounding(getItemBound(source.toString()));
    }
    
    public void scanMainPage() throws IOException
    {
        source= new Source(rURL);
        this.setReqDisentSkill(getDisentSkill(source.toString()));
        this.setAddedInPatch(getAddedPatch(source.toString()));
    }
    
    private net.htmlparser.jericho.Element[] getContentOfClass(String tag, String attrName, String attr, Segment el)
    {
        
        List<net.htmlparser.jericho.Element> elementList=el.getAllElements(tag);
        ArrayList<net.htmlparser.jericho.Element> elements = new ArrayList<Element>();
        for (net.htmlparser.jericho.Element element : elementList) 
        {
            String attributeValue = element.getAttributeValue(attr);
            if(attrName == null)
            {
                if(attributeValue != null)
                {
                    elements.add(element);
                }
            }
            else
            {
                if(attributeValue != null && attributeValue.equals(attrName))
                {
                    elements.add(element);
                }
            }
        }
        if(elements.isEmpty())return null;
        Element[] eleArr = new Element[elements.size()];
        for(int i = 0; i<elements.size(); i++)
        {
            eleArr[i] = elements.get(i);
        }
        return eleArr;
    }
    
    private String getAttributeValue(String tag, String attrName, String attr, Segment s)
    {
        Element[] contentOfClass = getContentOfClass(tag, attrName, attr, s);
        if(contentOfClass == null)return null;
        return contentOfClass[0].getAttributeValue(attr);
    }
    
    /**
     * This Method searches for "spell=" entrys and puts the spellId in a array .
     */
    public int getSpell(String sb)
    {
        int index1 = sb.indexOf("spell=", 0)+6;
        String s = sb.substring(index1, sb.length());
        try
        {
            return Integer.parseInt(s);
        }
        catch(Exception e)
        {}
        return -1;
    }
    
    private String[] boundPick = {"Binds when picked up", "Wird beim Aufheben gebunden", "Se liga al recogerlo", "Lié quand ramassé", "Персональный при поднятии"};
    private String[] boundEquip = {"Binds when equipped", "Wird beim Anlegen gebunden", "Se liga al equiparlo", "Lié quand équipé", "Становится персональным при надевании"};
    private String[] boundUse = {"Binds when used", "Wird bei Benutzung gebunden", "Se liga al usarlo", "Lié quand utilisé", "Персональный при использовании"};
    private String[] boundQuest = {"Quest Item", "Questgegenstand", "Objeto de misión", "Objet de quête", "Предмет, необходимый для задания"};
    /**
     * Didn't found a better way yet...
     * Only ArcEmu ? 
     */
    private int getBound(String s)
    {
        String bPick;
        String bEquip;
        String bUse;
        String questIt;
        int bound = -1;
        int count = 0;
        while(bound == -1)
        {
            if(count > boundPick.length-1)break;
            bPick = boundPick[count];
            bEquip = boundEquip[count];
            bUse = boundUse[count];
            questIt = boundQuest[count];
            if(s.equals(bPick))
            {
                return 1;
            }
            if(s.equals(bEquip))
            {
                return 2;
            }
            if(s.equals(bUse))
            {
                return 3;
            }
            if(s.equals(questIt))
            {
                return 4;
            }
            count++;
        }
        return 0;
    }
    
    public int getItemBound(String sb)
    {
        int indexBind = sb.indexOf("<!--bo-->");
        if(indexBind != -1)
        {
            int indexBindEnd = sb.indexOf("<", indexBind+9);
            return getBound(sb.substring(indexBind+9, indexBindEnd));
        }
        return 0;
    }
    
    public int parseTrigger(String trigger)
    {
        String toLowerCase = trigger.toLowerCase();
        if(toLowerCase.equals("equip"))
        {
            return Spell.On_Equip;
        }
        else if(toLowerCase.equals("use"))
        {
            return Spell.On_Use;
        }
        else
        {
            return Spell.Chance_on_hit;
        }
    }
    
     /**
     * This method reads the disendchant skill needed for the item.
     * This method slows the programm so if you want to make the program fast don't call it
     */
    private int getDisentSkill(String code)
    {
        String search = search(code, "[tooltip=tooltip_reqenchanting]", "[/tooltip]");
        if(search == null)
        {
            return -1;
        }
        return Integer.parseInt(search);
    }
    
    private String[] searchPatchLang = {"Added in patch ", "Hinzugefügt in Patch ", "Añadido en el parche ", "Ajouté dans la mise à jour ", "Добавлено в обновлении "};
    private String getAddedPatch(String code)
    {
        String search = null;
        int count = 0;
        while(search == null)
        {
            if(count > searchPatchLang.length-1)break;
            search = search(code, searchPatchLang[count], "[/li]");
            count++;
        }
        return search;
    }
    
    private String search(String code, String begin, String end)
    {
        String searchString = begin;
        int indexSkillBegin = code.indexOf(searchString);
        if(indexSkillBegin != -1)
        {
            int indexSkillEnd = code.indexOf(end, indexSkillBegin);
            String s = code.substring(indexSkillBegin+searchString.length(), indexSkillEnd);
            return s;
        }
        return null;
    }

    /**
     * @return the itemSpells
     */
    public ArrayList<Spell> getItemSpells()
    {
        return itemSpells;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return the bounding
     */
    public int getBounding()
    {
        return bounding;
    }

    /**
     * @param bounding the bounding to set
     */
    public void setBounding(int bounding)
    {
        this.bounding = bounding;
    }

    /**
     * @return the reqDisentSkill
     */
    public int getReqDisentSkill()
    {
        return reqDisentSkill;
    }

    /**
     * @param reqDisentSkill the reqDisentSkill to set
     */
    public void setReqDisentSkill(int reqDisentSkill)
    {
        this.reqDisentSkill = reqDisentSkill;
    }

    /**
     * @return the addedInPatch
     */
    public String getAddedInPatch()
    {
        return addedInPatch;
    }

    /**
     * @param addedInPatch the addedInPatch to set
     */
    public void setAddedInPatch(String addedInPatch)
    {
        this.addedInPatch = addedInPatch;
    }
}
