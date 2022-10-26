package com.chenyi.yanhuohui.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsBookQueryPageRequest implements Serializable {
    private static final long serialVersionUID = 2719706914335518933L;

    private Integer pageSize =5;

    private Integer pageNum = 0;
}
