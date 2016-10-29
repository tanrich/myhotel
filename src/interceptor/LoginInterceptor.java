package interceptor;

import java.io.PrintWriter;
import java.util.Map;

import javax.print.attribute.standard.MediaSize.Other;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import entity.User;

@SuppressWarnings("serial")
public class LoginInterceptor extends AbstractInterceptor {
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext context = invocation.getInvocationContext();
		HttpServletResponse responce = ServletActionContext.getResponse();
		Map session = context.getSession();
		User user = (User) session.get("user");
		JSONObject JSON_Object = new JSONObject();
		PrintWriter out = responce.getWriter();
		int status;
		if (user != null) {
			System.out.println(user.getUsername());
			status=1;
			JSON_Object.put("username", user.getUsername());
			JSON_Object.put("status", status);
			out.write(JSON_Object.toString());
			out.close();
			return invocation.invoke();
		}
		status=0;
		JSON_Object.put("status", status);
		out.write(JSON_Object.toString());
		out.close();
		System.out.println(user.getUsername());
		return Action.LOGIN;
	}

}