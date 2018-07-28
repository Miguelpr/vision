package vision;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PerformanceTest {
	public static BufferedImage load(String source) {

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(source));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static void save(BufferedImage img, String name) {

		try {
			// retrieve image

			File outputfile = new File(name);
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {

		BufferedImage hugeImage = load("1.jpg");
		/*
		 * System.out.println("Testing convertTo2DUsingGetRGB:"); for (int i =
		 * 0; i < 10; i++) { long startTime = System.nanoTime(); int[][] result
		 * = convertTo2DUsingGetRGB(hugeImage); long endTime =
		 * System.nanoTime(); System.out.println(String.format("%-2d: %s", (i +
		 * 1), toString(endTime - startTime))); }
		 */

		System.out.println("");

		System.out.println("Testing convertTo2DWithoutUsingGetRGB:");
		int[][] result = null;
		for (int i = 0; i < 10; i++) {
			long startTime = System.nanoTime();
			result = convertTo2DWithoutUsingGetRGB(hugeImage);
			long endTime = System.nanoTime();
			System.out.println(String.format("%-2d: %s", (i + 1), toString(endTime - startTime)));
		}

		saveToImageUsingGetRGB(result);

	}

	private static void saveToImageUsingGetRGB(int[][] image) {
		BufferedImage img2 = load("result3.png");
		int width = image.length;
		int height = image[0].length;
		System.out.println(width);
		System.out.println(height);
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				img2.setRGB(row, col, image[col][row]);
			}
		}

		save(img2, "result3.png");
	}

	private static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] result = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				result[row][col] = image.getRGB(col, row);
			}
		}

		return result;
	}

	private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {
		/*
		 * ##0 0## ##0 0## ##0 0##
		 * 
		 * 
		 * 
		 * ### #10 000
		 * 
		 * 
		 */
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		// final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] result = new int[height][width];
		// if (hasAlphaChannel) {
		final int pixelLength = 3;

		final int red = ((127 & 0xff) << 16); // red
		final int green = ((127 & 0xff) << 8); // green

		for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {

			int tot = (int) pixels[pixel];
			// int argb = (tot & 0xff); // blue
			// argb += ((tot & 0xff) << 8); // green
			// argb += ((tot & 0xff) << 16); // red
			if (tot > 0) {
				result[row][col] = 127;
			} else {
				result[row][col] = 0;
			}

			if (row > 2 && col > 0 && row < height - 1 && col < width - 1) {
				// if (row % 3 == 0 && (col - 1) % 3 == 0) {
				// pivote
				if (result[row - 2][col] > 0) {
					// 1
					if (result[row - 3][col - 1] == 0) {
						// 3 4 5 7 8 12
						if (result[row - 3][col] == 0) {
							// 5 7 8 12
							if (result[row - 3][col + 1] == 0) {
								if (result[row - 2][col - 1] == 0) {
									if (result[row - 2][col + 1] > 0) {
										if (result[row - 1][col - 1] == 0 && result[row - 1][col] > 0
												&& result[row - 1][col + 1] > 0) {
											// 7
											result[row - 2][col] = red; // red
										}
									}
								} else {
									if (result[row - 2][col + 1] > 0) {
										if (result[row - 1][col - 1] == 0) {

										} else {
											if (result[row - 1][col] > 0 && result[row - 1][col + 1] > 0) {
												// 8
												result[row - 2][col] = red; // red
											}
										}
									} else {
										if (result[row - 1][col - 1] > 0 && result[row - 1][col] > 0
												&& result[row - 1][col + 1] == 0) {
											// 5
											result[row - 2][col] = red; // red
										}
									}
								}
							}else{
								if (result[row - 2][col - 1] == 0 && result[row - 2][col +1] > 0 && result[row - 1][col - 1] > 0 && result[row - 1][col] > 0
										&& result[row - 1][col + 1] > 0) {
									// 12
									result[row - 2][col] = red; // red
								}
							}
						} else {
							// 3 4
							if (result[row - 3][col + 1] > 0 && result[row - 2][col - 1] == 0
									&& result[row - 2][col + 1] > 0 && result[row - 1][col - 1] == 0) {
								if (result[row - 1][col] == 0) {
									if (result[row - 1][col + 1] == 0) {
										result[row - 2][col] = red; // red
										// 3
										// System.out.println("3");
									}
								} else {
									if (result[row - 1][col + 1] > 0) {
										// 4
										result[row - 2][col] = red; // red
										// System.out.println("4");
									}
								}
							}
						}
					} else {
						// 1 2 6 9 10 11
						if (result[row - 3][col] > 0) {
							// 1 2 10 11
							if (result[row - 3][col + 1] == 0) {
								// 1 2
								if (result[row - 2][col - 1] > 0 && result[row - 2][col + 1] == 0
										&& result[row - 1][col + 1] == 0) {
									if (result[row - 1][col - 1] > 0) {
										if (result[row - 1][col] > 0) {
											// 2
											result[row - 2][col] = red; // red
											// System.out.println("2");
										}
									} else {
										if (result[row - 1][col] == 0) {
											// 1
											result[row - 2][col] = red; // red
											// System.out.println("1");
										}
									}
								}
							} else {
								
								// 6 10 11
								if (result[row - 2][col - 1] > 0) {
									if (result[row - 2][col + 1] > 0) {
										if (result[row - 1][col - 1] == 0 && result[row - 1][col] == 0
												&& result[row - 1][col + 1] == 0) {
											// 6
											result[row - 2][col] = red; // red
											// System.out.println("6");
										}
									} else {
										// 10
										if (result[row - 1][col - 1] > 0 && result[row - 1][col] == 0
												&& result[row - 1][col + 1] == 0) {
											result[row - 2][col] = red; // red
										}
									}
								}else{
									//11
									if (result[row - 2][col + 1] > 0 && result[row - 1][col - 1] == 0 && result[row - 1][col] == 0
											&& result[row - 1][col + 1] > 0) {
										// 11
										result[row - 2][col] = red; // red

									}
								}
							}
						}else{
							if (result[row - 3][col + 1] == 0 && result[row - 2][col - 1] > 0 && result[row - 2][col + 1] == 0 && result[row - 1][col - 1] > 0 && result[row - 1][col] > 0
									&& result[row - 1][col + 1] > 0) {
								// 9
								result[row - 2][col] = red; // red
								// System.out.println("6");
							}
						}
					}
				}
			}

			col++;
			if (col == width) {
				col = 0;
				row++;
			}
		}

		return result;
	}

	private static String toString(long nanoSecs) {
		int minutes = (int) (nanoSecs / 60000000000.0);
		int seconds = (int) (nanoSecs / 1000000000.0) - (minutes * 60);
		int millisecs = (int) (((nanoSecs / 1000000000.0) - (seconds + minutes * 60)) * 1000);

		if (minutes == 0 && seconds == 0)
			return millisecs + "ms";
		else if (minutes == 0 && millisecs == 0)
			return seconds + "s";
		else if (seconds == 0 && millisecs == 0)
			return minutes + "min";
		else if (minutes == 0)
			return seconds + "s " + millisecs + "ms";
		else if (seconds == 0)
			return minutes + "min " + millisecs + "ms";
		else if (millisecs == 0)
			return minutes + "min " + seconds + "s";

		return minutes + "min " + seconds + "s " + millisecs + "ms";
	}
}