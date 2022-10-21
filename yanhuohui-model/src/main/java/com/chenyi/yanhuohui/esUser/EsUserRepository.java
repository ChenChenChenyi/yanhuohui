package com.chenyi.yanhuohui.esUser;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsUserRepository extends ElasticsearchRepository<EsUser, Long> {
}
