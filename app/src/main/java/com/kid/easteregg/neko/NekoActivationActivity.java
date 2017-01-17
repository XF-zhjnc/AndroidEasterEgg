/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.kid.easteregg.neko;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class NekoActivationActivity extends Activity implements PrefState.PrefsListener {

    private PrefState mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = new PrefState(this);
        mPrefs.setListener(this);
    }

    @Override
    public void onStart() {
        final PackageManager pm = getPackageManager();
        //final ComponentName cn = new ComponentName(this, NekoTile.class);
        final ComponentName cn = new ComponentName(this, NekoService.class);
        if (pm.getComponentEnabledSetting(cn) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            if (NekoLand.DEBUG) {
                Log.v("Neko", "Disabling tile.");
            }
            //设为不可用状态
            pm.setComponentEnabledSetting(cn, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0);
            Toast.makeText(this, "抓猫猫游戏关闭", Toast.LENGTH_SHORT).show();
            mPrefs.setListener(null);
            NekoService.cancelJob(this);
        } else {
            if (NekoLand.DEBUG) {
                Log.v("Neko", "Enabling tile.");
            }
            //设为可用状态
            pm.setComponentEnabledSetting(cn, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 0);
            Toast.makeText(this, "抓猫猫游戏开启", Toast.LENGTH_SHORT).show();
            mPrefs.setFoodState(2);  // 永远有食物
            NekoService.registerJob(this, 30);
        }
        finish();
    }

    @Override
    public void onPrefsChanged() {
        //do nothing
    }
}
