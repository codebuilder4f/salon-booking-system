package com.mpb.salon.bookig.system.service.impl;

import com.mpb.salon.bookig.system.entity.Services;
import com.mpb.salon.bookig.system.repository.ServicesRepository;
import com.mpb.salon.bookig.system.service.ServicesService;
import com.mpb.salon.bookig.system.service.specifications.ServicesSpecification;
import com.mpb.salon.bookig.system.web.exception.EntityNotFoundException;
import com.mpb.salon.bookig.system.web.req.ServicesSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServicesServiceImpl implements ServicesService {

    private final ServicesRepository repository;

    public ServicesServiceImpl(ServicesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Services get(UUID uuid) {
        Optional<Services> services = repository.findById(uuid);
        if (services.isPresent()) {
            return services.get();
        } else {
            throw new EntityNotFoundException(Services.class, "id", uuid.toString());
        }
    }

    @Override
    public Services create(Services services) {
        return repository.save(services);
    }

    @Override
    public Services update(UUID uuid, Services services) {
        Optional<Services> services1 = repository.findById(uuid);
        if (services1.isPresent()) {
            services.setId(uuid);
            return repository.save(services);
        } else {
            throw new EntityNotFoundException(Services.class, "id", uuid.toString());
        }
    }

    @Override
    public void delete(UUID uuid) {
        Optional<Services> services = repository.findById(uuid);
        if (services.isPresent()) {
            repository.delete(services.get());
        } else {
            throw new EntityNotFoundException(Services.class, "id", uuid.toString());
        }
    }

    @Override
    public List<Services> createAll(List<Services> list) {
        return list.stream().parallel().map(this::create).collect(Collectors.toList());
    }

    @Override
    public Page<Services> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Services> search(ServicesSearch servicesSearch, Pageable pageable) {
        return repository.findAll(new ServicesSpecification(servicesSearch), pageable);
    }
}
