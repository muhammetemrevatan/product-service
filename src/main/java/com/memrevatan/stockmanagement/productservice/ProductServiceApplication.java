package com.memrevatan.stockmanagement.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication // Bunun yerine asagidakileri kullanabilirdik.
//@ComponentScan // @Component ile arasındaki fark ne ?? service ile fark ne??
//@Configuration // Spring boot uygulamasına ait temel konfigürasyon tanımlarını içeren sınıfı belirtmek için kullanılır.
//@EnableAutoConfiguration // spring boot uygulamasın olusturulacak olan application context in oto config yapar.
public class ProductServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
