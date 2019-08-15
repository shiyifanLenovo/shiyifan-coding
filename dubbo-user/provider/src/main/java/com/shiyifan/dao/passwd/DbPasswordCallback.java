package com.shiyifan.dao.passwd;

import com.alibaba.druid.util.DruidPasswordCallback;

import java.util.Properties;

/**
 * @compang 联想懂的通信
 * @Description: 数据库密文解密
 * @Author: shiyf
 * @CreateDate: 2019/6/20 14:35
 * @UpdateUser: shiyf
 * @UpdateDate: 2019/6/20 14:35
 * @UpdateRemark: 修改内容
 * @Version: cmp2.0
 */
public class DbPasswordCallback extends DruidPasswordCallback {

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String pwd = properties.getProperty("password");
        if (null != pwd && "".equals(pwd)) {
            try {
                //对jdbc.properties配置的密码密文 进行解密
                String password = RsaEncryptAndDecodeUtil.decrypt(pwd);
                setPassword(password.toCharArray());
            } catch (Exception e) {
                setPassword(pwd.toCharArray());
            }
        }
    }
}
