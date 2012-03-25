package org.sinner.study.view;

import java.util.Map;

import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class NormalJsonView extends MappingJacksonJsonView {

	@Override
	protected Object filterModel(Map<String, Object> model) {    
	    Map<?, ?> result = (Map<?, ?>) super.filterModel(model);    
	    if (result.size() == 1) {    
	        return result.values().iterator().next();    
	    } else {    
	        return result;    
	    }    
	}
}
