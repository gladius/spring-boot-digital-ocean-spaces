package io.thepro.dospaces.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import io.thepro.dospaces.entities.Image;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image, Long>{

}
