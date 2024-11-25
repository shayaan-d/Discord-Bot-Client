import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public final class GG {
	public static Font ggSansReg;
	public static Font ggSansMed;
	public static Font ggSansSemiBold;
	public static Font ggSansBold;
	public static Class<Main> mainClass = Main.class;
	
	static {
		try {
			// font init
			ggSansReg = getFont("ggsans.ttf", 15.0f);
			ggSansMed = getFont("ggsansmedium.ttf", 18.0f);
			ggSansSemiBold = getFont("ggsanssemibold.ttf", 25.0f);
			ggSansBold = getFont("ggsansbold.ttf", 30.0f);
		} catch (FontFormatException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Font getFont(String name, float size) throws FontFormatException, IOException {
		return java.awt.Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(mainClass.getResourceAsStream(name))).deriveFont(size);
	}
}
