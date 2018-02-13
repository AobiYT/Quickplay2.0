package co.bugg.quickplay.client.gui;

import co.bugg.quickplay.Quickplay;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class InstanceDisplay extends MoveableHudElement {

    int backgroundHorizontalPadding = 4;
    int backgroungVerticalPadding = 3;

    public InstanceDisplay() {
        super();
    }

    @Override
    public void render(double x, double y, double opacity) {
        super.render(x, y, opacity);

        String instance = Quickplay.INSTANCE.instanceWatcher.getCurrentServer();
        int stringHeight = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
        int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(instance);

        int scaledX = (int) (x * screenWidth);
        int scaledY = (int) (y * screenHeight);

        drawRect(scaledX - this.backgroundHorizontalPadding - stringWidth / 2,
                scaledY - this.backgroungVerticalPadding,
                scaledX + stringWidth + this.backgroundHorizontalPadding - stringWidth / 2 - 1, // -1 due to a padding issue I don't
                                                                                                      // understand it but it's uneven without.
                scaledY + stringHeight + this.backgroungVerticalPadding,
                0x000000 | (int) (opacity * 100 * 0.5) << 24);
        GL11.glEnable(GL11.GL_BLEND);

        drawCenteredString(Minecraft.getMinecraft().fontRendererObj, instance, scaledX, scaledY, Quickplay.INSTANCE.settings.primaryColor.getColor().getRGB() & 0xFFFFFF | (int) (opacity * 255) << 24);
    }

    @Override
    public void setxRatio(double xRatio) {
        Quickplay.INSTANCE.settings.instanceDisplayX = xRatio;
    }

    @Override
    public void setyRatio(double yRatio) {
        Quickplay.INSTANCE.settings.instanceDisplayY = yRatio;
    }

    @Override
    public double getxRatio() {
        if(Quickplay.INSTANCE.settings != null)
            return Quickplay.INSTANCE.settings.instanceDisplayX;
        else return 0.5;
    }

    @Override
    public double getyRatio() {
        if(Quickplay.INSTANCE.settings != null)
            return Quickplay.INSTANCE.settings.instanceDisplayY;
        else return 0.05;
    }

    @Override
    public void save() {
        try {
            Quickplay.INSTANCE.settings.save();
        } catch (IOException e) {
            System.out.println("Error saving config!");
            e.printStackTrace();
        }
    }
}
