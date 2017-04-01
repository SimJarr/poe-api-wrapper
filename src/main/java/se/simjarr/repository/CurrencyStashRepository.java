package se.simjarr.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.simjarr.model.CurrencyStash;

import java.util.List;

public interface CurrencyStashRepository extends PagingAndSortingRepository<CurrencyStash, Long> {

    CurrencyStash findByApiId(String apiId);

    List<CurrencyStash> findByUserUsername(String username);

}
