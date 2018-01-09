package org.kccc.gateway;

import io.realm.RealmObject;

/**
 * Created by hmk20 on 2018-01-08.
 */

public class AppInfoVO extends RealmObject{
    private int FirstTimeUse;

    public int getFirstTimeUse() {
        return FirstTimeUse;
    }

    public void setFirstTimeUse(int firstTimeUse) {
        FirstTimeUse = firstTimeUse;
    }
}
