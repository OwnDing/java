package springbootsecurityjdbc.books.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import springbootsecurityjdbc.books.web.LoggingAccessDeniedHandler;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/webjars/**").permitAll()
                //.antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Autowired
    @Qualifier("datasource")
    private DataSource dataSource;

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	/*auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
            .and()
                .withUser("manager").password("password").roles("MANAGER");*/
        auth.jdbcAuthentication().dataSource(dataSource)
                .authoritiesByUsernameQuery("select employeeName, employeeRole FROM employee where employeeName=?")
                .usersByUsernameQuery("select employeeName,employeePassword as password,1 FROM employee where employeeName=?");
    }

}
