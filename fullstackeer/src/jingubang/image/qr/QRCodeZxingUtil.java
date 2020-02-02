package jingubang.image.qr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeZxingUtil {
	// logo的width和height
	private static final int IMAGE_WIDTH = 100;
	private static final int IMAGE_HEIGHT = 100;
	private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
	private static final int FRAME_WIDTH = 2;
	private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

	/**
	 * 主方法
	 * @param content 需要转为二维码的内容
	 * @param width 宽度
	 * @param height 高度
	 * @param srcImagePath 源图片logo地址
	 * @param destImagePath 目标图片地址
	 */
	public static void encode(String content, int width, int height, String srcImagePath, String destImagePath) {
		try {
			//ImageIO.write(RenderedImage im, String formatName, File output)
			//genBarcode() 生成包含二维码的BufferedImage
			ImageIO.write(genBarcode(content, width, height, srcImagePath), "jpg", new File(destImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	
	public static void encode(String content, int width, int height, String destImagePath) {
		try {
			//ImageIO.write(RenderedImage im, String formatName, File output)
			//genBarcode() 生成包含二维码的BufferedImage
			ImageIO.write(genBarcode(content, width, height), "jpg", new File(destImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	
	private static BufferedImage genBarcode(String content, int width, int height)
			throws WriterException, IOException {
		
		Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
		hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 生成二维码
		BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hint);
		// 二维矩阵转为一维像素数组
		int[] pixels = new int[width * height];
		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {

					// 二维码颜色50 165 162
//					int num1 = (int) (50 - (50.0 - 13.0) / matrix.getHeight() * (y + 1));
//					int num2 = (int) (165 - (165.0 - 72.0) / matrix.getHeight() * (y + 1));
//					int num3 = (int) (162 - (162.0 - 107.0) / matrix.getHeight() * (y + 1));
					int num1 = 255;
					int num2 = (int) (150 - (150.0 - 1) / matrix.getHeight() * (y + 1));
					int num3 = 0;
					Color color = new Color(num1, num2, num3);
					int colorInt = color.getRGB();
					// 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
					pixels[y * width + x] = matrix.get(x, y) ? colorInt : 16777215;
			}
		}
					// 0x000000:0xffffff  16777215
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.getRaster().setDataElements(0, 0, width, height, pixels);
		return image;
	}
	

	private static BufferedImage genBarcode(String content, int width, int height, String srcImagePath)
			throws WriterException, IOException {
		//生成一个BufferedImage
		//scale方法用于生成一个logo的BufferedImage。传入的参数为logo地址，最终生成图像的宽，高。
		BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH, IMAGE_HEIGHT, true);
		int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
		for (int i = 0; i < scaleImage.getWidth(); i++) {
			for (int j = 0; j < scaleImage.getHeight(); j++) {
				srcPixels[i][j] = scaleImage.getRGB(i, j);
			}
		}
		Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
		hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 生成二维码
		BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hint);
		// 二维矩阵转为一维像素数组
		int halfW = matrix.getWidth() / 2;
		int halfH = matrix.getHeight() / 2;
		int[] pixels = new int[width * height];
		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				// 左上角颜色,根据自己需要调整颜色范围和颜色
				if (x > 0 && x < 170 && y > 0 && y < 170) {
					Color color = new Color(255, 215, 0);//231 144 56
					int colorInt = color.getRGB();
					pixels[y * width + x] = matrix.get(x, y) ? colorInt : 16777215;
				}
				// 读取图片
				else if (x > halfW - IMAGE_HALF_WIDTH && x < halfW + IMAGE_HALF_WIDTH && y > halfH - IMAGE_HALF_WIDTH
						&& y < halfH + IMAGE_HALF_WIDTH) {
					pixels[y * width + x] = srcPixels[x - halfW + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
				} 
				else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
						&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& y < halfH - IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH
								&& y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
					pixels[y * width + x] = 0xffffff;
					// 在图片logo四周形成边框
				} 
				else {
					// 二维码颜色50 165 162
//					int num1 = (int) (50 - (50.0 - 13.0) / matrix.getHeight() * (y + 1));
//					int num2 = (int) (165 - (165.0 - 72.0) / matrix.getHeight() * (y + 1));
//					int num3 = (int) (162 - (162.0 - 107.0) / matrix.getHeight() * (y + 1));
					int num1 = (int) (255 - (255.0 - 210.0) / matrix.getHeight() * (y + 1));
					int num2 = 0;
					int num3 = 0;
					Color color = new Color(num1, num2, num3);
					int colorInt = color.getRGB();
					// 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
					pixels[y * width + x] = matrix.get(x, y) ? colorInt : 16777215;
					// 0x000000:0xffffff  16777215
				}
			}
		}
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.getRaster().setDataElements(0, 0, width, height, pixels);
		return image;
	}

	private static BufferedImage scale(String srcImageFile, int height, int width, boolean hasFiller)
			throws IOException {
		double ratio = 0.0; // 缩放比例
		File file = new File(srcImageFile);
		//获得一个logo的输入流
		BufferedImage srcImage = ImageIO.read(file);
		//destImage是最终要输出的logo
		Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		// 计算比例
		if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
			if (srcImage.getHeight() > srcImage.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / srcImage.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / srcImage.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage = op.filter(srcImage, null);
		}
		if (hasFiller) {
			// 补白
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = image.createGraphics();
			graphic.setColor(Color.white);
			graphic.fillRect(0, 0, width, height);
			if (width == destImage.getWidth(null))
				graphic.drawImage(destImage, 0, (height - destImage.getHeight(null)) / 2, destImage.getWidth(null),
						destImage.getHeight(null), Color.white, null);
			else
				graphic.drawImage(destImage, (width - destImage.getWidth(null)) / 2, 0, destImage.getWidth(null),
						destImage.getHeight(null), Color.white, null);
			graphic.dispose();
			destImage = image;
		}
		return (BufferedImage) destImage;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// 依次为内容(不支持中文),宽,长,中间图标路径,储存路径
		QRCodeZxingUtil.encode("http://www.baidu.com/", 1000, 1000, "E:\\Data\\RunningSave\\wukonglai-logo.jpg", "E:\\Data\\RunningSave\\desk_zxing11.jpg");

	}
}