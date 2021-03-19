package com.ckgexample.miniboard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties( prefix = "application", ignoreUnknownFields = false )
@Data
public class ApplicationProperties {

	private final Datasource datasource = new Datasource();
	
	private final Security security = new Security();
	
	@Data
	public static class Datasource {
		
		private boolean cachePrepStmts = true;
		
		private int prepStmtCacheSize = 250;
		
		private int prepStmtCacheSqlLimit = 2048;
		
		private boolean useServerPrepStmts = true;
		
		public boolean isCachePrepStmts() {
			return cachePrepStmts;
		}
		
		public boolean isUseServerPrepStmts() {
			return useServerPrepStmts;
		}
		
	}
	
	@Data
	public static class Security {
		
		private final Jwt jwt = new Jwt();
		
		@Data
		public static class Jwt {
			
			private String secret;
			
			private long tokenValidityInSeconds = 1800;
			
			private long tokenValidityInSecondsForRememberMe = 2592000;
		}
	}
}
