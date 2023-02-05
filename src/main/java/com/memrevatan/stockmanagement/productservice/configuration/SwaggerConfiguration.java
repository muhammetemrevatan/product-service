package com.memrevatan.stockmanagement.productservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // Class icersiinde herhangi bir configurasyon oldugunu belirter. Spring icin buranin bir config oldugunu soyler.
@EnableSwagger2 // Bu class icerisinde Swagger'in aktiflerstirilmesi icin kullanılan bir annotation'dir. Configuration oldugu icin proje basladiginda bir kere calisir.
public class SwaggerConfiguration {

    @Bean // Singleton olarak basladigin bir kere instance ataması yapar. Bir kere olusturur hep aynı referansı geriye döner.
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2) //Docket documantation plugini implement eder.
                .select() // swagger tarafından sunulan endpointleri kontrol etmenin yolunu sagalyan api selecter builder instance ını return eder.
                .apis(RequestHandlerSelectors.basePackage("com.memrevatan")) // com.memrevatan altında buşunan tüm apileri ara demek.
                .paths(PathSelectors.any()) // Tüm api docs ları olustur demek.
                .build();
    }
}
