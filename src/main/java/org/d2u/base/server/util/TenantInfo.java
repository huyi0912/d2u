package org.d2u.base.server.util;

public class TenantInfo {
    static ThreadLocal<String> tenant = new ThreadLocal<String>();

    public static void setSchema(String schema){
        tenant.set(schema);
    }

    public static String getSchema(){
        return tenant.get();
    }
}
