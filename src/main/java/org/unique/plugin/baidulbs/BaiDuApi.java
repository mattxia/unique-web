package org.unique.plugin.baidulbs;

/**
 * 接口常量
 * @author rex
 *
 */
public interface BaiDuApi {

    /*
     * 根据ip获取信息
     */
    public static final String BD_IP_LOCATION_API = "http://api.map.baidu.com/location/ip";
    
    /*
     *根据城市/经纬度获取信息
     */
    public static final String BD_LOCATION2POINT_API = "http://api.map.baidu.com/geocoder/v2/";
    
    /*
     * 百度lbs ak密钥
     */
    public static final String BD_LBS_AK = "G77rtAitpqS9AW2zMWcEjA87";
    
}
