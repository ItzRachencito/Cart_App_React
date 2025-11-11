package in.bushansirgur.billingsoftware.controller;


import in.bushansirgur.billingsoftware.entity.ItemEntity;
import in.bushansirgur.billingsoftware.entity.StockMovement;
import in.bushansirgur.billingsoftware.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stock")
@CrossOrigin(origins = "http://localhost:5173")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * Aumentar stock (entrada de mercancía)
     */
    @PostMapping("/increase/{productId}")
    public ResponseEntity<Map<String, Object>> increaseStock(
            @PathVariable Long item_id,
            @RequestParam Integer quantity,
            @RequestParam String reason,
            @RequestParam(required = false, defaultValue = "System") String userName) {

        try {
            stockService.increaseStock(item_id, quantity, reason, userName);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Stock aumentado correctamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al aumentar stock: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Ajustar stock manualmente
     */
    @PutMapping("/adjust/{itemId}")
    public ResponseEntity<Map<String, Object>> adjustStock(
            @PathVariable Long item_id,
            @RequestParam Integer newStock,
            @RequestParam String reason,
            @RequestParam(required = false, defaultValue = "System") String userName) {

        try {
            stockService.adjustStock(item_id, newStock, reason, userName);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Stock ajustado correctamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al ajustar stock: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Obtener historial de movimientos de un producto
     */
    @GetMapping("/movements/{productId}")
    public ResponseEntity<List<StockMovement>> getProductMovements(@PathVariable Long
                                                                           productId) {
        List<StockMovement> tbl_stock_movements = stockService.getProductMovements(productId);
        return ResponseEntity.ok(tbl_stock_movements);
    }

    /**
     * Obtener movimientos recientes
     */
    @GetMapping("/movements/recent")
    public ResponseEntity<List<StockMovement>> getRecentMovements() {
        List<StockMovement> tbl_stock_movements = stockService.getRecentMovements();
        return ResponseEntity.ok(tbl_stock_movements);
    }

    /**
     * Obtener productos con stock bajo
     */
    @GetMapping("/low-stock")
    public ResponseEntity<List<ItemEntity>> getLowStockProducts() {
        List<ItemEntity> tbl_items = stockService.getLowStockProducts();
        return ResponseEntity.ok(tbl_items);
    }

    /**
     * Obtener productos sin stock
     */
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<ItemEntity>> getOutOfStockProducts() {
        List<ItemEntity> tbl_items = stockService.getOutOfStockProducts();
        return ResponseEntity.ok(tbl_items);
    }

    /**
     * Dashboard de stock
     */
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getStockDashboard() {
        Map<String, Object> dashboard = stockService.getStockDashboard();
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Verificar disponibilidad de stock
     */
    @GetMapping("/check/{itemId}")
    public ResponseEntity<Map<String, Object>> checkStock(
            @PathVariable Long item_id,
            @RequestParam Integer quantity) {

        Map<String, Object> response = new HashMap<>();
        boolean available = stockService.hasEnoughStock(item_id, quantity);

        response.put("available", available);
        response.put("productId", item_id);
        response.put("requestedQuantity", quantity);

        return ResponseEntity.ok(response);
    }

}
