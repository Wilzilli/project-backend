package com.raizesnordeste.project_backend.infrastructure;

import com.raizesnordeste.project_backend.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByPedidoId(Long pedidoId);
}
