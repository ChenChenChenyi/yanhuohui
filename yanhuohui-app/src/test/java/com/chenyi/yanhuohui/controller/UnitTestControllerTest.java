package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import com.chenyi.yanhuohui.manager.Manager;
import com.chenyi.yanhuohui.manager.ManagerRepository;
import com.chenyi.yanhuohui.manager.PorkInst;
import com.chenyi.yanhuohui.service.porktestservice.FactoryApi;
import com.chenyi.yanhuohui.service.porktestservice.WareHouseApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@Slf4j
class UnitTestControllerTest {

    /**
     * controller入口，由于是链路入口，无需用@Spy监听
     */
    @InjectMocks
    private UnitTestController porkController;

    /**
     * 接口类型的链路环节用实现类初始化代替, @Spy需要手动初始化避免initMocks时失败
     * 注：链路上每一环都必须声明，即使测试用例中并没有被显性调用
     */
//    @InjectMocks
//    @Spy
//    private PorkServiceImpl porkService = new PorkServiceImpl();

    /**
     * 待Mock的链路环节，下同
     */
    @Mock
    private ManagerRepository porkStorageDao;

    @Mock
    private FactoryApi factoryApi;

    @Mock
    private WareHouseApi wareHouseApi;

    /**
     * 预置数据可直接作为类变量声明,存放是的 购买人和配送地址
     */
    private final Map<String, Object> mockParams = new HashMap<String, Object>() {{
        put("user", "system_user");
    }};

    @Before
    public void setup() {
        // 必要: 初始化该类中所声明的Mock和InjectMock对象
        MockitoAnnotations.initMocks(this);

        // Mock预置数据并绑定相关方法(适用于有返回值的方法)
        Manager mockStorage = Manager.builder().id(1L).name("40").build();

        // 常见Mock写法一：仅试图Mock返回值
        when(porkStorageDao.findById(269L)).thenReturn(Optional.ofNullable(mockStorage));

        // 常见Mock写法二：不仅试图Mock返回值，还想额外打些日志方便定位
        when(wareHouseApi.packagePork(any(), any()))
                .thenAnswer(ans -> {
                    log.info("mock log can be written here");
                    return PorkInst.builder()
                            .weight(ans.getArgument(0,Long.class))
                            .paramsMap(ans.getArgument(1,Map.class))
                            .build();
                });

        // Mock动作并绑定相关方法(适用于无返回值方法)
        doAnswer((Answer<Void>) invocationOnMock -> {
            log.info("mock factory api success!");
            return null;
        }).when(factoryApi).supplyPork(any());
    }

    @After
    public void teardown() {
        // TODO: 可以加入Mock数据清理或资源释放
    }

    /**
     * 当传入参数为null时，抛出业务异常
     *
     * @throws
     */
    @Test(expected = SbcRuntimeException.class)
    public void testBuyPorkIfWeightIsNull() {
        porkController.buyPork(null, mockParams);
    }

    /**
     * 当后台库存不满足需求时，抛出业务异常
     *
     * @throws SbcRuntimeException
     */
    @Test(expected = SbcRuntimeException.class)
    public void testBuyPorkIfStorageIsShortage() {
        porkController.buyPork(20L, mockParams);
    }

    /**
     * 正常购买时返回业务结果
     */
    @Test
    public void testBuyPorkIfResultIsOk() {
        Long expectWeight = 5L;

        ResponseEntity<PorkInst> res = porkController.buyPork(expectWeight, mockParams);
        // 此处第一次校验接口返回状态是否符合预期
        Assert.assertEquals(HttpStatus.OK, res.getStatusCode());

        Long actualWeight = Optional.of(res).map(HttpEntity::getBody).map(PorkInst::getWeight).orElse(-99L);
        // 此处第二次校验接口返回值是否符合预期
        Assert.assertEquals(expectWeight, actualWeight);
    }
}