import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;

public class CertificateQRCodeGenerator {

	public static void generateQRCode(String data, String outputPath, int width, int height) {
	    try {
	        Map<EncodeHintType, Object> hints = new HashMap<>();
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

	        Path outputPathPath = FileSystems.getDefault().getPath(outputPath);

	        // Ensure the output directory exists
	        Files.createDirectories(outputPathPath.getParent());

	        // Check if the file exists, and if it does, delete it
	        if (Files.exists(outputPathPath)) {
	            Files.delete(outputPathPath);
	        }

	        // Convert BufferedImage to byte array
	        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(bufferedImage, "png", baos);
	        byte[] imageBytes = baos.toByteArray();

	        // Write the byte array to the file
	        Files.write(outputPathPath, imageBytes, StandardOpenOption.CREATE);

	        System.out.println("QR code saved to: " + outputPath);
	    } catch (WriterException | IOException e) {
	        e.printStackTrace();
	    }
	}


    public static void main(String[] args) {
        String certificateData = "String certificateData = \"Certificate ID: 12345\\n\"\r\n"
        		+ "                    + \"Recipient: John Doe\\n\"\r\n"
        		+ "                    + \"Type: Professional Certification\\n\"\r\n"
        		+ "                    + \"Issued on: 2023-09-05\\n\"\r\n"
        		+ "                    + \"Expires on: 2025-09-05\\n\"\r\n"
        		+ "                    + \"Issuer: Example Organization\\n\"\r\n"
        		+ "                    + \"Contact: example@email.com\";\r\n";
        		
        String outputPath = "C:/Users/HP/Downloads/output/certificate_qr.png"; // Change the output path as needed
        int width = 300;
        int height = 300;

        generateQRCode(certificateData, outputPath, width, height);
    }
}
