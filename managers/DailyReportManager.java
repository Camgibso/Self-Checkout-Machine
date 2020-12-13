package com.scs.managers;

import com.scs.models.Inventory;
import com.scs.models.Report;
import com.scs.subsystems.Reporter;

public class DailyReportManager {

    private Inventory inventory = new Inventory();

    private Reporter reporter = new Reporter();

    public DailyReportManager() {
    }

    public String printRevenueReport() {
        String reportText = reporter.printRevenue(inventory.orders);

        Report report = new Report(reportText);
        inventory.saveReport(report);

        return reportText;
    }

    public String printInventoryReport() {

        String inventoryReportText = reporter.printInventory(inventory.inventory);

        Report report = new Report(inventoryReportText);
        inventory.saveReport(report);

        return inventoryReportText;
    }
}
