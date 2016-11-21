package action.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

import entity.User;
import service.UserService;

public class ImageUploadAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private UserService userService;

	// 用户上传的文件
	private File uploadFile;
	// 上传文件的文件名
	private String uploadFileFileName;
	// 上传文件的类型
	private String uploadFileContentType;

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String execute() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject JSON_Object = new JSONObject();
		User user = (User) session.getAttribute("user");
		PrintWriter out = response.getWriter();
		int status;
		JSONArray Json_array = new JSONArray();
		System.out.println(uploadFileFileName);
		String root = ServletActionContext.getRequest().getRealPath("/upload");
		if (uploadFile != null) {
			// 上传文件存放的目录
			try {
				InputStream is = new FileInputStream(uploadFile);
				// 创建一个文件，路径为root，文件名叫fileFileName

				uploadFileFileName = user.getUsername() + uploadFileFileName.substring(uploadFileFileName.lastIndexOf("."));

				File destFile = new File(root, uploadFileFileName);
				// 将文件输出到指定的目录

				System.out.println(destFile.getAbsolutePath());
				// 开始上传
				OutputStream os = new FileOutputStream(destFile);

				byte[] buffer = new byte[50000];

				System.out.println(uploadFileFileName);
				String image = "upload\\" + uploadFileFileName;
				int length = 0;
				// enctype="multipart/form-data"
				while (-1 != (length = is.read(buffer))) {
					os.write(buffer, 0, length);
					is.close();
					os.close();
				}
				user.setPhoto(image);
				this.userService.update(user);
				status = 1;
				JSON_Object.put("status", status);
				JSON_Object.put("src", image);
				Json_array.add(JSON_Object);
				JSON_Object = new JSONObject();
				JSON_Object.put("content", Json_array.toJSONString());
				out.write(JSON_Object.toJSONString());
				out.flush();
				out.close();
				
			} catch (IOException ex) {

				ex.printStackTrace();
			}

		} else {

			status = 0;
			JSON_Object.put("status", status);
			Json_array.add(JSON_Object);
			JSON_Object = new JSONObject();
			JSON_Object.put("content", Json_array.toJSONString());
			out.write(JSON_Object.toJSONString());
			out.flush();
			out.close();
			return INPUT;
		}

		return SUCCESS;
	}

}
