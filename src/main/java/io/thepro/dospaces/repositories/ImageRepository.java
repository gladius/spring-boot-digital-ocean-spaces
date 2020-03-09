package io.thepro.dospaces.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.thepro.dospaces.entities.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long>{

}
