package com.mpb.salon.bookig.system.service;


import com.mpb.salon.bookig.system.entity.Services;
import com.mpb.salon.bookig.system.entity.auth.Role;
import com.mpb.salon.bookig.system.web.req.ServicesSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServicesService extends CRUDServices<Services> {
    Page<Services> search(ServicesSearch servicesSearch, Pageable pageable);
}
