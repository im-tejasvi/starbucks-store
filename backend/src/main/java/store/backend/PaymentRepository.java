package store.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository
  extends JpaRepository<PaymentsCommand, Long> {}
