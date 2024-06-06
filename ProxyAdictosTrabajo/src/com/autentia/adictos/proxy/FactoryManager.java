package com.autentia.adictos.proxy;

import java.lang.reflect.Proxy;

public class FactoryManager {
    
        public IUserManager createManager(Class claseManager) {
             
            IUserManager realUserManager = null;
            
            try {
                // Creamos un objeto de la clase que recibimos.
            	realUserManager = (IUserManager)claseManager.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            
          /* Creamos el CallBack Handler y le pasamos el objeto real para ser invocado               posteriormente en su método invoke. */
         
            ProxyManager proxyManager = new ProxyManager(realUserManager);
            
            // Creamos el proxy.
            Class[] interfacesQueEncapsulo = new Class[] {IUserManager.class};   
            return (IUserManager)Proxy.newProxyInstance(
                           claseManager.getClassLoader(),
                            interfacesQueEncapsulo,proxyManager);                   
        }     
}