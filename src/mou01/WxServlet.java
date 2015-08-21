package mou01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mou01.util.CheckUtil;

@WebServlet(name = "wxServlet", urlPatterns = "/wx1")
public class WxServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");

		String echostr = req.getParameter("echostr");

		PrintWriter pw = resp.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			pw.print(echostr);
		}

	}
}
