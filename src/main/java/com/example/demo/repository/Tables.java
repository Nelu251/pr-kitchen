package com.example.demo.repository;

import com.example.demo.domain.model.Table;
import com.example.demo.domain.model.enums.TableStatus;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Tables {
    private static List<Table> tables = new ArrayList<>();

    static {
        tables.add(new Table(1, TableStatus.WAITING_TO_MAKE_ORDER));
        tables.add(new Table(2, TableStatus.WAITING_TO_MAKE_ORDER));
        tables.add(new Table(3, TableStatus.WAITING_TO_MAKE_ORDER));
        tables.add(new Table(4, TableStatus.WAITING_TO_MAKE_ORDER));
        tables.add(new Table(5, TableStatus.WAITING_TO_MAKE_ORDER));
    }

    public Table findWaitingTable() {
        for (Table table: tables) {
            if (table.getStatus().equals(TableStatus.WAITING_TO_MAKE_ORDER)){
                return table;
            }
        }
        return null;
    }
}
