package se.simjarr.repository;

import org.springframework.data.repository.CrudRepository;
import se.simjarr.model.NextChangeId;

public interface NextChangeIdRepository extends CrudRepository<NextChangeId, String> {

}
