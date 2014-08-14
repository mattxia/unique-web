package com.demo.service.impl;

import org.unique.ioc.annotation.Service;

import com.demo.service.B;

@Service
public class BSX implements B{

    @Override
    public void sayBB(){
        System.out.println  ("service b ： say BBB");
    }
}
