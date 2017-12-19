package org.kccc.gateway;

/**
 * Created by ming on 2017. 12. 13..
 */

public class SettingVO {
    private int icon;
    private String name;
    public int getIcon(){return icon;}
    public String getName(){return name;}

    public SettingVO(int icon,String name){
        this.icon = icon;
        this.name = name;
    }
}
