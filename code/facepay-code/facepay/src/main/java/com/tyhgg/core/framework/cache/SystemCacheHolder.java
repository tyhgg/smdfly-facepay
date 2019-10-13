package com.tyhgg.core.framework.cache;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 缓存实现类持有对象， SystemCacheHolder不可实例化对象，只导出共有静态方法返回所需要的缓存
 * 
 * @创建人:zyt5668
 * @创建时间:2014-12-23 上午15:39:55
 * @修改人:
 * @修改时间:
 * @修改内容:
 */
public final class SystemCacheHolder {

    public enum CacheType {
        SYSTEMPROPERTY_CACHE, MSG_CACHE, TRAN_USERID_CHECK_CACHE, CLIENT_TRAN_CACHE, ALL;
    }

    private static final SystemCache<String, String> SYSTEM_PROPERTY_CACHE = new SystemPropertyCache();
    private static final SystemCache<String, String> MSGC_CACHE = new MsgCache();
    
    private static final SystemCache<String, String> COUNTRY_CACHE = new CountryMapCache();
    
    private static final SystemCache<String, String> TRAN_CACHE = new TranCache();
    private static final SystemCache<String, String> TRAN_USERID_CHECK_CACHE = new TranUseridCheckCache();
//    private static final SystemCache<String, String> CLIENT_TRAN_CACHE = new ClientTranCache();
    
    private SystemCacheHolder() {
    }

    public static SystemCache<String, String> getSystemPropertyCache() {

        return SYSTEM_PROPERTY_CACHE;
    }

    public static SystemCache<String, String> getMsgCache() {

        return MSGC_CACHE;
    }

    public static SystemCache<String, String> getCountryCache() {

        return COUNTRY_CACHE;
    }
    
    public static SystemCache<String, String> getTranUseridCheckCache() {

        return TRAN_USERID_CHECK_CACHE;
    }

    public static SystemCache<String, String> getTranCache() {

        return TRAN_CACHE;
    }

//    public static SystemCache<String, String> getClientTranCache() {
//
//        return CLIENT_TRAN_CACHE;
//    }
    
    /**
     * SystemPropertyCache为内部类 ，SystemProperty 表缓存，该类只能读取不能修改
     * */
    private static final class SystemPropertyCache implements SystemCache<String, String> {

        private final ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();

        private SystemPropertyCache() {
            this.refreshCache();
        }

        @Override
        public String get(String key) {
            String str = map.get(key);
            
            return str;
        }

        @Override
        public Map<String, String> getMaps() {
            return Collections.unmodifiableMap(this.map);
        }
        
        @Override
        public void refreshCache() {

            map.clear();

            SystemCacheJdbcUtil systemCacheJdbcUtil = new SystemCacheJdbcUtil();

            map.putAll(systemCacheJdbcUtil.loadSystemProperties());
        }

    }

    /**
     * MsgCache为内部类 ，b_msg 表缓存，该类只能读取不能修改
     * */
    private static final class MsgCache implements SystemCache<String, String> {

        private final ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();

        private MsgCache() {
            this.refreshCache();
        }

        @Override
        public String get(String key) {
            
            String str = map.get(key);
            
            return str;
        }

        @Override
        public Map<String, String> getMaps() {
            return Collections.unmodifiableMap(this.map);
        }
        
        @Override
        public void refreshCache() {

            map.clear();

            SystemCacheJdbcUtil systemCacheJdbcUtil = new SystemCacheJdbcUtil();

            map.putAll(systemCacheJdbcUtil.loadMsg());
        }
    }
    

    /**
     * loadCountryMapCache为内部类 ，b_nation_cache 表缓存，该类只能读取不能修改
     * */
    private static final class CountryMapCache implements SystemCache<String, String> {

         private final ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();

        private CountryMapCache() {
            this.refreshCache();
        }

        @Override
        public String get(String key) {
            String str = map.get(key);
            
            return str;
        }

        @Override
        public Map<String, String> getMaps() {
            return Collections.unmodifiableMap(this.map);
        }
        
        @Override
        public void refreshCache() {
            map.clear();
            SystemCacheJdbcUtil systemCacheJdbcUtil = new SystemCacheJdbcUtil();
            map.putAll(systemCacheJdbcUtil.loadCountryMap());
        }

    }


    
    /**
     * OrgNotesCache为内部类 ，b_org_notes 表缓存，该类只能读取不能修改
     * */
    private static final class TranCache implements SystemCache<String, String> {

        private final ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();

        private TranCache() {
            this.refreshCache();
        }

        @Override
        public String get(String key) {
            String str = map.get(key);
            
            return str;
        }

        @Override
        public Map<String, String> getMaps() {
            return Collections.unmodifiableMap(this.map);
        }
        
        @Override
        public void refreshCache() {

            map.clear();

            SystemCacheJdbcUtil systemCacheJdbcUtil = new SystemCacheJdbcUtil();

            map.putAll(systemCacheJdbcUtil.loadTran());
        }

    }

    /**
     * OrgNotesCache为内部类 ，b_org_notes 表缓存，该类只能读取不能修改
     * */
    private static final class TranUseridCheckCache implements SystemCache<String, String> {

        private final ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();

        private TranUseridCheckCache() {
            this.refreshCache();
        }

        @Override
        public String get(String key) {
            String str = map.get(key);
            
            return str;
        }

        @Override
        public Map<String, String> getMaps() {
            return Collections.unmodifiableMap(this.map);
        }
        
        @Override
        public void refreshCache() {

            map.clear();

            SystemCacheJdbcUtil systemCacheJdbcUtil = new SystemCacheJdbcUtil();

            map.putAll(systemCacheJdbcUtil.loadTranUseridCheck());
        }

    }
    
    /**
     * ClientTranCache为内部类 ，b_client_tran_rel 表缓存，该类只能读取不能修改
     * */
    private static final class ClientTranCache implements SystemCache<String, String> {

        private final ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();

        private ClientTranCache() {
            this.refreshCache();
        }

        @Override
        public String get(String key) {
            String str = map.get(key);
            
            return str;
        }

        @Override
        public Map<String, String> getMaps() {
            return Collections.unmodifiableMap(this.map);
        }
        
        @Override
        public void refreshCache() {

            map.clear();

            SystemCacheJdbcUtil systemCacheJdbcUtil = new SystemCacheJdbcUtil();

            map.putAll(systemCacheJdbcUtil.loadClientTranCheck());
        }

    }
}
