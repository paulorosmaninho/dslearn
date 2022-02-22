package com.devsuperior.dslearnbds.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.devsuperior.dslearnbds.components.JwtTokenEnhancer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	
	@Value("${jwt.duration}")
	private Integer jwtDuration;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private JwtTokenStore tokenStore;

	@Autowired
	private AuthenticationManager autenticationManager;

	@Autowired
	private JwtTokenEnhancer jwtTokenEnhancer;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");

	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory().withClient(clientId) /* Nome da aplicação */
				.secret(passwordEncoder.encode(clientSecret)) /*Senha da aplicação criptografada*/
				.scopes("read", "write") /* Escopo de acesso. Leitura e Escrita */
				.authorizedGrantTypes("password", "refresh_token") /* Tipos de autorizações = password e refresh_token*/
				.accessTokenValiditySeconds(jwtDuration) /* Tempo de validade do token de autenticação em segundos */
				.refreshTokenValiditySeconds(jwtDuration); /* Tempo de validade do refresh token em segundos */
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		//Adicionando mais informações do usuário no token com token enhancer
		TokenEnhancerChain chain = new TokenEnhancerChain();
		
		//Passa lista de tokens
		chain.setTokenEnhancers(Arrays.asList(accessTokenConverter, jwtTokenEnhancer));
		
		
		endpoints.authenticationManager(autenticationManager).tokenStore(tokenStore)
				.accessTokenConverter(accessTokenConverter)
				//Adicionando mais informações do usuário com token enhancer
				.tokenEnhancer(chain)
				//Adicionado userDetailsService junto com inclusão do refresh_token
				.userDetailsService(userDetailsService);
	}

}
