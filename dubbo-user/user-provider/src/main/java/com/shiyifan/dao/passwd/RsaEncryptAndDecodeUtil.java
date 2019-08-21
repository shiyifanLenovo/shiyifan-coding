package com.pinyg.dao.passwd;

import com.alibaba.druid.filter.config.ConfigTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @compang 联想懂的通信
 * @Description: RSA加解密
 * @Author: shiyf
 * @CreateDate: 2019/6/20 14:35
 * @UpdateUser: shiyf
 * @UpdateDate: 2019/6/20 14:35
 * @UpdateRemark: 修改内容
 * @Version: cmp2.0
 */
public class RsaEncryptAndDecodeUtil {

    private static final Logger logger = LoggerFactory.getLogger(RsaEncryptAndDecodeUtil.class);


    /**
     * 公钥(用于解密)
     */
    private static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdyNp2GBQQquPGR4KaTJ21wNoc0eQj11u+tN41eTs+3RqwySXKFheQBshEgQQ2QjBDsvKVZ/hVAzvLcBPxohbBGrAW7BMn7D8TVHAF0n7VIY3lmvwJwYdtrh/n5BcFOGZscN9OhZgMNVVyg12WEL7RK2D69nPv183uFFM7yCqbRQIDAQAB";
    /**
     * 私钥加密(用于加密)
     */
    private static final String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ3I2nYYFBCq48ZHgppMnbXA2hzR5CPXW7603jV5Oz7dGrDJJcoWF5AGyESBBDZCMEOy8pVn+FUDO8twE/GiFsEasBbsEyfsPxNUcAXSftUhjeWa/AnBh22uH+fkFwU4Zmxw306FmAw1VXKDXZYQvtErYPr2c+/Xze4UUzvIKptFAgMBAAECgYAuURV/8RWCxwdR3xABh1gMUG42Y46azs8JrkJEZC/2BiWdm45SQiXsi3EiWtUHsGpGlTMMlJabLfXRhDb2iWQwGUwMvNUb21G3G/exmJnhTyA2tKR9kVel+S39KuWr8dRyHrLoB0JVBcBW8T0QonLSV6OGqFomnsKmQ88SXsCxiQJBAMps3aqZYd565VIz2kTJDfVjDK9rmbRumUD+9kzUCTuVnk5MPFlA/KL0xbPDCKdZu8pQPbjSJzwkb5x+RvOQEbcCQQDHi2Z84VHMVAXMmN9akKBGrmcN2o4MH2mdAfytVxESjdhW7+rjhPdaGPe8JhdbxpYvTIlzmCm8rFP8bn14FErjAkEAp8RKgxXUUzuvy81F3TR06SdKYCjhnvT69y9YmpwxeryqE2KDscV2nOgY4r+F8I0iYq7WONOTyPi5uA3AaCTDSQJAfa25/jt35oahJGzAeG0Q1ZOwypP11YLPZRlESPTGhvYZ5R+0xFoR5IhHOBSRr7cTDnVZORoEZ5XuS9ZXZhL52wJBAIl9HU4vzAqBPcWtmuw6ADbjF2EGjhlRq+a9YjPoQLKr0Ww9wukRFBJsXABUjK3EHDOsa5H3jVehZNELmeQwM/I=";


    public static void main(String[] args) {
        String miwen = encrypt("Lenovo$123");
        logger.info("密文=====>" + miwen);
        String mingwen = decrypt("icuoQY8/Zogx99F8/WQG7We+MIWuiQSP+VS46GWmekUOhYBBUF4EgVSjhwzDbgSQPSiyaxOrsTpzXjBAV13kVy7oVH/9aJLCWfvbk2UVw6LM5f6+Lr8xnYRZTyuLvJwvgMHFxvrGKeCcHnRGUrrHBtoK0YR/YJqGBOD74MXnUcQ=");
        logger.info("名文=====>" + mingwen);
    }

    /**
     * 描述 私钥加密
     *
     */
    public static String encrypt(String strMing) {
        try {
            return ConfigTools.encrypt(RSA_PRIVATE_KEY, strMing);
        } catch (Exception e) {
           logger.error(e.getMessage(), e);
        }
        return null;

    }

    /**
     * 描述 公钥解密
     *
     */
    public static String decrypt(String strMi) {
        try {
            return ConfigTools.decrypt(RSA_PUBLIC_KEY, strMi);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
