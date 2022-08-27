package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class ListUserController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);

	@Override
	void doGet(HttpRequest request, HttpResponse response) throws Exception {
		byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");

		if (request.getCookie().get("logined").equals("true")) {
			TemplateLoader loader = new ClassPathTemplateLoader();
			loader.setPrefix("/templates");
			loader.setSuffix(".html");
			Handlebars handlebars = new Handlebars(loader);

			Template template = handlebars.compile("user/profile");

			User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
			String profilePage = template.apply(user);
			logger.debug("ProfilePage : {}", profilePage);

			body = profilePage.getBytes();
		}
		response.response200Header(body.length);
		response.responseBody(body);
	}
}