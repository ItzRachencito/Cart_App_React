package in.bushansirgur.billingsoftware.repository;

import in.bushansirgur.billingsoftware.entity.ItemEntity;
import in.bushansirgur.billingsoftware.entity.StockStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findByItemId(String id);

    Integer countByCategoryId(Long id);

    // Productos con stock bajo
    @Query("SELECT p FROM tbl_items p WHERE p.stock <= p.minStock AND p.stock > 0")
    List<ItemEntity> findLowStockProducts();

    // Productos sin stock
    @Query("SELECT p FROM tbl_items p WHERE p.stock = 0")
    List<ItemEntity> findOutOfStockProducts();

    // Productos por estado de stock
    List<ItemEntity> findByStockStatus(StockStatus stockStatus);

    // Contar productos por estado
    Long countByStockStatus(StockStatus stockStatus);

}
