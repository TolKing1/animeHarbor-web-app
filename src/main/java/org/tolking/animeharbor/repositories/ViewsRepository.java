package org.tolking.animeharbor.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tolking.animeharbor.entities.Views;

@Repository
public interface ViewsRepository extends CrudRepository<Views, Long> {

}
