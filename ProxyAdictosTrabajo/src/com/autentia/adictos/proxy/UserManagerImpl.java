package com.autentia.adictos.proxy;

public class UserManagerImpl implements IUserManager{
	
    public void save(Object obj) {        
        System.out.println("I save user Objects");
    }

    public void remove(Integer id) {
        System.out.println("I remove user objects");
    }
    
}
