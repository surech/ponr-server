package ch.poinzofnoreturn.server.rest.model;

/**
 * Enthält den Inhalt eines QR-Codes
 */
public class QrCodeScanResult {
    private final String content;

    public QrCodeScanResult(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
