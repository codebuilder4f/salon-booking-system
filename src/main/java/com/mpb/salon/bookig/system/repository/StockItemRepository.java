package com.mpb.salon.bookig.system.repository;




import com.mpb.salon.bookig.system.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, UUID>, JpaSpecificationExecutor<StockItem> {
    List<StockItem> findAllByAddedDateBetween(Date addedDate, Date addedDate2);
;}
