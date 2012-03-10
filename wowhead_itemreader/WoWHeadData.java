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
import wowhead_itemreader.core.ArcEmuSheme;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.json.JSONException;
import org.json.JSONObject;
import wowDatatypes.Spell;
import wowDatatypes.SpellStats;
import wowhead_itemreader.core.MangosSheme;
import wowhead_itemreader.core.SkyfireSheme;

public class WoWHeadData
{
    public static String[] userdFields = new String[]{};
    
    // patch
    public static final int NO_CATACLYSM = 4, PATCH_3_3_5 = 3, PATCH_4_1_0 = 2, PATCH_4_2_0 = 1, NONE = 0;
  
    // Error Definition
    public boolean urlexists;
    public boolean scanned;

    // Error Items Arr
    public static int[] errArr;
    public static int count;

    // Html Tooltip
    public String tooltip;
    
    // patch
    public String patch;

    // Link Definition
    public String url;

    // Picture Definition
    public String iconName;

    // data not need by emu
    public double gearScore;
    public double dps;
    public int slot;
    public int heroic;
    public int nsockets;
    
    // Data Definition
    public int itemId;
    public String name = "";
    public String description = "";
    public int itemLevel;
    public int itemQuality;
    public int itemClass;
    public int itemSubClass;
    public int itemDisplayId;
    public int itemInventoryId;
    public double itemParry;
    public double itemDef;
    public double dmg_min1;
    public double dmg_max1;
    public int dmg_type1;
    public double dmg_min2;
    public double dmg_max2;
    public int dmg_type2;
    public int durability;
    public double attSpeed;
    public int reqLevel;
    public int inventoryType;
    public double attackPower;
    public double critStr;
    public double hitRat;
    public int sellPrice;
    public int buyprice;
    public double hasteRat;
    public int socket_color1;
    public int socket_color2;
    public int socket_color3;
    public int socket_bonus;
    public int reqDisentSkill;
    public int maxCount;
    public double armorDurch;
    public Spell[] itemSpells;
    public int reqClass;
    public int reqRace;
    public int spellPower;
    public int armor;
    public int stamina;
    public int strength;
    public int agility;
    public int intellect;
    public int spirit;
    public int containerSlots;
    public int frostRes;
    public int holyRes;
    public int naturRes;
    public int shadowRes;
    public int fireRes;
    public int arcRes;
    public int scaDist;
    public int scaFlag;
    public int block;
    public int blockRat;
    public int dodgeRat;
    public int itemSet;
    public int manaReg;
    public int resiRat;
    public int expRat;
    public int splPen;
    public double rangeDmgMin;
    public double rangeDmgMax;
    public int range;
    public double armorbonus;
    public SpellStats[] spellStatsAr;
    public int reqskill;
    public int reqskillrank;
    public int reqArenaRat;
    public int cooldown;
    public int itemstatscount;
    public int bonding;
    public int healthrgn;
    public int rgdatkpwr;
    public int rgdcritstrkrtng;
    
    // unused
    public int mastrtng;
    public double mledmgmax;
    public double mledmgmin;
    public double mledps;
    public double mlespeed;
    public int reqFaction;

    public WoWHeadData()
    {
        reqClass = -1;
        reqRace = -1;
        reqLevel = -1;
        urlexists = true;
        scanned = true;
        itemSpells = new Spell[5];
        errArr = new int[50000];
    }

     /** Creates the site scanner
     * @param itemid Means the Item Id
     * @param lang Means the language of wowhead.
     * @param multi If multi is selected.
     * @return The scanner with the site information.
     **/
    public URL getURL(int itemid, String lang, boolean multi, boolean xml) throws Exception
    {
        if(lang.equals("en"))
        {
            lang = "www";
        }
        url = "http://"+lang+".wowhead.com/item="+itemid;
        URL x = null;
        try
        {
            if(xml)
                x = new URL(url + "&xml");
            else
                x = new URL(url);
        }catch(MalformedURLException e)
        {
            if(multi == false)
            {
                urlexists = false;
                throw new Exception("UTR dont exists !");
            }
            // Print error
            System.out.println(e);
        }
        return x;
    }
    
     /** Main Search Method
     * @param itemid Means the Item Id
     * @param lang Means the language of wowhead.
     * @param multi If multi is selected.
     **/
    // For Example
    // lang = de -> http://de.wowhead.com/.
    // lang = "en" -> Means the normal website http://www.wowhead.com/
    public void search(int itemid, String lang, boolean multi, boolean deep, int addedIn) throws Exception
    {
        try
        {
            String toString = getURL(itemid, lang, multi, false).toString();
            if(deep)
            {
                URL x = new URL(toString);
                boolean parseMainHTML = parseMainHTML(x, addedIn);
                if(parseMainHTML == false)
                {
                    this.itemId = 0;
                    throw new Exception("Item doesnt match your patch input!");
                }
            }
            org.dom4j.Document document = parse(toString+"&xml");
            Element rootElement = document.getRootElement();
            parseXML(rootElement);
            parseHTML(this.tooltip);
            doSpellStats();
            endUpCheckValues();
        } 
        catch (Exception ex)
        {
            // Print Error
            ex.printStackTrace();
            if(multi == false)
            {
                throw new Exception(ex.getMessage());
            }
            else
            {
                WoWHeadData.errArr[WoWHeadData.count] = itemid;
                WoWHeadData.count++;
            }
        }
    }
    
    private void doSpellStats()
    {
        double[][] spellStats = SpellStats.getValues(this);
        spellStats = delTrashSpellStats(spellStats);
        this.spellStatsAr = new SpellStats[10];
        for(int i = 0; i<spellStatsAr.length; i++)
        {
            if(i > spellStats[0].length-1)
            {
                this.spellStatsAr[i] = new SpellStats();
            }
            else
            {
                this.spellStatsAr[i] = new SpellStats((int)(spellStats[1][i]), spellStats[0][i]);
            }
        }
    }
    
    private void parseXML(Element n) throws Exception
    {
        addToEngine(n);
        for(int i = 0; i<n.nodeCount(); i++)
        {
            Node node = n.node(i);
            if(node instanceof Element)
            {
                parseXML((Element)node);
            }
        }
    }
    
    private void addToEngine(Element n) throws Exception
    {
        String nodeName = n.getName();
        try
        {
            if(nodeName.equals("item"))
            {
                this.itemId = getAttributeValue(n, "id");
            }
            else if(nodeName.equals("name"))
            {
                this.name = n.getData().toString();
            }
            else if(nodeName.equals("level"))
            {
                this.itemLevel = Integer.parseInt(n.getData().toString());
            }
            else if(nodeName.equals("gearScore"))
            {
                this.gearScore = Double.parseDouble(n.getData().toString());
            }
            else if(nodeName.equals("quality"))
            {
                this.itemQuality = getAttributeValue(n, "id");
            }
            else if(nodeName.equals("class"))
            {
                this.itemClass = getAttributeValue(n, "id");
            }
            else if(nodeName.equals("subclass"))
            {
                this.itemSubClass = getAttributeValue(n, "id");
            }
            else if(nodeName.equals("icon"))
            {
                this.itemDisplayId = getAttributeValue(n, "displayId");
                this.iconName = n.getData().toString();
            }
            else if(nodeName.equals("inventorySlot"))
            {
                this.itemInventoryId = getAttributeValue(n, "id");
            }
            else if(nodeName.equals("htmlTooltip"))
            {
                this.tooltip = n.getData().toString();
            }
            else if(nodeName.startsWith("json"))
            {
                parseJson(n.getData().toString());
            }
            else if(nodeName.startsWith("error"))
            {
                this.urlexists = false;
                throw new Exception(n.getData().toString());
            }
        }catch(NumberFormatException ex){}
    }
    
    private int getAttributeValue(Element ele, String attributeName)
    {
        Attribute attribute = ele.attribute(attributeName);
        int value = Integer.parseInt(attribute.getValue());
        return value;
    }
    
    private void parseHTML(String html)
    {
        try
        {
            DeepParser pars = new DeepParser(html);
            pars.scanTooltip();
            ArrayList<Spell> itemSpells1 = pars.getItemSpells();
            int size = itemSpells1.size();
            if(size < 5)
            {
                size = 5;
            }
            this.itemSpells = new Spell[size];
            for(int i = 0; i<itemSpells.length; i++)
            {
                try
                {
                    Spell get = itemSpells1.get(i);
                    if(this.cooldown > 0 && get.getSpellTrigger() == 0)
                    {
                        get.setSpellCooldown(this.cooldown);
                    }
                    this.itemSpells[i] = get;
                }catch(Exception ex){this.itemSpells[i] = Spell.noSpell;}
            }
            this.description = pars.getDescription();
            this.bonding = pars.getBounding();
        } catch (MalformedURLException ex)
        {
            Logger.getLogger(WoWHeadData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(WoWHeadData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex)
        {
            Logger.getLogger(WoWHeadData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex)
        {
            Logger.getLogger(WoWHeadData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean parseMainHTML(URL url, int addedIn)
    {
        try
        {
            DeepParser parser = new DeepParser(url);
            parser.scanMainPage();
            this.patch = parser.getAddedInPatch();
            if(this.patch != null)
            {
                String[] split = this.patch.split("\\.");
                try
                {
                    int firstNumber = Integer.parseInt(split[0]);
                    int secondNumber = Integer.parseInt(split[1]);
                    int thirdNumber = Integer.parseInt(split[2]);
                    if(addedIn == WoWHeadData.NO_CATACLYSM)
                    {
                        if(firstNumber > 3)
                        {
                            return false;
                        }
                    }
                    else if(addedIn == WoWHeadData.PATCH_3_3_5)
                    {
                        if(firstNumber != 3 || secondNumber != 3 || thirdNumber != 5)
                        {
                            return false;
                        }
                    }
                    else if(addedIn == WoWHeadData.PATCH_4_1_0)
                    {
                        if(firstNumber != 4 || secondNumber != 1 || thirdNumber != 0)
                        {
                            return false;
                        }
                    }
                    else if(addedIn == WoWHeadData.PATCH_4_2_0)
                    {
                        if(firstNumber != 4 || secondNumber != 2 || thirdNumber != 0)
                        {
                            return false;
                        }
                    }
                }catch(Exception ex){}
            }
            this.reqDisentSkill = parser.getReqDisentSkill();
        } catch (MalformedURLException ex)
        {
            Logger.getLogger(WoWHeadData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(WoWHeadData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex)
        {
            Logger.getLogger(WoWHeadData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex)
        {
            Logger.getLogger(WoWHeadData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    private void endUpCheckValues()
    {
        if(this.itemSubClass < 0)
        {
            this.itemSubClass = 0;
        }
    }
    
    private void parseJson(String json)
    {
        try
        {
            json = "{" + json + "}";
            JSONObject object = new JSONObject(json);
            setJsonValue("dps", getJsonValue(object,"dps"), PARSEDOUBLE);
            setJsonValue("mledps", getJsonValue(object,"mledps"), PARSEDOUBLE);
            setJsonValue("reqLevel", (getJsonValue(object,"reqlevel")),  PARSEINT);
            setJsonValue("slot",(getJsonValue(object,"slot")), PARSEINT);
            setJsonValue("inventoryType", (getJsonValue(object,"slotbak")),  PARSEINT);
            setJsonValue("attSpeed", (getJsonValue(object,"speed")),  PARSEDOUBLE);
            setJsonValue("mlespeed", (getJsonValue(object,"mlespeed")),  PARSEDOUBLE);
            setJsonValue("sellPrice", (getJsonValue(object,"sellprice")), PARSEINT);
            setJsonValue("durability", (getJsonValue(object,"dura")),  PARSEINT);
            setJsonValue("socket_color1", (getJsonValue(object,"socket1")),  PARSEINT);
            setJsonValue("socket_color2", (getJsonValue(object,"socket2")),  PARSEINT);
            setJsonValue("socket_color3", (getJsonValue(object,"socket3")),  PARSEINT);
            setJsonValue("socket_bonus", (getJsonValue(object,"socketbonus")),  PARSEINT);
            setJsonValue("nsockets", (getJsonValue(object,"nsockets")),  PARSEINT);
            setJsonValue("maxCount", (getJsonValue(object,"maxcount")),  PARSEINT);
            setJsonValue("reqClass", (getJsonValue(object,"reqclass")),  PARSEINT);
            setJsonValue("reqRace", (getJsonValue(object,"reqrace")),  PARSEINT);
            setJsonValue("armor", (getJsonValue(object,"armor")), PARSEINT);
            setJsonValue("buyprice", (getJsonValue(object,"buyprice")), PARSEINT);
            setJsonValue("containerSlots", (getJsonValue(object,"nslots")), PARSEINT);
            setJsonValue("arcRes", (getJsonValue(object,"arcres")), PARSEINT);
            setJsonValue("fireRes", (getJsonValue(object,"firres")),  PARSEINT);
            setJsonValue("frostRes", (getJsonValue(object,"frores")),  PARSEINT);
            setJsonValue("naturRes", (getJsonValue(object,"natres")),  PARSEINT);
            setJsonValue("shadowRes", (getJsonValue(object,"shares")),  PARSEINT);
            setJsonValue("holyRes", (getJsonValue(object,"holres")),  PARSEINT);
            setJsonValue("scaDist", (getJsonValue(object,"scadist")),  PARSEINT);
            setJsonValue("scaFlag", (getJsonValue(object,"scaflags")),  PARSEINT);
            setJsonValue("itemSet", (getJsonValue(object,"itemset")),  PARSEINT);
            setJsonValue("rangeDmgMin", (getJsonValue(object,"rgddmgmin")),  PARSEDOUBLE);
            setJsonValue("rangeDmgMax", (getJsonValue(object,"rgddmgmax")),  PARSEDOUBLE);
            setJsonValue("armorbonus", (getJsonValue(object,"armorbonus")),  PARSEINT);
            setJsonValue("reqskill", (getJsonValue(object,"reqskill")),  PARSEINT);
            setJsonValue("reqskillrank", (getJsonValue(object,"reqskillrank")),  PARSEINT);
            setJsonValue("reqArenaRat", (getJsonValue(object,"reqarenartng")),  PARSEINT);
            setJsonValue("reqFaction", (getJsonValue(object,"reqfaction")),  PARSEINT);
            setJsonValue("cooldown", (getJsonValue(object,"cooldown")),  PARSEINT);
            setJsonValue("dmg_min1", (getJsonValue(object,"dmgmin1")),  PARSEDOUBLE);
            setJsonValue("dmg_max1", (getJsonValue(object,"dmgmax1")),  PARSEDOUBLE);
            setJsonValue("dmg_type1", (getJsonValue(object,"dmgtype1")),  PARSEINT);
            setJsonValue("dmg_min2", (getJsonValue(object,"dmgmin2")),  PARSEDOUBLE);
            setJsonValue("dmg_max2", (getJsonValue(object,"dmgmax2")),  PARSEDOUBLE);
            setJsonValue("dmg_type2", (getJsonValue(object,"dmgtype2")),  PARSEINT);
            setJsonValue("mledmgmin", (getJsonValue(object,"mledmgmin")),  PARSEDOUBLE);
            setJsonValue("mledmgmax", (getJsonValue(object,"mledmgmax")), PARSEDOUBLE);
            setJsonValue("stamina", (getJsonValue(object,"sta")),  PARSEINT);
            setJsonValue("strength", (getJsonValue(object,"str")), PARSEINT);
            setJsonValue("agility", (getJsonValue(object,"agi")),  PARSEINT);
            setJsonValue("intellect", (getJsonValue(object,"int")),  PARSEINT);
            setJsonValue("spirit", (getJsonValue(object,"spi")),  PARSEINT);
            setJsonValue("itemParry", (getJsonValue(object,"parryrtng")),  PARSEDOUBLE);
            setJsonValue("itemDef", (getJsonValue(object,"defrtng")),  PARSEDOUBLE);
            setJsonValue("attackPower", (getJsonValue(object,"atkpwr")),  PARSEDOUBLE);
            setJsonValue("hitRat", (getJsonValue(object,"hitrtng")),  PARSEDOUBLE);
            setJsonValue("critStr", (getJsonValue(object,"critstrkrtng")),  PARSEDOUBLE);
            setJsonValue("armorDurch", (getJsonValue(object,"armorpenrtng")),  PARSEDOUBLE);
            setJsonValue("hasteRat", (getJsonValue(object,"hastertng")),  PARSEDOUBLE);
            setJsonValue("spellPower", (getJsonValue(object,"splpwr")),  PARSEINT);
            setJsonValue("block", (getJsonValue(object,"block")),  PARSEINT);
            setJsonValue("blockRat", (getJsonValue(object,"blockrtng")),  PARSEINT);
            setJsonValue("dodgeRat", (getJsonValue(object,"dodgertng")), PARSEINT);
            setJsonValue("resiRat", (getJsonValue(object,"resirtng")), PARSEINT);
            setJsonValue("expRat", (getJsonValue(object,"exprtng")),  PARSEINT);
            setJsonValue("splPen", (getJsonValue(object,"splpen")),  PARSEINT);
            setJsonValue("manaReg", (getJsonValue(object,"manargn")),  PARSEINT);
            setJsonValue("heroic", (getJsonValue(object,"heroic")), PARSEINT);
            setJsonValue("mastrtng", (getJsonValue(object,"mastrtng")), PARSEINT);
            setJsonValue("healthrgn", (getJsonValue(object,"healthrgn")), PARSEINT);
            setJsonValue("rgdatkpwr", (getJsonValue(object,"rgdatkpwr")), PARSEINT);
            setJsonValue("rgdcritstrkrtng", (getJsonValue(object,"rgdcritstrkrtng")), PARSEINT);
            
            
            this.attSpeed = this.attSpeed*1000D;
            this.mlespeed = this.mlespeed*1000D;
        } catch (JSONException ex)
        {
            Logger.getLogger(WoWHeadData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getJsonValue(JSONObject object, String key)
    {
        try
        {
            return object.get(key).toString();
        } catch (JSONException ex)
        {}
        return null;
    }
    
    public static int PARSEINT = 1;
    public static int PARSEDOUBLE = 2;
    
    private void setJsonValue(String fieldName, Object value, int type)
    {
        try
        {
            Class<? extends WoWHeadData> aClass = this.getClass();
            Field field = aClass.getDeclaredField(fieldName);
            if(value != null)
            {
                try
                {
                    switch(type)
                    {
                        case 1:
                            field.setInt(this, Integer.parseInt(value.toString()));
                            break;
                        case 2:
                            field.setDouble(this, Double.parseDouble(value.toString()));
                            break;
                        default:
                            field.set(this, value.toString());
                            break;
                    }
                }catch(Exception ex){}
            }
        } catch (NoSuchFieldException ex)
        {
            Logger.getLogger(WoWHeadData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex)
        {
            Logger.getLogger(WoWHeadData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This Method deletes all variables with -1 or 0.
     * The Array must be at least 10 counts long
     */
    private double[][] delTrashSpellStats(double[][] spellStats)
    {
        int countTemp = 0;
        double[][] spellStats_new = new double[2][15];
        for(int i = 0; i<spellStats[0].length; i++)
        {
            if(spellStats[0][i] > 0)
            {
                spellStats_new[0][countTemp] = spellStats[0][i];
                spellStats_new[1][countTemp] = spellStats[1][i];
                countTemp++;
            }
        }
        itemstatscount = countTemp;
        spellStats = new double[2][10];
        for(int i = 0; i<spellStats.length; i++)
        {
            System.arraycopy(spellStats_new[i], 0, spellStats[i], 0, spellStats[0].length);
        }
        return spellStats;
    }

     protected org.dom4j.Document parse(String url) throws Exception 
     {
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(url);
        return document;
    }
    
     // <editor-fold defaultstate="collapsed" desc="Tooltip parsing">    

    

    
    // </editor-fold>                        



    /**
     * Only ArcEmu implemented yet..
     * @param i 0 -> ArcEmu 1 -> Mangos 2 -> Trinity 3 -> Oregon Core
     */
    public String createSql(int i)
    {
        Sheme sheme = null;
        switch(i)
        {
            case 0:
                sheme = new ArcEmuSheme();
                break;
            case 1:
            case 2:
                sheme = new MangosSheme();
                break;
            case 3:
                sheme = new SkyfireSheme();
                break;
        }
        return sheme.getQuery(this);
    }
}
