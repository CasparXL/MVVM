package com.lxb.mvvmproject.helper;

import android.content.Context;
import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Collections;
import java.util.Set;

/**
 * 腾讯MMKV储存数据工具类
 * 具备SharedPreferences的特性，但又不会因为频繁操作导致数据混乱或ANR
 * 开源库地址：https://github.com/Tencent/MMKV
 */
public class MMKVUtil {
    private MMKV mmkv;
    private volatile static MMKVUtil INSTANCE;

    //初始化
    public static void init(Context c) {
        MMKV.initialize(c);
    }

    private MMKVUtil() {
        mmkv = MMKV.defaultMMKV();
    }

    public static MMKVUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (MMKVUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MMKVUtil();
                }
            }
        }
        return INSTANCE;
    }


    //基础类型赋值
    public void encode(String key, Object value) {
        if (value instanceof String) {
            if (mmkv != null) {
                mmkv.encode(key, (String) value);
            }
        } else if (value instanceof Float) {
            if (mmkv != null) {
                mmkv.encode(key, ((Number) value).floatValue());
            }
        } else if (value instanceof Boolean) {
            if (mmkv != null) {
                mmkv.encode(key, (Boolean) value);
            }
        } else if (value instanceof Integer) {
            if (mmkv != null) {
                mmkv.encode(key, ((Number) value).intValue());
            }
        } else if (value instanceof Long) {
            if (mmkv != null) {
                mmkv.encode(key, ((Number) value).longValue());
            }
        } else if (value instanceof Double) {
            if (mmkv != null) {
                mmkv.encode(key, ((Number) value).doubleValue());
            }
        } else if (value instanceof byte[]) {
            if (mmkv != null) {
                mmkv.encode(key, (byte[]) value);
            }
        }
    }

    //序列化赋值
    public void encode(String key, Parcelable t) {
        if (t != null) {
            if (mmkv != null) {
                mmkv.encode(key, t);
            }
        }
    }

    //Set赋值
    public void encode(String key, Set sets) {
        if (sets != null) {
            if (mmkv != null) {
                mmkv.encode(key, sets);
            }

        }
    }


    public Integer decodeInt(String key) {
        return mmkv != null ? mmkv.decodeInt(key, 0) : null;
    }


    public Double decodeDouble(String key) {
        return mmkv != null ? mmkv.decodeDouble(key, 0.0D) : null;
    }


    public Long decodeLong(String key) {
        return mmkv != null ? mmkv.decodeLong(key, 0L) : null;
    }


    public Boolean decodeBoolean(String key) {
        return mmkv != null ? mmkv.decodeBool(key, false) : null;
    }


    public Float decodeFloat(String key) {
        return mmkv != null ? mmkv.decodeFloat(key, 0.0F) : null;
    }


    public byte[] decodeByteArray(String key) {
        return mmkv != null ? mmkv.decodeBytes(key) : null;
    }


    public String decodeString(String key) {
        return mmkv != null ? mmkv.decodeString(key, "") : null;
    }


    public Parcelable decodeParcelable(String key, Class tClass) {
        return mmkv != null ? mmkv.decodeParcelable(key, tClass) : null;
    }


    public Set decodeStringSet(String key) {
        return mmkv != null ? mmkv.decodeStringSet(key, Collections.emptySet()) : null;
    }

    public void removeKey(String key) {
        if (mmkv != null) {
            mmkv.removeValueForKey(key);
        }

    }

    public void clearAll() {
        if (mmkv != null) {
            mmkv.clearAll();
        }

    }


}
