/**
 * Copyright &copy; 2012-2016 <a href="www.foresee.com">Forsee</a> All rights reserved.
 */
package com.javenluo.ranger.common.utils;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.Iterator;
import java.util.Set;

/**
 * Cache工具类
 * @author gulong
 * @version 2013-5-29
 */
public class CacheUtils {
	
	private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
	
	private static final String SYS_CACHE = "sysCache";

	/**
	 * 获取SYS_CACHE缓存
	 * @param key Key
	 * @return Object
	 */
	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}
	
	/**
	 * 获取SYS_CACHE缓存
	 * @param key Key
	 * @param defaultValue 默认值
	 * @return Object
	 */
	public static Object get(String key, Object defaultValue) {
		Object value = get(key);
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 写入SYS_CACHE缓存
	 * @param key Key
	 * @param value value
	 */
	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}
	
	/**
	 * 从SYS_CACHE缓存中移除
	 * @param key Key
	 */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}
	
	/**
	 * 获取缓存
	 * @param cacheName Cache名称
	 * @param key Key
	 * @return Object
	 */
	public static Object get(String cacheName, String key) {
		return getCache(cacheName).get(getKey(key));
	}
	
	/**
	 * 获取缓存
	 * @param cacheName Cache名称
	 * @param key Key
	 * @param defaultValue 默认值
	 * @return Object
	 */
	public static Object get(String cacheName, String key, Object defaultValue) {
		Object value = get(cacheName, getKey(key));
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 写入缓存
	 * @param cacheName Cache名称
	 * @param key Key
	 * @param value Object
	 */
	public static void put(String cacheName, String key, Object value) {
		getCache(cacheName).put(getKey(key), value);
	}

	/**
	 * 从缓存中移除
	 * @param cacheName Cache名称
	 * @param key Key
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(getKey(key));
	}

	/**
	 * 从缓存中移除所有
	 * @param cacheName Cache名称
	 */
	public static void removeAll(String cacheName) {
		Cache<String, Object> cache = getCache(cacheName);
		Set<String> keys = cache.keys();
		for (Iterator<String> it = keys.iterator(); it.hasNext();){
			cache.remove(it.next());
		}
	}
	
	/**
	 * 获取缓存键名，多数据源下增加数据源名称前缀
	 * @param key Key
	 * @return String
	 */
	private static String getKey(String key){
//		String dsName = DataSourceHolder.getDataSourceName();
//		if (StringUtils.isNotBlank(dsName)){
//			return dsName + "_" + key;
//		}
		return key;
	}
	
	/**
	 * 获得一个Cache，没有则显示日志。
	 * @param cacheName Cache名称
	 * @return Cache
	 */
	private static Cache<String, Object> getCache(String cacheName){
		Cache<String, Object> cache = cacheManager.getCache(cacheName);
		if (cache == null){
			throw new RuntimeException("当前系统中没有定义“"+cacheName+"”这个缓存。");
		}
		return cache;
	}

}
