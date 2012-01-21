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

public class SpellStats
{
    
    public static final int  
    POWER  =  0,
    HEALTH = 1,
    UNKNOWN = 2,
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
    MELEE_HIT_RATING = 16,
    RANGED_HIT_RATING = 17,
    SPELL_HIT_RATING = 18,
    MELEE_CRITICAL_STRIKE_RATING = 19,
    RANGED_CRITICAL_STRIKE_RATING = 20,
    SPELL_CRITICAL_STRIKE_RATING = 21,
    MELEE_HIT_AVOIDANCE_RATING = 22,
    RANGED_HIT_AVOIDANCE_RATING = 23,
    SPELL_HIT_AVOIDANCE_RATING = 24,
    MELEE_CRITICAL_AVOIDANCE_RATING = 25,
    RANGED_CRITICAL_AVOIDANCE_RATING = 26,
    SPELL_CRITICAL_AVOIDANCE_RATING = 27,
    MELEE_HASTE_RATING = 28,
    RANGED_HASTE_RATING = 29,
    SPELL_HASTE_RATING = 30,
    HIT_RATING = 31,
    CRITICAL_STRIKE_RATING = 32,
    HIT_AVOIDANCE_RATING = 33,
    EXPERTISE_RATING = 34,
    RESILIENCE_RATING = 35,
    HASTE_RATING = 36,
    EXPERTISE_RATING_2 = 37,
    ATTACK_POWER = 38,
    RANGED_ATTACK_POWER = 39,
    FERAL_ATTACK_POWER = 40,
    SPELL_HEALING_DONE = 41,
    SPELL_DAMAGE_DONE = 42,
    MANA_REGENERATION = 43,
    ARMOR_PENETRATION_RATING = 44,
    SPELL_POWER = 45,
    HEALTH_REGEN = 46,
    SPELL_PENETRATION = 47,
    BLOCK_VALUE = 48;

    
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
    
    
}
