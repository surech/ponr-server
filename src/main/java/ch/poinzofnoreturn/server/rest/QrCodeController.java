package ch.poinzofnoreturn.server.rest;

import ch.poinzofnoreturn.server.rest.model.QrCodeScanResult;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * REST-Controller für die Verarbeitung von QR-Codes
 */
@RestController
public class QrCodeController {

    private static final int SIZE = 300;

    @RequestMapping(path = "/qrcode/generate", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generate(@RequestParam(value="input") String input) throws WriterException, IOException {
//        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.MARGIN, 1);

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix byteMatrix = writer.encode(input, BarcodeFormat.QR_CODE, SIZE, SIZE, hintMap);

        int width = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, width);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    @RequestMapping(path = "/qrcode/scanner", method = RequestMethod.POST)
    public QrCodeScanResult scan(@RequestParam("qrcode") MultipartFile qrcode) throws IOException, NotFoundException {
        if (!qrcode.isEmpty()) {
            InputStream inputStream = qrcode.getInputStream();

            BufferedImage image = ImageIO.read(inputStream);
            BufferedImage resizedCopy = createResizedCopy(image, 1200, false);

            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(resizedCopy)));
            MultiFormatReader reader = new MultiFormatReader();
            Result content = reader.decode(bitmap);

            QrCodeScanResult result = new QrCodeScanResult(content.getText());
            return result;
        } else {
            throw new IllegalArgumentException("Empty File not accepted");
        }
    }

    BufferedImage createResizedCopy(BufferedImage originalImage,
                                    int scaledWidth,
                                    boolean preserveAlpha)
    {
        int scaledHeight = originalImage.getHeight() * scaledWidth / originalImage.getWidth();
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

}
