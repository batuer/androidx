package androidx.core.app;

import android.os.AsyncTask;
import android.util.Log;

import com.gusi.androidx.module.ReflectUtils1;

import java.lang.reflect.Field;

/**
 * @author Ylw
 * @since 2020/11/13 20:23
 */
public abstract class JobIntentServiceAdapter extends JobIntentService {

//    @Override
//    GenericWorkItem dequeueWork() {
//        try {
//            return super.dequeueWork();
//        } catch (SecurityException e) {
//            Log.e(TAG, "dequeueWork: " + e.toString());
//        }
//        return null;
//    }

    @Override
    void ensureProcessorRunningLocked(boolean reportStarted) {
        super.ensureProcessorRunningLocked(reportStarted);
        Field mCurProcessor = ReflectUtils1.getField(getClass(), "mCurProcessor");
        Log.w(TAG, "ensureProcessorRunningLocked: " + mCurProcessor);
        if (mCurProcessor != null) {
            try {
                Object o = mCurProcessor.get(this);
                if (o instanceof AsyncTask) {
                    AsyncTask asyncTask = (AsyncTask) o;
                }

                Log.w(TAG, "ensureProcessorRunningLocked: " + o + " : " + (o instanceof AsyncTask));
            } catch (IllegalAccessException e) {
                Log.e(TAG, "ensureProcessorRunningLocked: " + e.toString());
            }
        }
    }
}
