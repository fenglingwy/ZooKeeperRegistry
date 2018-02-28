package com.wy.service.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ServiceRegistryImpl implements ServiceRegistry,Watcher {

    private static final String ROOT_REGISTRY = "/registry";
    private static final int SESSION_TIMEOUT = 5000;
    private static CountDownLatch latch = new CountDownLatch(1);
    private ZooKeeper zk ;


    public ServiceRegistryImpl() {

    }

    public ServiceRegistryImpl(String zkServers) {

        try {
            zk = new ZooKeeper(zkServers,SESSION_TIMEOUT,this);
            log.info("链接成功！");
            latch.await();
        }catch (Exception e){
            log.error("创建失败！",e);
        }

    }


    @Override
    public void registe(String serviceName, String serviceRegistry,String serviceAddress) {
        try {
            if(zk.exists(ROOT_REGISTRY,false)==null){
                zk.create(ROOT_REGISTRY,null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            String servicePath = ROOT_REGISTRY+"/"+serviceName;

            if(zk.exists(servicePath,false)==null){
                zk.create(servicePath,null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            String addressPath = servicePath+"/Address_";
            zk.create(addressPath,serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        }catch (Exception e){
            log.error("创建失败！",e);
        }

    }


    @Override
    public void process(WatchedEvent event) {
        if(event.getState()== Event.KeeperState.SyncConnected){
            latch.countDown();
        }
    }

}
