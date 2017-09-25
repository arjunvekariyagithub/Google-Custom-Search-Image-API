package com.smule.test.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.smule.test.di.ApplicationContext;
import com.smule.test.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {
    private static final String TAG = "AppPreferencesHelper";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    /**
     * save {@link Integer} value
     *
     * @param keyString key to save value for
     * @param key       updated value
     */
    public void saveState(String keyString, int key) {
        try {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.remove(keyString);
            editor.putInt(keyString, key);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (OutOfMemoryError oome) {
            Log.e(TAG, "saveState" + oome.toString());
        }
    }

    /**
     * save {@link String} value
     *
     * @param keyString key to save value for
     * @param key       updated value
     */
    public void saveState(String keyString, String key) {
        try {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.remove(keyString);
            editor.putString(keyString, key);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (OutOfMemoryError oome) {
            Log.e(TAG, "saveState" + oome.toString());
        }
    }

    /**
     * save {@link Long} value
     *
     * @param keyString key to save value for
     * @param key       updated value
     */
    public void saveState(String keyString, long key) {
        try {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.remove(keyString);
            editor.putLong(keyString, key);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (OutOfMemoryError oome) {
            Log.e(TAG, "saveState" + oome.toString());
        }

    }

    /**
     * save {@link Boolean} value
     *
     * @param keyString key to save value for
     * @param key       updated value
     */
    public void saveState(String keyString, boolean key) {
        try {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.remove(keyString);
            editor.putBoolean(keyString, key);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } catch (OutOfMemoryError oome) {
            Log.e(TAG, "saveState" + oome.toString());
        }

    }

    /**
     * load {@link String} value for given key.
     *
     * @param keyString key to load value for
     * @return value associated with given key
     */
    public String loadStringKey(String keyString) {
        String key = "";
        try {
            key = mPrefs.getString(keyString, "");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return key;
    }

    /**
     * load {@link Integer} value.
     *
     * @param keyString   key to load value for
     * @param returnValue default return value if key does not found
     * @return value associated with given key
     */
    public int loadIntKey(String keyString, int returnValue) {
        int key = 0;
        try {
            key = mPrefs.getInt(keyString, returnValue);
        } catch (Exception e) {
            key = returnValue;
            Log.e(TAG, e.toString());
        }
        return key;
    }

    /**
     * load {@link Long} value.
     *
     * @param keyString   key to load value for
     * @param returnValue default return value if key does not found
     * @return value associated with given key
     */
    public long loadLongKey(String keyString, long returnValue) {
        try {
            returnValue = mPrefs.getLong(keyString, returnValue);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return returnValue;
    }

    /**
     * load {@link Boolean} value.
     *
     * @param keyString   key to load value for
     * @param returnValue default return value if key does not found
     * @return value associated with given key
     */
    public boolean loadBooleanKey(String keyString, boolean returnValue) {
        try {
            returnValue = mPrefs.getBoolean(keyString, returnValue);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return returnValue;
    }

    /**
     * load {@link String} value.
     *
     * @param keyString   key to load value for
     * @param returnValue default return value if key does not found
     * @return value associated with given key
     */
    public String loadSettingsStringKey(String keyString, String returnValue) {
        try {
            returnValue = mPrefs.getString(keyString, returnValue);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return returnValue;
    }
}
