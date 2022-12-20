package com.mpb.salon.bookig.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface CRUDServices<T> {
    T get(UUID uuid);
    T create(T t);
    T update(UUID uuid, T t);
    void delete(UUID uuid);

    List<T> createAll(List<T> list);
    Page<T> getAll(Pageable pageable);
}
