package com.study.radasm.vanhttpclient.common;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;

import java.util.concurrent.TimeUnit;

/**
 * 客户端连接管理器
 *
 * Created by RadAsm on 15/6/9.
 */
public class VanClientConnectionManager implements ClientConnectionManager {
    private SchemeRegistry schemeRegistry;

    public VanClientConnectionManager(SchemeRegistry schemeRegistry){
        this.schemeRegistry=schemeRegistry;

    }



    @Override
    public SchemeRegistry getSchemeRegistry() {
        return null;
    }

    @Override
    public ClientConnectionRequest requestConnection(HttpRoute httpRoute, Object o) {
        return null;
    }

    @Override
    public void releaseConnection(ManagedClientConnection managedClientConnection, long l, TimeUnit timeUnit) {

    }

    @Override
    public void closeIdleConnections(long l, TimeUnit timeUnit) {

    }

    @Override
    public void closeExpiredConnections() {

    }

    @Override
    public void shutdown() {

    }
}
