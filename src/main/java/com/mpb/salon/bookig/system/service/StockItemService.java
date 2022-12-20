package com.mpb.salon.bookig.system.service;


import com.mpb.salon.bookig.system.entity.StockItem;
import com.mpb.salon.bookig.system.service.dao.DayReport;
import com.mpb.salon.bookig.system.web.req.StockItemSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface StockItemService extends CRUDServices<StockItem>{
    Page<StockItem> search(StockItemSearch stockItemSearch, Pageable pageable);
    List<StockItem> getBetween(Date date1, Date date2);
    List<DayReport>  monthlyStockAddingCost(Date from, Date to);
}
