package com.tyhgg.core.framework.cache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyhgg.core.framework.util.PropertiesFileUtils;

/**
 * 查询数据库表缓存
 * @author zyt5668
 *
 */
public class SystemCacheJdbcUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemCacheJdbcUtil.class);
    private static Properties jdbcProperties = PropertiesFileUtils.getSystemProperties();
	
    static {
		try {
			Class.forName(jdbcProperties.getProperty("spring.datasource.driver-class-name"));
		} catch (ClassNotFoundException e) {
			LOGGER.error("加载数据库驱动失败", e);
		}
    }

    /**
     * 查询system_properties表的数据
     * @return
     */
    public Map<String, String> loadSystemProperties(){

        Map<String, String> map = new HashMap<String, String>();

        Connection conn = null;
	    PreparedStatement pstat = null;
	    ResultSet rs = null;
        try {
        	conn = DriverManager.getConnection(jdbcProperties.getProperty("spring.datasource.url") , 
            		jdbcProperties.getProperty("spring.datasource.username") , jdbcProperties.getProperty("spring.datasource.password"));
        	pstat = conn.prepareStatement("select sys_key, sys_value from system_properties");
            rs = pstat.executeQuery();
            LOGGER.info(pstat.toString());

            while (rs.next()) {
                map.put(rs.getString("sys_key"), rs.getString("sys_value"));
            }
            
            LOGGER.info("加载system_properties数据成功:"+map.size());
        } catch (Exception e) {
            LOGGER.error("查询system_properties表失败", e);
        } finally {
        	try {
                if (null !=  rs) {
                	rs.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != pstat) {
                	pstat.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != conn) {
                	conn.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        }

        return map;
    }
    
    /**
     * 查询b_msg表的数据
     * @return
     */
    public Map<String, String> loadMsg(){

        Map<String, String> map = new HashMap<String, String>();

        Connection conn = null;
	    PreparedStatement pstat = null;
	    ResultSet rs = null;
        try {
        	conn = DriverManager.getConnection(jdbcProperties.getProperty("spring.datasource.url") , 
            		jdbcProperties.getProperty("spring.datasource.username") , jdbcProperties.getProperty("spring.datasource.password"));
        	pstat = conn.prepareStatement("select msg_key, msg_value from b_msg");
            rs = pstat.executeQuery();
            LOGGER.info(pstat.toString());
            while (rs.next()) {
                map.put(rs.getString("msg_key"), rs.getString("msg_value"));
            }

            LOGGER.info("加载b_msg数据成功:"+map.size());
        } catch (Exception e) {
            LOGGER.error("查询b_msg表失败", e);
        } finally {
        	try {
                if (null !=  rs) {
                	rs.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != pstat) {
                	pstat.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != conn) {
                	conn.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        }

        return map;
    }
    
    /**
     * 查询b_country表的数据
     * @return
     */
    public Map<String, String> loadCountryMap(){

        Map<String, String> map = new HashMap<String, String>();

        Connection conn = null;
	    PreparedStatement pstat = null;
	    ResultSet rs = null;
        try {
        	conn = DriverManager.getConnection(jdbcProperties.getProperty("spring.datasource.url") , 
            		jdbcProperties.getProperty("spring.datasource.username") , jdbcProperties.getProperty("spring.datasource.password"));
        	pstat = conn.prepareStatement("select country_id, country_name from b_country");
            rs = pstat.executeQuery();
            LOGGER.info(pstat.toString());
            while (rs.next()) {
                map.put(rs.getString("country_id"), rs.getString("country_name"));
            }
            
            LOGGER.info("加载b_country数据成功:"+map.size());
        } catch (Exception e) {
            LOGGER.error("查询b_country表失败", e);
        } finally {
        	try {
                if (null !=  rs) {
                	rs.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != pstat) {
                	pstat.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != conn) {
                	conn.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        }

        return map;
    }
    
    /**
     * 查询b_tran表的数据
     * @return
     */
    public Map<String, String> loadTran(){

        Map<String, String> map = new LinkedHashMap<String, String>();

        Connection conn = null;
	    PreparedStatement pstat = null;
	    ResultSet rs = null;
        try {
        	conn = DriverManager.getConnection(jdbcProperties.getProperty("spring.datasource.url") , 
            		jdbcProperties.getProperty("spring.datasource.username") , jdbcProperties.getProperty("spring.datasource.password"));
        	pstat = conn.prepareStatement("select tran_url, tran_name from b_tran where tran_status = '1'");
            rs = pstat.executeQuery();
            LOGGER.info(pstat.toString());
            while (rs.next()) {
                map.put(rs.getString("tran_url"), rs.getString("tran_name"));
            }
            
            LOGGER.info("加载b_tran数据成功:"+map.size());
        } catch (Exception e) {
            LOGGER.error("查询b_tran表失败", e);
        } finally {
        	try {
                if (null !=  rs) {
                	rs.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != pstat) {
                	pstat.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != conn) {
                	conn.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        }

        return map;
    }

    /**
     * 查询b_tran_userid_check表的数据
     * @return
     */
    public Map<String, String> loadTranUseridCheck(){

        Map<String, String> map = new LinkedHashMap<String, String>();

        Connection conn = null;
	    PreparedStatement pstat = null;
	    ResultSet rs = null;
        try {
        	conn = DriverManager.getConnection(jdbcProperties.getProperty("spring.datasource.url") , 
            		jdbcProperties.getProperty("spring.datasource.username") , jdbcProperties.getProperty("spring.datasource.password"));
        	pstat = conn.prepareStatement("select tran_url, body_field from b_tran_userid_check");
            rs = pstat.executeQuery();
            LOGGER.info(pstat.toString());
            while (rs.next()) {
                map.put(rs.getString("tran_url"), rs.getString("body_field"));
            }
            
            LOGGER.info("加载b_tran_userid_check数据成功:"+map.size());
        } catch (Exception e) {
            LOGGER.error("查询b_tran_userid_check表失败", e);
        } finally {
        	try {
                if (null !=  rs) {
                	rs.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != pstat) {
                	pstat.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != conn) {
                	conn.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        }

        return map;
    }

    /**
     * 接口权限控制
     * 查询b_client_tran_rel表的数据
     * @return
     */
    public Map<String, String> loadClientTranCheck(){

        Map<String, String> map = new LinkedHashMap<String, String>();

        Connection conn = null;
	    PreparedStatement pstat = null;
	    ResultSet rs = null;
        try {
        	conn = DriverManager.getConnection(jdbcProperties.getProperty("spring.datasource.url") , 
            		jdbcProperties.getProperty("spring.datasource.username") , jdbcProperties.getProperty("spring.datasource.password"));
        	pstat = conn.prepareStatement("SELECT client_id,tran_url FROM b_client_tran_rel");
            rs = pstat.executeQuery();
            LOGGER.info(pstat.toString());
            while (rs.next()) {
                map.put(rs.getString("tran_url")+rs.getString("client_id"), rs.getString("client_id"));
            }
            
            LOGGER.info("加载b_client_tran_rel数据成功:"+map.size());
        } catch (Exception e) {
            LOGGER.error("查询b_client_tran_rel表失败", e);
        } finally {
        	try {
                if (null !=  rs) {
                	rs.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != pstat) {
                	pstat.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        	try {
                if (null != conn) {
                	conn.close();
                }
            } catch (Exception e) {
                LOGGER.error("关闭数据库资源异常：", e);
            }
        }

        return map;
    }
}