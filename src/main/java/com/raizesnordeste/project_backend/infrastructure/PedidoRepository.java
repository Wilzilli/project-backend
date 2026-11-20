package com.raizesnordeste.project_backend.infrastructure;

import com.raizesnordeste.project_backend.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCanalPedido(String canalPedido);
}