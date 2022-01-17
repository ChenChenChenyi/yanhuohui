package com.chenyi.yanhuohui.manager;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 猪肉实例，由仓库打包后生成
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PorkInst {
    /**
     * 重量
     */
    private Long weight;

    /**
     * 附件参数，例如包装类型，寄送地址等信息
     */
    private Map<String, Object> paramsMap;
}
