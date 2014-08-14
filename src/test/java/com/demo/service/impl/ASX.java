package com.demo.service.impl;

import org.unique.ioc.annotation.Autowired;
import org.unique.ioc.annotation.Service;

import com.demo.service.A;
import com.demo.service.B;

@Service
public class ASX implements A{
    
    @Autowired
    private B b;
    
    @Override
    public void sayAA(){
        b.sayBB();
    }
}
