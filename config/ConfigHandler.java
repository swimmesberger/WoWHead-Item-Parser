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
package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import wowhead_itemreader.MainFrame;

public class ConfigHandler
{
    private String wowheadUrl;
    private File configFile;
    private Properties globalConfig = new Properties();
    
    public ConfigHandler()
    {
        this.configFile = new File(MainFrame.mainDir + File.separator + "main.conf");
        intConfig();
    }
    
    private void intConfig()
    {
        if(!configFile.exists())
        {
            setValImpl("wowHeadUrl", "www");
            saveConfig();
        }
    }
    
    public void saveConfig()
    {
        OutputStream o = null;
        try
        {
            o = new FileOutputStream(configFile);
            globalConfig.store(o, "http://www.fseek.org/\n~Thedeath\nGlobal Configuration File\n\nWoWHead url (eg. old = http://old.wowhead.com/, www = http://www.wowhead.com/) (be careful if you change this to a site with a other language some values cant be read anymore !)\n");
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(o != null)
            {
                try
                {
                    o.close();
                } catch (IOException ex){}
            }
        }
    }
    
    public boolean loadConfig() throws Exception
    {
        InputStream s = null;
        this.globalConfig = new Properties();
        try
        {
            s = new FileInputStream(this.configFile);
            this.globalConfig.load(s);
            this.wowheadUrl = this.globalConfig.getProperty("wowHeadUrl");
            return true;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(s != null)
            {
                try
                {
                    s.close();
                } catch (IOException ex){}
            }
        }
        return false;
    }
    
    private void setValImpl(String key, String val)
    {
        if(this.globalConfig.contains(val))
        {
            this.globalConfig.setProperty(key, val);
        }
        else
        {
            this.globalConfig.put(key, val);
        }
    }

    /**
     * @return the wowheadUrl
     */
    public String getWowheadUrl()
    {
        return wowheadUrl;
    }
}
