package com.chenyi.yanhuohui.service.ddd;

public interface Identifiable<ID extends Identifier> {
    ID getId();
}
