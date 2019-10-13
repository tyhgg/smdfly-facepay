package com.tyhgg.core.framework.cache;

import java.util.Map;

/**
 * 
 * @author zyt5668
 *
 * @param <K>
 * @param <V>
 */
public interface SystemCache<K, V>{

	/**
	 * 根据指定的键获取缓存中的值
	 */
	public Object get(K key);

	/**
	 *刷新缓存中的值
	 */
	public void refreshCache();
	
	
	/**
	 *获取所有缓存对象
	 */
	public Map<K, V> getMaps();
	
}
