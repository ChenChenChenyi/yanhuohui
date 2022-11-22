package com.chenyi.yanhuohui.service.porktestservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.chenyi.yanhuohui.manager.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WareHouseApiImpl.class})
@ExtendWith(SpringExtension.class)
class WareHouseApiImplTest {
    @MockBean
    private ManagerRepository managerRepository;

    @Autowired
    private WareHouseApiImpl wareHouseApiImpl;

    @Test
    void testQueryPorkSum() {
        when(this.managerRepository.queryPork()).thenReturn("42");
        assertEquals(42L, this.wareHouseApiImpl.queryPorkSum().longValue());
        verify(this.managerRepository).queryPork();
    }
}

