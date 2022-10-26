package com.dss.rest.entity;

import com.dss.rest.entity.types.Category;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

//@Entity
//@Table(schema="movs", name="movie_category")
public class MovieCategory {

  //  @ElementCollection(targetClass = com.dss.rest.entity.types.Category.class)
    Set<Category> categories;
}
