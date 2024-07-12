package dd.projects.ddshop.Repositories;

import dd.projects.ddshop.Entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<Address, Integer> {
}
