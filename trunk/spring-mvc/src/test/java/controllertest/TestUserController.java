package controllertest;
import java.util.Collections;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.*;


public class TestUserController {
	@Test
	public void testJson(){
		RestTemplate t = buildTemplate();
		User u = new User();
		u.setName("a");
		u.setPassword("a");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/xml;UTF-8"));
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<User> requestEntity = new HttpEntity<User>(u,headers);
		
		ResponseEntity<User> response = t.exchange("http://localhost:8080/spring-mvc/user/testJson.html", HttpMethod.POST, requestEntity, User.class);
		User responseUser = response.getBody();
		assertNotNull(responseUser);
		assertEquals("1", responseUser.getId());
	}
	
	private RestTemplate buildTemplate(){
		
		RestTemplate t = new RestTemplate();
		MappingJacksonHttpMessageConverter c = new MappingJacksonHttpMessageConverter();
		t.getMessageConverters().add(c);
		return t;
	}
}
