package com.mpb.salon.bookig.system.service.impl;


import com.mpb.salon.bookig.system.entity.StockItem;
import com.mpb.salon.bookig.system.repository.StockItemRepository;
import com.mpb.salon.bookig.system.service.StockItemService;
import com.mpb.salon.bookig.system.service.dao.DayReport;
import com.mpb.salon.bookig.system.service.specifications.StockItemSpecification;
import com.mpb.salon.bookig.system.web.exception.EntityNotFoundException;
import com.mpb.salon.bookig.system.web.req.StockItemSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StockItemServiceImpl implements StockItemService {

    private final StockItemRepository repository;

    public StockItemServiceImpl(StockItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public StockItem get(UUID uuid) {
        Optional<StockItem> stockItem = repository.findById(uuid);
        if (stockItem.isPresent()) {
            return stockItem.get();
        } else {
            throw new EntityNotFoundException(StockItem.class, "id", uuid.toString());
        }
    }

    @Override
    public StockItem create(StockItem stockItem) {
        return repository.save(stockItem);
    }

    @Override
    public StockItem update(UUID uuid, StockItem stockItem) {
        Optional<StockItem> stockItem1 = repository.findById(uuid);
        if (stockItem1.isPresent()) {
            stockItem.setId(uuid);
            return repository.save(stockItem);
        } else {
            throw new EntityNotFoundException(StockItem.class, "id", uuid.toString());
        }
    }

    @Override
    public void delete(UUID uuid) {
        Optional<StockItem> stockItem = repository.findById(uuid);
        if (stockItem.isPresent()) {
            repository.delete(stockItem.get());
        } else {
            throw new EntityNotFoundException(StockItem.class, "id", uuid.toString());
        }
    }

    @Override
    public List<StockItem> createAll(List<StockItem> list) {
        return list.stream().parallel().map(this::create).collect(Collectors.toList());
    }

    @Override
    public Page<StockItem> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<StockItem> search(StockItemSearch stockItemSearch, Pageable pageable) {
        StockItemSpecification stockItemSpecification = new StockItemSpecification(stockItemSearch);
        return repository.findAll(stockItemSpecification, pageable);
    }

    @Override
    public List<StockItem> getBetween(Date date1, Date date2) {
        return repository.findAllByAddedDateBetween(date1, date2);
    }

    @Override
    public List<DayReport> monthlyStockAddingCost(Date from, Date to) {
        return this.getBetween(from,to)
                .stream()
                .map(item -> new DayReport(item.getAddedDate().toString().split(" ")[0], BigDecimal.valueOf(item.getPrice())))
                .collect(
                        Collectors.toMap(
                                DayReport::getMonth,
                                Function.identity(),
                                (sum1, sum2) -> new DayReport(
                                        sum1.getDay(),
                                        sum1.getAmount().add(sum2.getAmount())
                                )
                        )
                )
                .values()
                .stream()
                .sorted(Comparator.comparing(DayReport::getDay))
                .collect(Collectors.toList());
    }
}
