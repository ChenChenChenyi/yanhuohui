package com.chenyi.yanhuohui.request;

import com.chenyi.yanhuohui.manager.Manager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerQueryRequest implements Serializable {
    private static final long serialVersionUID = -9160258192363545023L;

    private Long id;

    private String name;

    private String role;

    public Specification<Manager> getWhereCriteria(){
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(Objects.nonNull(id)){
                predicates.add(criteriaBuilder.equal(root.get("id"),id));
            }
            if(Objects.nonNull(name) && StringUtils.hasText(name)){
                predicates.add(criteriaBuilder.equal(root.get("name"),name));
            }
            if(Objects.nonNull(role) && StringUtils.hasText(role)){
                predicates.add(criteriaBuilder.equal(root.get("role"),role));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);

            return p.length == 0 ? null : p.length == 1 ? p[0] : criteriaBuilder.and(p);
        };
    }
}
