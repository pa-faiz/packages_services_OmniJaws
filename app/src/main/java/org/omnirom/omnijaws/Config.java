/*
 *  Copyright (C) 2015 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.omnirom.omnijaws;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.preference.PreferenceManager;

public class Config {
    public static final String PREF_KEY_PROVIDER = "provider";
    public static final String PREF_KEY_UNITS = "units";
    public static final String PREF_KEY_LOCATION_ID = "location_id";
    public static final String PREF_KEY_LOCATION_NAME = "location_name";
    public static final String PREF_KEY_CUSTOM_LOCATION = "custom_location";
    public static final String PREF_KEY_WEATHER_DATA = "weather_data";
    public static final String PREF_KEY_LAST_UPDATE = "last_update";
    public static final String PREF_KEY_ENABLE = "enable";
    public static final String PREF_KEY_UPDATE_INTERVAL = "update_interval";
    public static final String PREF_KEY_ICON_PACK = "icon_pack";
    public static final String PREF_KEY_UPDATE_ERROR = "update_error";
    public static final String PREF_KEY_OWM_KEY = "owm_key";
    public static final String PREF_KEY_HISTORY = "history";
    public static final String PREF_KEY_HISTORY_SIZE = "history_size";

    public static AbstractWeatherProvider getProvider(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        switch (prefs.getString(PREF_KEY_PROVIDER, "0")) {
            default:
            case "0":
                return new MeteoWeatherProvider(context);
            case "1":
                return new OpenWeatherMapProvider(context);
            case "2":
                return new METNorwayProvider(context);
        }
    }

    public static String getProviderId(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        String provider = prefs.getString(PREF_KEY_PROVIDER, "0");
        switch (provider) {
            default:
            case "0":
                return "Open-Meteo";
            case "1":
                return "OpenWeatherMap";
            case "2":
                return "MET Norway";
        }
    }

    public static boolean isMetric(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.getString(PREF_KEY_UNITS, "0").equals("0");
    }

    public static boolean isCustomLocation(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.getBoolean(PREF_KEY_CUSTOM_LOCATION, false);
    }

    public static String getLocationId(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.getString(PREF_KEY_LOCATION_ID, null);
    }

    public static void setLocationId(Context context, String id) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        prefs.edit().putString(PREF_KEY_LOCATION_ID, id).commit();
    }

    public static String getLocationName(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.getString(PREF_KEY_LOCATION_NAME, null);
    }

    public static void setLocationName(Context context, String name) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        prefs.edit().putString(PREF_KEY_LOCATION_NAME, name).commit();
    }

    public static WeatherInfo getWeatherData(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        String str = prefs.getString(PREF_KEY_WEATHER_DATA, null);
        if (str != null) {
            WeatherInfo data = WeatherInfo.fromSerializedString(context, str);
            return data;
        }
        return null;
    }

    public static void setWeatherData(Context context, WeatherInfo data) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        prefs.edit().putString(PREF_KEY_WEATHER_DATA, data.toSerializedString()).commit();
        prefs.edit().putLong(PREF_KEY_LAST_UPDATE, System.currentTimeMillis()).commit();
    }

    public static void clearWeatherData(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        prefs.edit().remove(PREF_KEY_WEATHER_DATA).commit();
        prefs.edit().remove(PREF_KEY_LAST_UPDATE).commit();
    }

    public static long getLastUpdateTime(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.getLong(PREF_KEY_LAST_UPDATE, 0);
    }

    public static void clearLastUpdateTime(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        prefs.edit().putLong(PREF_KEY_LAST_UPDATE, 0).commit();
    }

    public static boolean isEnabled(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.getBoolean(PREF_KEY_ENABLE, false);
    }

    public static boolean setEnabled(Context context, boolean value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.edit().putBoolean(PREF_KEY_ENABLE, value).commit();
    }

    public static int getUpdateInterval(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        String valueString = prefs.getString(PREF_KEY_UPDATE_INTERVAL, "2");
        return Integer.valueOf(valueString);
    }

    public static String getIconPack(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.getString(PREF_KEY_ICON_PACK, null);
    }

    public static void setIconPack(Context context, String value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        prefs.edit().putString(PREF_KEY_ICON_PACK, value).commit();
    }

    public static boolean isUpdateError(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.getBoolean(PREF_KEY_UPDATE_ERROR, false);
    }

    public static void setUpdateError(Context context, boolean value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        prefs.edit().putBoolean(PREF_KEY_UPDATE_ERROR, value).commit();
    }

    public static boolean isSetupDone(Context context) {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public static String getOwmKey(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.getString(PREF_KEY_OWM_KEY, null);
    }

    public static boolean isHistoryOn(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.getBoolean(PREF_KEY_HISTORY, true);
    }

    public static void setHistoryOn(Context context, boolean value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        prefs.edit().putBoolean(PREF_KEY_HISTORY, value).commit();
    }

    public static int getHistorySize(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        return prefs.getInt(PREF_KEY_HISTORY_SIZE, 24);
    }

    public static void setHistorySize(Context context, int value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        prefs.edit().putInt(PREF_KEY_HISTORY_SIZE, value).commit();
    }
}
