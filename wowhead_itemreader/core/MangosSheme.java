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

import wowhead_itemreader.Util;
import wowhead_itemreader.WoWHeadData;

public class MangosSheme extends BasicSheme
{
    public MangosSheme()
    {
        super(new String[]{"entry", "class", "subclass", "name", "displayid", "Quality", "BuyPrice", "SellPrice", "InventoryType", "AllowableClass", "AllowableRace", "ItemLevel", "RequiredLevel", "RequiredSkill", "RequiredSkillRank", "RequiredReputationFaction", "maxcount", "ContainerSlots", "StatsCount", "stat_type1", "stat_value1", "stat_type2", "stat_value2", "stat_type3", "stat_value3", "stat_type4", "stat_value4", "stat_type5", "stat_value5", "stat_type6", "stat_value6", "stat_type7", "stat_value7", "stat_type8", "stat_value8", "stat_type9", "stat_value9", "stat_type10", "stat_value10", "ScalingStatDistribution", "ScalingStatValue", "dmg_min1", "dmg_max1", "dmg_type1", "dmg_min2", "dmg_max2", "dmg_type2", "armor", "holy_res", "fire_res", "nature_res", "frost_res", "shadow_res", "arcane_res", "delay", "RangedModRange", "spellid_1","spelltrigger_1","spellcharges_1","spellcooldown_1","spellcategory_1","spellcategorycooldown_1", "spellid_2","spelltrigger_2","spellcharges_2","spellcooldown_2","spellcategory_2","spellcategorycooldown_2", "spellid_3","spelltrigger_3","spellcharges_3","spellcooldown_3","spellcategory_3","spellcategorycooldown_3","spellid_4","spelltrigger_4","spellcharges_4","spellcooldown_4","spellcategory_4","spellcategorycooldown_4","spellid_5","spelltrigger_5","spellcharges_5","spellcooldown_5","spellcategory_5","spellcategorycooldown_5","bonding", "description", "itemset", "MaxDurability", "socketColor_1", "socketColor_2", "socketColor_3", "socketBonus", "RequiredDisenchantSkill"});
    }

    protected String getValueData(WoWHeadData data)
    {
        String values = 
              data.itemId+","
             +data.itemClass+","
             +data.itemSubClass+","
             +"'"+Util.escapeSQL(data.name)+"' ,"
             +data.itemDisplayId+","
             +data.itemQuality+","
             +data.buyprice+","
             +data.sellPrice+","
             +data.inventoryType+","
             +data.reqClass+","
             +data.reqRace+","
             +data.itemLevel+","
             +data.reqLevel+","
             +data.reqskill+","
             +data.reqskillrank+","
             +data.reqFaction+","
             +data.maxCount+","
             +data.containerSlots+", "
             +data.itemstatscount+", "
             +getSpellStatsQuery(data)+
             +data.scaDist+","
             +data.scaFlag+","
             +data.dmg_min1+","
             +data.dmg_max1+","
             +data.dmg_type1+","
             +data.dmg_min2+","
             +data.dmg_max2+","
             +data.dmg_type2+","
             +data.armor+","
             +data.holyRes+","
             +data.fireRes+","
             +data.naturRes+","
             +data.frostRes+","
             +data.shadowRes+","
             +data.arcRes+","
             +data.attSpeed+","
             +data.range+","
             +getSpellQuery(data)+
             +data.bonding+","
             +"'"+Util.escapeSQL(data.description)+"' ,"
             +data.itemSet+","
             +data.durability+","
             +data.socket_color1+","
             +data.socket_color2+","
             +data.socket_color3+","
             +data.socket_bonus+","
             +data.reqDisentSkill;
        return values;
    }

    @Override
    public String getTableName()
    {
        return "item_template";
    }
    
}
