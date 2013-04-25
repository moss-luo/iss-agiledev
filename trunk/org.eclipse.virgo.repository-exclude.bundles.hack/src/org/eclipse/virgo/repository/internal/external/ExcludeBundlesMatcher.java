package org.eclipse.virgo.repository.internal.external;

import java.util.StringTokenizer;

public class ExcludeBundlesMatcher {
    private String[] excludePatterns = null;
    private AntPathMatcher matcher = new AntPathMatcher();
    
    public ExcludeBundlesMatcher() {
    	loadExcludePatterns();
    }
    
    private void loadExcludePatterns(){
    	loadExcludePatternsFromEnv();
    	if(excludePatterns == null){
    		setDefaultExcludePatterns();
    	}
    }

	private void setDefaultExcludePatterns() {
		excludePatterns = new String[] {
			System.getProperty("user.home") + "/.m2/repository/org/springframework/**/*",
			System.getProperty("user.home") + "/.m2/repository/org/slf4j/**/*"
		};
	}
    
    private void loadExcludePatternsFromEnv() {
		String vebp = getEnvVarible();
		if (vebp == null)
			return;
		
		StringTokenizer tokenizer = new StringTokenizer(vebp, ";");
		excludePatterns = new String[tokenizer.countTokens()];
		int current = 0;
    	while (tokenizer.hasMoreTokens()) {
			String p = replaceSystemProperties(tokenizer.nextToken());
			excludePatterns[current++] = p;
    	}
	}

	protected String getEnvVarible() {
		return System.getenv().get("VIRGO_EXCLUDE_BUNDLES");
	}

	private String replaceSystemProperties(String p){
		int placeholderStart = p.indexOf("${");
		if (placeholderStart == -1) {
			return p;
		}
		
		int placeholderEnd = p.indexOf('}');
		if (placeholderEnd == -1)
			return p;
		
		if (placeholderEnd < placeholderStart)
			return p;
		
		StringBuilder sb = new StringBuilder();
		sb.append(p.substring(0, placeholderStart));
		
		String sp = System.getProperty(p.substring(placeholderStart + 2, placeholderEnd));
		if (sp == null) {
			throw new RuntimeException(String.format("can't read system property[%s]",
				p.substring(placeholderStart + 2, placeholderEnd)));
		}
		sb.append(sp);
		sb.append(p.substring(placeholderEnd + 1));
		
		return replaceSystemProperties(sb.toString());
    }
	
	public boolean match(String path) {
    	for (String excludeBundlePattern : excludePatterns) {
    		if (matcher.doMatch(excludeBundlePattern, path, true))
    			return true;
    	}
    	
    	return false;
	}
}
