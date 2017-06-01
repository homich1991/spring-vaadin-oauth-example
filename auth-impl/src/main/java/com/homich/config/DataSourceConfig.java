package com.homich.config;

import com.homich.entities.Model;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan(basePackageClasses = {Model.class, Jsr310JpaConverters.class})
public class DataSourceConfig {

}
