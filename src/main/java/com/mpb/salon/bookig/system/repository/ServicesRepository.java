package com.mpb.salon.bookig.system.repository;




import com.mpb.salon.bookig.system.entity.Services;
import com.mpb.salon.bookig.system.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServicesRepository extends JpaRepository<Services, UUID>, JpaSpecificationExecutor<Services> {
}
