package lambda.macrocalculator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{

    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
    {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        // http.anonymous().disable();
        // remember that there is also security set in the UserController!
        http.authorizeRequests()
            .antMatchers("/",                       // h2
                                   "/h2-console/**",          // h2
                                   "/v2/api-docs",            // swagger
                                   "/swagger-resources",      // swagger
                                   "/swagger-resources/**",   // swagger
                                   "/configuration/ui",       // swagger
                                   "/configuration/security", // swagger
                                   "/swagger-ui.html",        // swagger
                                   "/webjars/**"              // swagger
                        ).permitAll()
                // hasAnyRole can be a list of roles as in "ADMIN", "DATA"
                .antMatchers("/roles", "/actuator/**", "/users/**").hasAnyRole("ADMINS")
                .antMatchers("/books/**", "/authors/**").hasAnyRole("USER", "ADMINS", "DATA")
                .antMatchers("/data/**").hasAnyRole("ADMINS", "DATA")
//                .antMatchers("/**").authenticated()
            .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());

        // http.requiresChannel().anyRequest().requiresSecure();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
