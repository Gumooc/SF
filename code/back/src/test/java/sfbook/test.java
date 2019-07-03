package sfbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.run.entity.User;
import com.run.service.EmailService;
import com.run.service.UserService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/test")
public class test {
	
	private void setRHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
	}
	
	@RequestMapping("/echo")
	public String echo(@RequestBody String liString,HttpServletRequest request, HttpServletResponse response) {
		setRHeader(request, response);
		return liString;
	}
}
