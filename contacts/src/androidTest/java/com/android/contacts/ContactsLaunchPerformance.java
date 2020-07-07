/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gusi.contacts;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.gusi.contacts.test.LaunchPerformanceBase;

/**
 * Instrumentation class for Address Book launch performance testing.
 */
public class ContactsLaunchPerformance extends LaunchPerformanceBase {

    @Override
    public void onCreate(Bundle arguments) {
        mIntent.setAction(Intent.ACTION_MAIN);
        mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mIntent.setComponent(new ComponentName(getTargetContext(),
                "com.gusi.contacts.activities.PeopleActivity"));

        start();
    }

    /**
     * Calls LaunchApp and finish.
     */
    @Override
    public void onStart() {
        super.onStart();
        LaunchApp();
        finish(Activity.RESULT_OK, mResults);
    }
}