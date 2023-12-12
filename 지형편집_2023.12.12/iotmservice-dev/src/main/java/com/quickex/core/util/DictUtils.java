package com.quickex.core.util;




import com.quickex.core.constant.Constants;
import com.quickex.core.redis.RedisCache;
import com.quickex.core.util.spring.SpringUtils;
import com.quickex.domain.layer.KoDictData;

import java.util.Collection;
import java.util.List;

/**
 * Dictionary tool class
 *
 * @author ffzh.thero
 */
public class DictUtils {
    /**
     * Separator
     */
    public static final String SEPARATOR = ",";

    /**
     * Set dictionary cache
     *
     * @param key       Parameter key
     * @param dictDatas Dictionary data list
     */
    public static void setDictCache(String key, List<KoDictData> dictDatas) {
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), dictDatas);
    }

    /**
     * Get dictionary cache
     *
     * @param key
     * @return dictDatas
     */
    public static List<KoDictData> getDictCache(String key) {
        Object cacheObj = SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj)) {
            List<KoDictData> dictDatas = StringUtils.cast(key);
            return dictDatas;
        }
        return null;
    }

    /**
     * Gets the dictionary label based on the dictionary type and dictionary value
     *
     * @param dictType  Dictionary type
     * @param dictValue Dictionary value
     * @return Dictionary label
     */
    public static String getDictLabel(String dictType, String dictValue) {
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }

    /**

     *Get dictionary value according to dictionary type and dictionary label

     *

     *@ param dicttype dictionary type

     *@ param dictlabel dictionary label

     *@ return dictionary value

     */
    public static String getDictValue(String dictType, String dictLabel) {
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }

    /**

     *Gets the dictionary label based on the dictionary type and dictionary value

     *

     *@ param dicttype dictionary type

     *@ param dictvalue dictionary value

     *@ param separator separator separator

     *@ return dictionary label

     */
    public static String getDictLabel(String dictType, String dictValue, String separator) {
        StringBuilder propertyString = new StringBuilder();
        List<KoDictData> datas = getDictCache(dictType);

        if (StringUtils.containsAny(separator, dictValue) && StringUtils.isNotEmpty(datas)) {
            for (KoDictData dict : datas) {
                for (String value : dictValue.split(separator)) {
                    if (value.equals(dict.getDictValue())) {
                        propertyString.append(dict.getDictLabel() + separator);
                        break;
                    }
                }
            }
        } else {
            for (KoDictData dict : datas) {
                if (dictValue.equals(dict.getDictValue())) {
                    return dict.getDictLabel();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**

     *Get dictionary value according to dictionary type and dictionary label

     *

     *@ param dicttype dictionary type

     *@ param dictlabel dictionary label

     *@ param separator separator separator

     *@ return dictionary value

     */
    public static String getDictValue(String dictType, String dictLabel, String separator) {
        StringBuilder propertyString = new StringBuilder();
        List<KoDictData> datas = getDictCache(dictType);

        if (StringUtils.containsAny(separator, dictLabel) && StringUtils.isNotEmpty(datas)) {
            for (KoDictData dict : datas) {
                for (String label : dictLabel.split(separator)) {
                    if (label.equals(dict.getDictLabel())) {
                        propertyString.append(dict.getDictValue() + separator);
                        break;
                    }
                }
            }
        } else {
            for (KoDictData dict : datas) {
                if (dictLabel.equals(dict.getDictLabel())) {
                    return dict.getDictValue();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * Empty dictionary cache
     */
    public static void clearDictCache() {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.SYS_DICT_KEY + "*");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

    /**
     * Empty a dictionary cache
     */
    public static void clearOneDictCache(String type) {
        SpringUtils.getBean(RedisCache.class).deleteObject(getCacheKey(type));
    }

    /**

     *Set cache key

     *

     *@ param configkey parameter key

     *@ return cache key

     */
    public static String getCacheKey(String configKey) {
        return Constants.SYS_DICT_KEY + configKey;
    }
}
