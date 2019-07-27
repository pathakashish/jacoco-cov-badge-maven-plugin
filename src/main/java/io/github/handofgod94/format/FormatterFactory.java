package io.github.handofgod94.format;

import org.apache.commons.lang3.NotImplementedException;

/**
 * Factory class to generate formatter based on configuration i.e. (jpg, png, svg, etc).
 */
public class FormatterFactory {

  public static final String JPEG_EXT = "jpg";
  public static final String PNG_EXT = "png";
  public static final String SVG_EXT = "svg";

  public static Formatter createFormatter(String ext) {

    if (ext.equals(JPEG_EXT)) {
      return new JpegFormatter();
    } else if (ext.equals(PNG_EXT)) {
      return new PngFormatter();
    } else if (ext.equals(SVG_EXT)) {
      return new SvgFormatter();
    } else {
      throw new NotImplementedException(String.format("Format: %s is not yet supported", ext));
    }
  }
}