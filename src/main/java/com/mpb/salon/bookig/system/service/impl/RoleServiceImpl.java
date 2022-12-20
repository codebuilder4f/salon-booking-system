package com.mpb.salon.bookig.system.service.impl;



import com.mpb.salon.bookig.system.entity.auth.Role;
import com.mpb.salon.bookig.system.repository.RoleRepository;
import com.mpb.salon.bookig.system.service.RoleService;
import com.mpb.salon.bookig.system.web.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role get(UUID uuid) {
        Optional<Role> role = repository.findById(uuid);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new EntityNotFoundException(Role.class, "id", uuid.toString());
        }
    }

    @Override
    public Role create(Role role) {
        return repository.save(role);
    }

    @Override
    public Role update(UUID uuid, Role role) {
        Optional<Role> role1 = repository.findById(uuid);
        if (role1.isPresent()) {
            role.setId(uuid);
            return repository.save(role);
        } else {
            throw new EntityNotFoundException(Role.class, "id", uuid.toString());
        }
    }

    @Override
    public void delete(UUID uuid) {
        Optional<Role> role = repository.findById(uuid);
        if (role.isPresent()) {
            repository.delete(role.get());
        } else {
            throw new EntityNotFoundException(Role.class, "id", uuid.toString());
        }
    }

    @Override
    public List<Role> createAll(List<Role> list) {
        return list.stream().parallel().map(this::create).collect(Collectors.toList());
    }

    @Override
    public Page<Role> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
