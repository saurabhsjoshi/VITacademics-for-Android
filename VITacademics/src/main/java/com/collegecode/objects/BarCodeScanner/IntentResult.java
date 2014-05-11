package com.collegecode.objects.BarCodeScanner;

/**
 * @author Jim.H
 */
public final class IntentResult {

    private final String contents;
    private final String formatName;

    IntentResult(String contents, String formatName) {
        this.contents = contents;
        this.formatName = formatName;
    }

    public String getContents() {
        return contents;
    }

    public String getFormatName() {
        return formatName;
    }

    @Override
    public String toString() {
        return "IntentResult [contents=" + contents + ", formatName=" + formatName + "]";
    }
}
