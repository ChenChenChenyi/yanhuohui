package com.chenyi.yanhuohui.service.porktestservice;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WareHouseApiImpl.class,FactoryApiImpl.class})
@ExtendWith(SpringExtension.class)
class PorkServiceImplTest {

    @Autowired
    private WareHouseApiImpl wareHouseApiImpl;

    @Autowired
    private FactoryApiImpl factoryApiImpl;

    @Test
    void testBuyPorkIfStorageIsShortage(){

    }

}