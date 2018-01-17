package com.netcracker.dao;

import com.netcracker.entity.PublishingHouse;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface PublishingHouseRepository extends CrudRepository<PublishingHouse, BigInteger>{
    PublishingHouse findByPublishingHouseInfo(String publishingHouseInfo);
}
