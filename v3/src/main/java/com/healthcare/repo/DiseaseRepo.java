package com.healthcare.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.healthcare.domain.Disease;
//@RepositoryRestResource(collectionResourceRel="people", path = "people")
@Repository
@CrossOrigin
public interface DiseaseRepo extends JpaRepository<Disease, Integer> {
	//List<Disease> findByName(@Param("name") String name);
}
