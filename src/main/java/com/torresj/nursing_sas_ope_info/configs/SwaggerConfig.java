package com.torresj.nursing_sas_ope_info.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.AllArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SwaggerConfig {

  @Value("${info.app.version}")
  private final String version;

  @Bean
  public OpenAPI springOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Nursing SAS Ope info API")
                .description("Nursing SAS Ope info API")
                .version(version)
                .license(
                    new License()
                        .name("GNU General Public License V3.0")
                        .url("https://www.gnu.org/licenses/gpl-3.0.html")));
  }
}