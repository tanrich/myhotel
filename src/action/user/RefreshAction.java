package action.user;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

import entity.Order;
import entity.User;
import service.OrderService;

public class RefreshAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderService orderService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void refresh() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject JSON_Object = new JSONObject();
		User user = (User) session.getAttribute("user");
		List<Order> order = orderService.findAllOrders();
		PrintWriter out = response.getWriter();
		int status;
		JSONArray Json_array = new JSONArray();
		System.out.println(user.getName());
		Order o = new Order();
		Iterator<Order> it = order.iterator();
		while (it.hasNext()) {

			o = it.next();
			if (o.getName().equals(user.getName())) {

				status = 1;
				JSON_Object.put("status", status);
				Json_array.add(JSON_Object);
				JSON_Object = new JSONObject();
				{
					JSON_Object.put("orderId", o.getOrderId());
					JSON_Object.put("name", o.getName());
					JSON_Object.put("roomId", o.getRoomId());
					JSON_Object.put("startDay", o.getStart_day());
					JSON_Object.put("finalDay", o.getFinal_day());
					JSON_Object.put("totalMoney", o.getTotalMoney());
				}
				Json_array.add(JSON_Object);
				JSON_Object = new JSONObject();
				{
					JSON_Object.put("username", user.getUsername());
					JSON_Object.put("money", user.getMoney());
					
				}
				Json_array.add(JSON_Object);
				JSON_Object = new JSONObject();

				JSON_Object.put("content", Json_array.toJSONString());
				out.write(JSON_Object.toJSONString());
				out.flush();
				out.close();

			}
		}
		status = 0;
		JSON_Object.put("status", status);
		out.write(JSON_Object.toString());
		out.flush();
		out.close();

	}

}
