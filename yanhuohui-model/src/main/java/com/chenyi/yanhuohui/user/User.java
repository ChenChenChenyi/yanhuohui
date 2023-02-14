package com.chenyi.yanhuohui.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //@Dict
    private String name;
    //@ChangeValue
    private String email;
    @Column
    private String nameCN;
//    @ManyToOne
//    //private ParkingSpace ps;
//    //@DateFormatter(value = "yyyy-MM-dd HH:mm:ss")
//    private Date createTime;

    public User(Long i, String name) {
        this.id = i;
        this.name = name;
    }
}
