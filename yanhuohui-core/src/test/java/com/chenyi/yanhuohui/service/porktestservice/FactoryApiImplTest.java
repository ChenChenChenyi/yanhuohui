package com.chenyi.yanhuohui.service.porktestservice;

import com.chenyi.yanhuohui.manager.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FactoryApiImpl.class})
@ExtendWith(SpringExtension.class)
class FactoryApiImplTest {
    @InjectMocks
    private FactoryApiImpl factoryApiImpl;

    @Mock
    private ManagerRepository managerRepository;

    @Test
    void testSupplyPork() {
        // TODO: This test is incomplete.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by supplyPork(Long)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        this.factoryApiImpl.supplyPork(1L);
    }
}

