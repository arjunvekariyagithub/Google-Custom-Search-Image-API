package com.smule.test.data.preferences;



public interface PreferencesHelper {

    void saveState(String keyString, int key);

    void saveState(String keyString, String key);

    void saveState(String keyString, long key);

    void saveState(String keyString, boolean key);

    String loadStringKey(String keyString);

    int loadIntKey(String keyString, int returnValue);

    long loadLongKey(String keyString, long returnValue);

    boolean loadBooleanKey(String keyString, boolean returnValue);

    String loadSettingsStringKey(String keyString, String returnValue);
}
