package net.evecom.utils.request;

import net.evecom.tools.constant.consts.CacheGroupConst;
import net.evecom.utils.string.RandomUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @ClassName: VerifyCodeUtils
 * @Description: 验证码组件
 * @author： zhengc
 * @date： 2014年3月19日
 */
public class VerifyCodeUtils {

	public static final int CHAR_LENGTH = 4;

	public static final int IMG_WIDTH = 120;
	public static final int IMG_HEIGHT = 30;

	public static final int COLOR_BG_FC = 200;
	public static final int COLOR_BG_BC = 250;
	public static final int COLOR_LINE_FC = 160;
	public static final int COLOR_LINE_BC = 200;
	public static final int COLOR_CODE_FC = 20;
	public static final int COLOR_CODE_BC = 170;

	/**
	 * 验证码图片
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void image(HttpServletRequest request, HttpServletResponse response)
			throws IOException, FontFormatException {
		BufferedImage bufferedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics graphices = bufferedImage.getGraphics();
		graphices.setColor(getRandColor(COLOR_BG_FC, COLOR_BG_BC));
		graphices.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
		Font font = new Font("宋体", Font.BOLD, 25);
		graphices.setFont(font);
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			graphices.setColor(getRandColor(COLOR_LINE_FC, COLOR_LINE_BC));
			graphices.drawLine(r.nextInt(IMG_WIDTH), r.nextInt(IMG_HEIGHT), r.nextInt(IMG_WIDTH),
					r.nextInt(IMG_HEIGHT));
		}

		StringBuffer sb = new StringBuffer();
		int offset = IMG_WIDTH / CHAR_LENGTH;
		for (int i = 0; i < CHAR_LENGTH; i++) {
			graphices.setColor(getRandColor(COLOR_CODE_FC, COLOR_CODE_BC));
			String str = RandomUtil.randomString("0123456789", 1);
			sb.append(str);
			graphices.drawString(str, i * offset + 10, 23);
		}

		request.getSession().setAttribute(CacheGroupConst.CHECK_CODE_NAME, sb.toString());
		response.reset();
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		graphices.dispose();
		ImageIO.write(bufferedImage, "JPG", response.getOutputStream());
	}

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		fc = fc < 0 ? 0 : fc;
		fc = fc > 255 ? 255 : fc;
		bc = bc < 0 ? 1 : bc;
		bc = bc > 255 ? 255 : bc;
		bc = bc == fc ? bc + 10 : bc;

		int temp;
		if (bc < fc) {
			temp = bc;
			bc = fc;
			fc = temp;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
