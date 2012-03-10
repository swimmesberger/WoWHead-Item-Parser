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
package wowDatatypes;

import wowhead_itemreader.WoWHeadData;

public class SpellStats
{
    
    public static final int  
    POWER  =  0, //unused
    HEALTH = 1, //unused
    UNKNOWN = 2, //unused
    AGILITY = 3,
    STRENGTH = 4,
    INTELLECT = 5,
    SPIRIT = 6,
    STAMINA = 7,
    WEAPON_SKILL_RATING = 11,
    DEFENSE_RATING = 12,
    DODGE_RATING = 13,
    PARRY_RATING = 14,
    SHIELD_BLOCK_RATING = 15,
    MELEE_HIT_RATING = 16, //unused
    RANGED_HIT_RATING = 17, //unused
    SPELL_HIT_RATING = 18, //unused item 56489 seems to use it but wowhead have no data about SPELL_HIT_RATING
    MELEE_CRITICAL_STRIKE_RATING = 19, //unused seems to be CRITICAL_STRIKE_RATING(32) - item 33265 seems to use it
    RANGED_CRITICAL_STRIKE_RATING = 20,
    SPELL_CRITICAL_STRIKE_RATING = 21, //unused
    MELEE_HIT_AVOIDANCE_RATING = 22, //unused
    RANGED_HIT_AVOIDANCE_RATING = 23, //unused
    SPELL_HIT_AVOIDANCE_RATING = 24, //unused
    MELEE_CRITICAL_AVOIDANCE_RATING = 25, //unused
    RANGED_CRITICAL_AVOIDANCE_RATING = 26, //unused
    SPELL_CRITICAL_AVOIDANCE_RATING = 27, //unused
    MELEE_HASTE_RATING = 28, //unused
    RANGED_HASTE_RATING = 29, //unused
    SPELL_HASTE_RATING = 30, //unused
    HIT_RATING = 31,
    CRITICAL_STRIKE_RATING = 32,
    HIT_AVOIDANCE_RATING = 33, //unused
    EXPERTISE_RATING = 34, //seems to be MASTERY_RATING(49) today
    RESILIENCE_RATING = 35,
    HASTE_RATING = 36,
    EXPERTISE_RATING_2 = 37,
    ATTACK_POWER = 38,
    RANGED_ATTACK_POWER = 39,
    FERAL_ATTACK_POWER = 40, //unused (item 34967 seems to use it, wowhead knows nothing about this)
    SPELL_HEALING_DONE = 41, //unused
    SPELL_DAMAGE_DONE = 42, //unused
    MANA_REGENERATION = 43,
    ARMOR_PENETRATION_RATING = 44,
    SPELL_POWER = 45,
    HEALTH_REGEN = 46,
    SPELL_PENETRATION = 47,
    BLOCK_VALUE = 48,
    MASTERY_RATING = 49,
    FIRE_RESISTANCE = 51,
    FROST_RESISTANCE = 52,
    HOLY_RESISTANCE = 53,
    SHADOW_RESISTANCE = 54,
    NATURE_RESISTANCE = 55,
    ARCANE_RESISTANCE = 56;
    
    private int statType;
    private double statValue;

    public SpellStats(int statType, double statValue)
    {
        this.statType = statType;
        this.statValue = statValue;
    }
    
    public SpellStats()
    {
        this.statType = 0;
        this.statValue = 0;
    }

    /**
     * @return the statType
     */
    public int getStatType()
    {
        return statType;
    }

    /**
     * @param statType the statType to set
     */
    public void setStatType(int statType)
    {
        this.statType = statType;
    }

    /**
     * @return the statValue
     */
    public double getStatValue()
    {
        return statValue;
    }

    /**
     * @param statValue the statValue to set
     */
    public void setStatValue(double statValue)
    {
        this.statValue = statValue;
    }
    
    public static double[][] getValues(WoWHeadData data)
    {
        double[][] spellStats = {
                                    {
                                        data.stamina, 
                                        data.strength, 
                                        data.agility, 
                                        data.intellect, 
                                        data.spirit, 
                                        data.itemParry, 
                                        data.itemDef, 
                                        data.attackPower, 
                                        data.critStr, 
                                        data.hitRat, 
                                        data.hasteRat, 
                                        data.armorDurch, 
                                        data.spellPower, 
                                        data.block, 
                                        data.blockRat, 
                                        data.dodgeRat, 
                                        data.resiRat, 
                                        data.expRat, 
                                        data.splPen, 
                                        data.manaReg, 
                                        data.mastrtng,
                                        data.healthrgn,
                                        data.rgdatkpwr,
                                        data.rgdcritstrkrtng,
                                        data.fireRes,
                                        data.frostRes,
                                        data.holyRes,
                                        data.shadowRes,
                                        data.naturRes,
                                        data.arcRes
                                    }, 
                                    { 
                                        SpellStats.STAMINA,
                                        SpellStats.STRENGTH,
                                        SpellStats.AGILITY,
                                        SpellStats.INTELLECT,
                                        SpellStats.SPIRIT,
                                        SpellStats.PARRY_RATING,
                                        SpellStats.DEFENSE_RATING,
                                        SpellStats.ATTACK_POWER,
                                        SpellStats.CRITICAL_STRIKE_RATING,
                                        SpellStats.HIT_RATING,
                                        SpellStats.HASTE_RATING,
                                        SpellStats.ARMOR_PENETRATION_RATING,
                                        SpellStats.SPELL_POWER,
                                        SpellStats.BLOCK_VALUE,
                                        SpellStats.SHIELD_BLOCK_RATING,
                                        SpellStats.DODGE_RATING,
                                        SpellStats.RESILIENCE_RATING,
                                        SpellStats.EXPERTISE_RATING_2,
                                        SpellStats.SPELL_PENETRATION,
                                        SpellStats.MANA_REGENERATION, 
                                        SpellStats.MASTERY_RATING,
                                        SpellStats.HEALTH_REGEN,
                                        SpellStats.RANGED_ATTACK_POWER,
                                        SpellStats.RANGED_CRITICAL_STRIKE_RATING,
                                        SpellStats.FIRE_RESISTANCE,
                                        SpellStats.FROST_RESISTANCE,
                                        SpellStats.HOLY_RESISTANCE,
                                        SpellStats.SHADOW_RESISTANCE,
                                        SpellStats.NATURE_RESISTANCE,
                                        SpellStats.ARCANE_RESISTANCE
                                    }
                                };
        return spellStats;
    }
    
    
}
