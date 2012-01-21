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

public class Spell
{
    public static final Spell noSpell = new Spell();
    public static final int On_Equip = 1,
                            On_Use = 0,
                            Chance_on_hit = 2;
    
    private int spellId;
    private int spellTrigger;
    private int spellCharges;
    private int spellCooldown;
    private int spellCategory;
    private int spellCategoryCooldown;

    public Spell(int spellId, int spellTrigger, int spellCharges, int spellCooldown, int spellCategory, int spellCategoryCooldown)
    {
        this.spellId = spellId;
        this.spellTrigger = spellTrigger;
        this.spellCharges = spellCharges;
        this.spellCategory = spellCategory;
        this.spellCategoryCooldown = spellCategoryCooldown;
    }
    
    public Spell()
    {
        this.spellId = 0;
        this.spellTrigger = 0;
        this.spellCharges = 0;
        this.spellCooldown = -1;
        this.spellCategory = 0;
        this.spellCategoryCooldown = -1;
    }

    /**
     * @return the spellId
     */
    public int getSpellId()
    {
        return spellId;
    }

    /**
     * @param spellId the spellId to set
     */
    public void setSpellId(int spellId)
    {
        this.spellId = spellId;
    }

    /**
     * @return the spellTrigger
     */
    public int getSpellTrigger()
    {
        return spellTrigger;
    }

    /**
     * @param spellTrigger the spellTrigger to set
     */
    public void setSpellTrigger(int spellTrigger)
    {
        this.spellTrigger = spellTrigger;
    }

    /**
     * @return the spellCharges
     */
    public int getSpellCharges()
    {
        return spellCharges;
    }

    /**
     * @param spellCharges the spellCharges to set
     */
    public void setSpellCharges(int spellCharges)
    {
        this.spellCharges = spellCharges;
    }

    /**
     * @return the spellCategory
     */
    public int getSpellCategory()
    {
        return spellCategory;
    }

    /**
     * @param spellCategory the spellCategory to set
     */
    public void setSpellCategory(int spellCategory)
    {
        this.spellCategory = spellCategory;
    }

    /**
     * @return the spellCategoryCooldown
     */
    public int getSpellCategoryCooldown()
    {
        return spellCategoryCooldown;
    }

    /**
     * @param spellCategoryCooldown the spellCategoryCooldown to set
     */
    public void setSpellCategoryCooldown(int spellCategoryCooldown)
    {
        this.spellCategoryCooldown = spellCategoryCooldown;
    }

    /**
     * @return the spellCooldown
     */
    public int getSpellCooldown()
    {
        return spellCooldown;
    }

    /**
     * @param spellCooldown the spellCooldown to set
     */
    public void setSpellCooldown(int spellCooldown)
    {
        this.spellCooldown = spellCooldown;
    }
    
    
}
