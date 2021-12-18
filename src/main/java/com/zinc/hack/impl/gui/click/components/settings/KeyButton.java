package com.zinc.hack.impl.gui.click.components.settings;

import com.zinc.hack.Zinc;
import com.zinc.hack.api.font.FontUtil;
import com.zinc.hack.impl.gui.click.Component;
import com.zinc.hack.impl.gui.click.components.ModuleButton;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class KeyButton extends Component {

    private boolean binding;
    private final ModuleButton button;
    private boolean isHovered;
    private int offset;
    private int x;
    private int y;

    public KeyButton(final ModuleButton button, final int offset) {
        this.button = button;
        this.x = button.frame.getX() + button.frame.getWidth();
        this.y = button.frame.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
    }

    @Override
    public void updateComponent(final double mouseX, final double mouseY) {
        isHovered = isHovered(mouseX, mouseY);
        y = button.frame.getY() + this.offset;
        x = button.frame.getX();
    }

    @Override
    public void mouseClicked(final double mouseX, final double mouseY, final int button) {
        if (isHovered(mouseX, mouseY) && button == 0 && this.button.open) {
            binding = !binding;
        }
    }

    @Override
    public void keyTyped(final int key) {
        if (this.binding) {
            if (key == Keyboard.KEY_DELETE) button.module.setKey(-1);
            else button.module.setKey(key);
            this.binding = false;
        }
    }

    @Override
    public void render() {
        Zinc.gui.drawGradient(button.frame.getX(), button.frame.getY() + offset, button.frame.getX() + button.frame.getWidth(), button.frame.getY() + offset + 16, isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB(), isHovered ? new Color(0, 0, 0, 75).getRGB() : new Color(0, 0, 0, 60).getRGB());
        FontUtil.drawCFontText("Key", button.frame.getX() + 5, button.frame.getY() + offset + 1, -1);
        if (binding) {
            FontUtil.drawCFontText( "...", button.frame.getX() + button.frame.getWidth() - 5 - FontUtil.getCFontTextWidth("..."), button.frame.getY() + offset + 1, -1);
        } else {
            String key;
            switch (button.module.getKey()) {
                case 345:
                    key = "RCtrl";
                    break;
                case 341:
                    key = "Ctrl";
                    break;
                case 346:
                    key = "RAlt";
                    break;
                case -1:
                    key = "NONE";
                    break;
                default:
                    key = Keyboard.getKeyName(button.module.getKey());
            }

            FontUtil.drawCFontText(key, button.frame.getX() + button.frame.getWidth() - 5 - FontUtil.getCFontTextWidth(key), button.frame.getY() + offset + 1, -1);
        }
    }

    public boolean isHovered(final double x, final double y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 16;
    }
}
