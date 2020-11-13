package com.gusi.androidx.module.service;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobServiceEngine;
import android.os.IBinder;

import androidx.core.app.CompatJobEngineAdapter;

/**
 * @author Ylw
 * @since 2020/11/13 21:57
 */
public abstract class JobIntentServiceProxy extends JobServiceEngine
        implements CompatJobEngineAdapter {

    /**
     * Create a new engine, ready for use.
     *
     * @param service The {@link Service} that is creating this engine and in which it will run.
     */
    public JobIntentServiceProxy(Service service) {
        super(service);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    @Override
    public IBinder compatGetBinder() {
        return null;
    }


}
