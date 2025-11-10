package in.bushansirgur.billingsoftware.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ... existing fields ...

    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    @Column(name = "min_stock", nullable = false)
    private Integer minStock = 10;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_status")
    private StockStatus stockStatus = StockStatus.IN_STOCK;

    // Getters y Setters
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
        updateStockStatus();
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
        updateStockStatus();
    }

    public StockStatus getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(StockStatus stockStatus) {
        this.stockStatus = stockStatus;
    }

    // Método para actualizar estado automáticamente
    private void updateStockStatus() {
        if (stock <= 0) {
            this.stockStatus = StockStatus.OUT_OF_STOCK;
        } else if (stock <= minStock) {
            this.stockStatus = StockStatus.LOW_STOCK;
        } else {
            this.stockStatus = StockStatus.IN_STOCK;
        }
    }

    // Método helper para reducir stock
    public boolean reduceStock(Integer quantity) {
        if (this.stock >= quantity) {
            this.stock -= quantity;
            updateStockStatus();
            return true;
        }
        return false;
    }

    // Método helper para aumentar stock
    public void increaseStock(Integer quantity) {
        this.stock += quantity;
        updateStockStatus();
    }

}



