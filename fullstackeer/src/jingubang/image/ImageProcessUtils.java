/**
 * 包打听全知道-微信H5版本
 * jingubang.image
 * ImageUtils.java
 * Ver0.0.1
 * 2017年2月23日-下午5:14:30
 * 2017全智道(北京)科技有限公司-版权所有
 * 
 */
package jingubang.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


/**
 * 
 * ImageUtils
 * 
 * 李华栋
 * 李华栋
 * 2017年2月23日 下午5:14:30
 * 
 * @version 0.0.1
 * 
 */
public class ImageProcessUtils {

	
	/**
	 * 剪切图片。中间有空隙。
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param descImageFile
	 *            切片目标文件夹
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度
	 */
	public static List cutImgWell(String srcImageFile, String descImageFile, int width, String date, double ratio) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			
			System.out.println("width: "+width);
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			// 源图宽度
			int srcWidth = bi.getWidth();
			// 源图高度
			int srcHeight = bi.getHeight();
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			int rows = 3;
			int cols = 3;
			
			int midWidth = (int) (width / ratio);
			System.out.println("midWidth: "+midWidth);
			
			int destWidth = (width - midWidth*2)/3;
			System.out.println("destWidth: "+destWidth);
			
			Image img;
			ImageFilter cropFilter;
			// 循环建立切片
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					// 四个参数分别为图像起点坐标和宽高
					cropFilter = new CropImageFilter(j * (destWidth+midWidth), i * (destWidth+midWidth), destWidth,destWidth);
					img = Toolkit.getDefaultToolkit()
							.createImage(new FilteredImageSource(image.getSource(), cropFilter));
					BufferedImage tag = new BufferedImage(destWidth, destWidth, BufferedImage.TYPE_INT_RGB);
					Graphics g = tag.getGraphics();
					g.drawImage(img, 0, 0, null); // 绘制缩小后的图
					g.dispose();
					// 输出为文件
					ImageIO.write(tag, "JPEG", new File(descImageFile + "/fin_" + date + i + "_" + j + ".jpg"));

					list.add("fin_" + date + i + "_" + j + ".jpg");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 剪切图片
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param descImageFile
	 *            切片目标文件夹
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度
	 */
	public static List cutImg(String srcImageFile, String descImageFile, int destWidth, int destHeight, String date) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			// 源图宽度
			int srcWidth = bi.getWidth();
			// 源图高度
			int srcHeight = bi.getHeight();
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				// 切片横向数量
				int cols = 0;
				// 切片纵向数量
				int rows = 0;
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				Image img;
				ImageFilter cropFilter;
				// 循环建立切片
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						cropFilter = new CropImageFilter(j * destWidth, i * destHeight, (j + 1) * destWidth,
								(i + 1) * destHeight);
						img = Toolkit.getDefaultToolkit()
								.createImage(new FilteredImageSource(image.getSource(), cropFilter));
						BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descImageFile + "/fin_" + date + i + "_" + j + ".jpg"));

						list.add("fin_" + date + i + "_" + j + ".jpg");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 剪切图片
	 * 
	 * @param srcImageFile
	 * @param descImageFile
	 * @param destWidth
	 * @param destHeight
	 */
	public static void cutImg(String srcImageFile, String descImageFile, int destWidth, int destHeight) {

		try {
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			// 源图宽度
			int srcWidth = bi.getWidth();
			// 源图高度
			int srcHeight = bi.getHeight();
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				// 切片横向数量
				int cols = 0;
				// 切片纵向数量
				int rows = 0;
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				Image img;
				ImageFilter cropFilter;
				// 循环建立切片
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						cropFilter = new CropImageFilter(j * destWidth, i * destHeight, (j + 1) * destWidth,
								(i + 1) * destHeight);
						img = Toolkit.getDefaultToolkit()
								.createImage(new FilteredImageSource(image.getSource(), cropFilter));
						BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descImageFile + "/pre_" + i + "_" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取图像宽度与高度
	 * 
	 * @param imgpaht
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Integer> getImg(String imgpaht) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>(2);
		BufferedImage bi = ImageIO.read(new File(imgpaht));
		// 源图宽度
		int srcWidth = bi.getWidth();
		// 源图高度
		int srcHeight = bi.getHeight();
		map.put("w", srcWidth);
		map.put("h", srcHeight);
		return map;
	}

	/**
	 * 将附加图片合并到底图的正中央
	 * 
	 * @param negativeImagePath
	 *            底图路径
	 * @param additionImagePath
	 *            附加图片路径
	 * @param toPath
	 *            合成图片写入路径
	 * @throws IOException
	 */
	public static void mergeBothImageCenter(String negativeImagePath, String additionImagePath, String toPath)
			throws IOException {
		InputStream is = null;
		InputStream is2 = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(negativeImagePath);
			is2 = new FileInputStream(additionImagePath);
			BufferedImage image = ImageIO.read(is);
			BufferedImage image2 = ImageIO.read(is2);
			Graphics g = image.getGraphics();
			g.drawImage(image2, image.getWidth() / 2 - image2.getWidth() / 2,
					image.getHeight() / 2 - image2.getHeight() / 2, null);
			os = new FileOutputStream(toPath);
			JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(os);
			enc.encode(image);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
			if (is2 != null) {
				is2.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 生成图像。此处生成一张空白图。
	 * 
	 * @param desc
	 * @param length
	 * @throws Exception
	 */
	public static void generateImg(String desc, int length) throws Exception {

		File file = new File(desc);
		// 创建一个画布
		BufferedImage bi = new BufferedImage(length, length, BufferedImage.TYPE_INT_RGB);
		// 获取画布的画笔
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, length, length);
		ImageIO.write(bi, "jpg", file);
	}





	/**
	 * 纵向拼接图片（两张）
	 * 
	 * @param firstSrcImagePath
	 *            读取的第一张图片
	 * @param secondSrcImagePath
	 *            读取的第二张图片
	 * @param imageFormat
	 *            图片写入格式
	 * @param toPath
	 *            图片写入路径
	 */
	public static void joinImagesVertical(String firstSrcImagePath, String secondSrcImagePath, String imageFormat,
			String toPath) {
		try {
			// 读取第一张图片
			File fileOne = new File(firstSrcImagePath);
			BufferedImage imageOne = ImageIO.read(fileOne);
			int width = imageOne.getWidth();// 图片宽度
			int height = imageOne.getHeight();// 图片高度
			// 从图片中读取RGB
			int[] imageArrayOne = new int[width * height];
			imageArrayOne = imageOne.getRGB(0, 0, width, height, imageArrayOne, 0, width);

			// 对第二张图片做相同的处理
			File fileTwo = new File(secondSrcImagePath);
			BufferedImage imageTwo = ImageIO.read(fileTwo);
			int width2 = imageTwo.getWidth();
			int height2 = imageTwo.getHeight();
			int[] ImageArrayTwo = new int[width2 * height2];
			ImageArrayTwo = imageTwo.getRGB(0, 0, width, height, ImageArrayTwo, 0, width);
			// ImageArrayTwo =
			// imageTwo.getRGB(0,0,width2,height2,ImageArrayTwo,0,width2);

			// 生成新图片
			// int width3 = (width>width2 || width==width2)?width:width2;
			BufferedImage imageNew = new BufferedImage(width, height * 2, BufferedImage.TYPE_INT_RGB);
			// BufferedImage imageNew = new
			// BufferedImage(width3,height+height2,BufferedImage.TYPE_INT_RGB);
			imageNew.setRGB(0, 0, width, height, imageArrayOne, 0, width);// 设置上半部分的RGB
			imageNew.setRGB(0, height, width, height, ImageArrayTwo, 0, width);// 设置下半部分的RGB
			// imageNew.setRGB(0,height,width2,height2,ImageArrayTwo,0,width2);//设置下半部分的RGB

			File outFile = new File(toPath);
			ImageIO.write(imageNew, imageFormat, outFile);// 写图片
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 纵向拼接一组（多张）图像
	 * 
	 * @param pics
	 *            将要拼接的图像数组
	 * @param type
	 *            写入图像类型
	 * @param dst_pic
	 *            写入图像路径
	 * @return
	 */
	public static boolean joinImageListVertical(String[] pics, String type, String dst_pic) {
		try {
			int len = pics.length;
			if (len < 1) {
				System.out.println("pics len < 1");
				return false;
			}
			File[] src = new File[len];
			BufferedImage[] images = new BufferedImage[len];
			int[][] imageArrays = new int[len][];
			for (int i = 0; i < len; i++) {
				// System.out.println(i);
				src[i] = new File(pics[i]);
				images[i] = ImageIO.read(src[i]);
				int width = images[i].getWidth();
				int height = images[i].getHeight();
				imageArrays[i] = new int[width * height];// 从图片中读取RGB
				imageArrays[i] = images[i].getRGB(0, 0, width, height, imageArrays[i], 0, width);
			}

			int dst_height = 0;
			int dst_width = images[0].getWidth();
			for (int i = 0; i < images.length; i++) {
				dst_width = dst_width > images[i].getWidth() ? dst_width : images[i].getWidth();
				dst_height += images[i].getHeight();
			}
			// System.out.println(dst_width);
			// System.out.println(dst_height);
			if (dst_height < 1) {
				System.out.println("dst_height < 1");
				return false;
			}
			/*
			 * 生成新图片
			 */
			BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);
			int height_i = 0;
			for (int i = 0; i < images.length; i++) {
				ImageNew.setRGB(0, height_i, dst_width, images[i].getHeight(), imageArrays[i], 0, dst_width);
				height_i += images[i].getHeight();
			}
			File outFile = new File(dst_pic);
			ImageIO.write(ImageNew, type, outFile);// 写图片
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 长高等比例缩小图片
	 * 
	 * @param srcImagePath
	 *            读取图片路径
	 * @param toImagePath
	 *            写入图片路径
	 * @param ratio
	 *            缩小比例
	 * @throws IOException
	 */
	public static void reduceImageEqualProportion(String srcImagePath, String toImagePath, int ratio)
			throws IOException {
		FileOutputStream out = null;
		try {
			// 读入文件
			File file = new File(srcImagePath);
			// 构造Image对象
			BufferedImage src = javax.imageio.ImageIO.read(file);
			int width = src.getWidth();
			int height = src.getHeight();
			// 缩小边长
			BufferedImage tag = new BufferedImage(width / ratio, height / ratio, BufferedImage.TYPE_INT_RGB);
			// 绘制 缩小 后的图片
			tag.getGraphics().drawImage(src, 0, 0, width / ratio, height / ratio, null);
			out = new FileOutputStream(toImagePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 长高等比例放大图片
	 * 
	 * @param srcImagePath
	 *            读取图形路径
	 * @param toImagePath
	 *            写入入行路径
	 * @param ratio
	 *            放大比例
	 * @throws IOException
	 */
	public static void enlargementImageEqualProportion(String srcImagePath, String toImagePath, int ratio)
			throws IOException {
		FileOutputStream out = null;
		try {
			// 读入文件
			File file = new File(srcImagePath);
			// 构造Image对象
			BufferedImage src = javax.imageio.ImageIO.read(file);
			int width = src.getWidth();
			int height = src.getHeight();
			// 放大边长
			BufferedImage tag = new BufferedImage(width * ratio, height * ratio, BufferedImage.TYPE_INT_RGB);
			// 绘制放大后的图片
			tag.getGraphics().drawImage(src, 0, 0, width * ratio, height * ratio, null);
			out = new FileOutputStream(toImagePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 重置图形的边长大小
	 * 
	 * @param srcImagePath
	 * @param toImagePath
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void resizeImage(String srcImagePath, String toImagePath, int width, int height) throws IOException {
		FileOutputStream out = null;
		try {
			// 读入文件
			File file = new File(srcImagePath);
			// 构造Image对象
			BufferedImage src = javax.imageio.ImageIO.read(file);
			// 放大边长
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 绘制放大后的图片
			tag.getGraphics().drawImage(src, 0, 0, width, height, null);
			out = new FileOutputStream(toImagePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	
/*	public static String getLocalPath(String sourceUrl, String date, String sign) throws Exception{
		
		String localPath = null;
		String imageName = "";
		String newSourceUrl = "";
		
		
		if (sourceUrl.startsWith("http") || sourceUrl.startsWith("https")) {
			//imageName = date + "_" + sourceUrl.substring(sourceUrl.lastIndexOf("/") + 1);
			imageName = sign + date+".jpg";
			newSourceUrl = sourcePath + "/" + imageName;
			ImgUtil.getResourceFromUrl(sourceUrl, sourcePath, imageName);
			ImgUtil.resizeImage(newSourceUrl, newSourceUrl, 300, 300);
		} else {
			imageName = sourceUrl.substring(sourceUrl.lastIndexOf("/") + 1);
			imageName = sign + date + "_" + imageName; // 将读到的图片名加上时间戳
												// //201601011221_test.png
			newSourceUrl = sourcePath + "/" + imageName;
			File oldfile = new File(sourceUrl);
			File newfile = new File(newSourceUrl);
			oldfile.renameTo(newfile); // 给图片改名
			ImgUtil.resizeImage(newSourceUrl, newSourceUrl, 300, 300);
		}
		
		localPath = newSourceUrl;
		return localPath;
	}*/
	/**
	 * main(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param args 
	 *void
	 * @exception 
	 * @since  0.0.1
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
